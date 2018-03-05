package com.team2.pacman.framework;

import java.awt.Point;

/**
 *
 * @author Herbie Bradley
 */
public class Tile {
    
    private TileType type;
    private Point gridIndex;
    private Entity collectable;
    
    public Tile(Point index, TileType type, Entity collectable) 
    {
        gridIndex = index;
        this.type = type;
        this.collectable = collectable;
    }
    
    public Entity getCollectable() 
    {
        return collectable;
    }
    
    public boolean isWall() 
    {
        return type == TileType.WALL;
    }
    
    public boolean isEdge() 
    {
        return type == TileType.EDGE;
    }
    
    public Point getGridIndex()
    {
        return gridIndex;
    }
}
