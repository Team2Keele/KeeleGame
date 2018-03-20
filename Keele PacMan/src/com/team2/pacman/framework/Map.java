/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import com.team2.pacman.framework.Controllable.Direction;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

/**
 *
 * @author elliott
 */
public class Map {

    private Tile[][] grid;
    private Sprite bgImage;
    private Point tileSize;
    private Point gridLength;
    private int windowX;
    private int windowY;

    public Map(String mapFile, String imageFile, int windowX, int windowY) {
        bgImage = new Sprite(imageFile, 800, 1, 0);
        gridLength = new Point(0, 0);
        this.windowX = windowX;
        this.windowY = windowY;
        tileSize = new Point(0, 0);
        loadMap(mapFile);
    }

    public void render(Graphics g) {
        bgImage.render(g, 0, 0, windowX, windowY);

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                Entity collectable = grid[x][y].getCollectable();
                grid[x][y].render(g);
                if (collectable != null) {
                    collectable.render(g);
                }
            }
        }
    }

    public void update() {
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (tile.getCollectable() != null) {
                    tile.getCollectable().update();
                    if (tile.getCollectable().finishedDying()) {
                        tile.removeCollectable();
                    }
                }
            }
        }
    }

    private void loadMap(String mapFile) {
        String line = null;

        try {
            URL url = this.getClass().getResource("../res/" + mapFile);
            File file = new File(url.toURI());

            FileReader fileReader = new FileReader(file);

            BufferedReader br = new BufferedReader(fileReader);

            String sizeLine = br.readLine();
            gridLength.setLocation(Integer.parseInt(sizeLine.split(" ")[0]), Integer.parseInt(sizeLine.split(" ")[1]));
            tileSize.x = windowX / gridLength.x;
            tileSize.y = windowY / gridLength.y;
            grid = new Tile[gridLength.x][gridLength.y];
            Random rand = new Random();

            TileType type;
            float xPos;
            float yPos;
            Point entitySize;
            Point.Float entityPos;
            float percentOffset = 0.8f;
            Entity tileCollectable;

            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    type = TileType.NONE;

                    xPos = (x * tileSize.x) + (tileSize.x * ((1 - percentOffset) / 2));
                    yPos = (y * tileSize.y) + (tileSize.y * ((1 - percentOffset) / 2));

                    entityPos = new Point.Float(xPos, yPos);
                    entitySize = new Point((int) (tileSize.x * percentOffset), (int) (tileSize.y * percentOffset));
                    tileCollectable = null;

                    if (x == gridLength.x - 1 || x == 0 || y == gridLength.y - 1 || y == 0) {
                        type = TileType.EDGE;
                    }

                    if (line.charAt(x) == '1') {
                        type = TileType.WALL;
                    }

                    if (rand.nextFloat() > 0.95 && type != TileType.WALL) //chance to place a powerup rather than an acorn
                    {
                        //blocked by Powerup class
                        tileCollectable = new Powerup(this, entityPos, entitySize);
                    } else if (type != TileType.WALL) {
                        //blocked by Acorn class
                        tileCollectable = new Acorn(this, entityPos, entitySize);
                    }

                    grid[x][y] = new Tile(new Point(x, y), type, tileSize, tileCollectable);
                }
                y++;
            }

            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + mapFile + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + mapFile + "'");
        } catch (URISyntaxException ex) {

        }
    }
    
    public void respawnCollectables()
    {
        Random rand = new Random();

        TileType type;
        float xPos;
        float yPos;
        Point entitySize;
        Point.Float entityPos;
        float percentOffset = 0.8f;
        Entity tileCollectable;
        
        for(int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[0].length; x++) {

                    xPos = (x * tileSize.x) + (tileSize.x * ((1 - percentOffset) / 2));
                    yPos = (y * tileSize.y) + (tileSize.y * ((1 - percentOffset) / 2));

                    entityPos = new Point.Float(xPos, yPos);
                    entitySize = new Point((int) (tileSize.x * percentOffset), (int) (tileSize.y * percentOffset));
                    tileCollectable = null;

                    if (rand.nextFloat() > 0.95 && !grid[x][y].isWall()) //chance to place a powerup rather than an acorn
                    {
                        //blocked by Powerup class
                        tileCollectable = new Powerup(this, entityPos, entitySize);
                    } else if (!grid[x][y].isWall()) {
                        //blocked by Acorn class
                        tileCollectable = new Acorn(this, entityPos, entitySize);
                    }

                    grid[x][y].setCollectable(tileCollectable);
                }
            }
    }

    public Tile getTileAdjacent(Tile tile, Direction adjacentDir) {
        int xIncrement = 0;
        int yIncrement = 0;

        switch (adjacentDir) {
            case UP:
                yIncrement = -1;
                break;
            case DOWN:
                yIncrement = 1;
                break;
            case LEFT:
                xIncrement = -1;
                break;
            case RIGHT:
                xIncrement = 1;
                break;
        }

        try {
            return getTile(tile.getGridIndex().x + xIncrement, tile.getGridIndex().y + yIncrement);
        } catch (ArrayIndexOutOfBoundsException e) {
            //return dummy tile to prevent execptions
            Point index = tile.getGridIndex();
            Point nextIndex = (Point) tile.getGridIndex().clone();
            if (index.x == 0) {
                nextIndex.x = gridLength.x - 1;
            } else if (index.x == gridLength.x - 1) {
                nextIndex.x = 0;
            }

            if (index.y == 0) {
                nextIndex.y = gridLength.y - 1;
            } else if (index.y == gridLength.y - 1) {
                nextIndex.y = 0;
            }

            return getTile(nextIndex.x, nextIndex.y);
        }
    }

    public boolean isTileJunction(Tile tile) {
        boolean leftWall = getTileAdjacent(tile, Direction.LEFT).isWall();
        boolean rightWall = getTileAdjacent(tile, Direction.RIGHT).isWall();
        boolean upWall = getTileAdjacent(tile, Direction.UP).isWall();
        boolean downWall = getTileAdjacent(tile, Direction.DOWN).isWall();

        return !((leftWall && rightWall) || (upWall && downWall));
    }

    public Rectangle2D.Float getBoundingBox(Tile tile) {
        float[] coord = {tile.getGridIndex().x * tileSize.x, tile.getGridIndex().y * tileSize.y};

        return new Rectangle2D.Float(coord[0], coord[1], tileSize.x, tileSize.y);
    }

    public void setBackground(Sprite image) {
        bgImage = image;
    }

    public Tile getTile(int xIndex, int yIndex) {
        return grid[xIndex][yIndex];
    }

    public Point getGridSize() {
        return new Point(grid.length, grid[0].length);
    }

    public Point getTileSize() {
        return tileSize;
    }

    public void setTileSize(Point newTileSize) {
        tileSize = newTileSize;
    }
}
