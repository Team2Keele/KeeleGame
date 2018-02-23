/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
/**
 *
 * @author elliott
 */
public class Map 
{
    private Tile[][] grid;
    private Sprite bgImage;
    private float tileSize;
    
    Map(float tileSize)
    {
        grid = new Tile[0][0];
        bgImage = new Sprite();
        this.tileSize = tileSize;
    }
    
    public void render(Graphics g)
    {
        //TODO: blocked by Sprite being unfinished
    }
    
    public void loadMap(String mapFile)
    {
        String line = null;

        try {
            FileReader fileReader = 
                new FileReader(mapFile);

            BufferedReader br = 
                new BufferedReader(fileReader);
            
            String sizeLine = br.readLine();
            int xSize = Integer.parseInt(sizeLine.split(" ")[0]);
            int ySize = Integer.parseInt(sizeLine.split(" ")[1]);
            grid = new Tile[xSize][ySize];
            
            TileType type = TileType.NONE;
            
            Random rand = new Random();
            
            int x = 0;
            while((line = br.readLine()) != null) 
            {
                for(int y = 0; y < line.length(); y++)
                {
                    if(x == xSize || x == 0 || y == ySize || y == 0)
                    {
                        type = TileType.EDGE;
                    }
                    
                    if(line.charAt(y) == '0')
                    {
                        type = TileType.WALL;
                    }
                    
                    if(rand.nextFloat() > 0.95)
                    {
                        //blocked by Powerup class
                        //grid[x][y] = new Tile(this, new Point(x, y), type, new Powerup(PowerType.getRandomPower()));
                    }
                    else
                    {
                        //blocked by Acorn class
                        //grid[x][y] = new Tile(this, new Point(x, y), type, new Acorn());
                    }
                }
                x++;
            }   
            
            br.close();         
        }
        catch(FileNotFoundException ex) 
        {
            System.out.println(
                "Unable to open file '" + 
                mapFile + "'");                
        }
        catch(IOException ex) 
        {
            System.out.println(
                "Error reading file '" 
                + mapFile + "'");                  
        }
    }
    
    public void setBackground(Sprite image)
    {
        bgImage = image;
    }

    public Tile getTile(int xIndex, int yIndex)
    {   
        return grid[xIndex][yIndex];
    }
    
    public void clearMap()
    {
        grid = new Tile[0][0];
        bgImage = new Sprite();
    }
    
    public Point getGridSize()
    {
        return new Point(grid.length, grid[0].length);
    }
    
    public float getTileSize()
    {
        return tileSize;
    }
    
    public void setTileSize(float newTileSize)
    {
        tileSize = newTileSize;
    }
}
