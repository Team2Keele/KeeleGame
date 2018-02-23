package com.team2.pacman.framework;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Herbie Bradley
 */
public class Tile {
    
    private TileType type;
    private Point gridIndex;
    private Map tileMap;
    private Entity collectable;
    
    public Tile(Map tileMap, Point index, TileType type, Entity collectable) 
    {
        this.tileMap = tileMap;
        gridIndex = index;
        this.type = type;
        this.collectable = collectable;
    }
    
    public void collect(Player player) 
    {
        // TODO: Blocked by Player/Acorn/Powerup
    }
    
    // The following 4 methods work on the assumption that the array of arrays holding the tiles
    // is indexed from (0, 0) in the top left, and that the first parameter of getTile
    // refers to the left to right (x-axis) coordinates, the second parameter to the y coordinates.
    public Tile getAdjacentUp() 
    {
        return tileMap.getTile(gridIndex.x, gridIndex.y - 1);
    }
    
    public Tile getAdjacentDown() 
    {
        return tileMap.getTile(gridIndex.x, gridIndex.y + 1);
    }
    
    public Tile getAdjacentLeft() 
    {
        return tileMap.getTile(gridIndex.x - 1, gridIndex.y);
    }
    
    public Tile getAdjacentRight() 
    {
        return tileMap.getTile(gridIndex.x + 1, gridIndex.y);
    }
    
    public boolean isJunction() 
    {
        boolean leftWall = getAdjacentLeft().getIsWall();
        boolean rightWall = getAdjacentRight().getIsWall();
        boolean upWall = getAdjacentUp().getIsWall();
        boolean downWall = getAdjacentDown().getIsWall();
        return !((leftWall && rightWall) || (upWall && downWall));
    }
    
    public boolean getIsWall() 
    {
        return type == TileType.WALL;
    }
    
    public boolean getIsEdge() 
    {
        return type == TileType.EDGE;
    }
    
    public Rectangle2D.Float getBoundingBox()
    {
        float tileSize = tileMap.getTileSize();
        float[] coord = {gridIndex.x * tileSize, gridIndex.y * tileSize};
        
        return new Rectangle2D.Float(coord[0], coord[1], tileSize, tileSize);
    }
}
