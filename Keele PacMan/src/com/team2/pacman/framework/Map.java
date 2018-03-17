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
    private float tileSize;
    private Point gridLength;

    public Map(String mapFile, String imageFile, float tileSize) {
        bgImage = new Sprite(imageFile, 800, 1, 0);
        this.tileSize = tileSize;
        gridLength = new Point(0, 0);
        loadMap(mapFile);
    }

    public void render(Graphics g) {
        bgImage.render(g, bgImage.getCurrentFrame(), 0, 0, (int) (grid.length * tileSize), (int) (grid[0].length * tileSize), false, false);

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                Entity collectable = grid[x][y].getCollectable();
                grid[x][y].testRender(g, this);
                if (collectable != null) {
                    collectable.render(g);
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
            grid = new Tile[gridLength.x][gridLength.y];
            Random rand = new Random();

            TileType type;
            float xPos;
            float yPos;
            Point entitySize;
            Point.Float entityPos;
            float percentOffset = 0.5f;
            Entity tileCollectable;

            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    type = TileType.NONE;
                    xPos = (x * tileSize) + (tileSize * ((1 - percentOffset) / 2));
                    yPos = (y * tileSize) + (tileSize * ((1 - percentOffset) / 2));

                    entityPos = new Point.Float(xPos, yPos);
                    entitySize = new Point((int) (tileSize * percentOffset), (int) (tileSize * percentOffset));
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
                        tileCollectable.setSprite(new Sprite("powerup.png", 16, 1, 0));
                    } else if (type != TileType.WALL) {
                        //blocked by Acorn class
                        tileCollectable = new Acorn(this, entityPos, entitySize);
                        tileCollectable.setSprite(new Sprite("acorn.png", 16, 1, 0));
                    }

                    grid[x][y] = new Tile(new Point(x, y), type, tileCollectable);
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
        float[] coord = {tile.getGridIndex().x * tileSize, tile.getGridIndex().y * tileSize};

        return new Rectangle2D.Float(coord[0], coord[1], tileSize, tileSize);
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

    public float getTileSize() {
        return tileSize;
    }

    public void setTileSize(float newTileSize) {
        tileSize = newTileSize;
    }
}
