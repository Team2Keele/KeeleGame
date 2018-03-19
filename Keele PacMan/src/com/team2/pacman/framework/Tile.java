package com.team2.pacman.framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

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
    
    public void removeCollectable()
    {
        collectable = null;
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
    
    public void testRender(Graphics g, Map m)
    {
        Rectangle2D.Float tRect = m.getBoundingBox(this);
        
        switch(type)
        {
            case WALL:
                g.setColor(Color.red);
                break;
            case EDGE:
                g.setColor(Color.MAGENTA);
                break;
            case NONE:
                g.setColor(Color.GREEN);
                break;
        }
        
        g.fillRect((int)tRect.x, (int)tRect.y, (int)tRect.width, (int)tRect.height);
        g.setColor(Color.BLACK);
        g.drawRect((int)tRect.x, (int)tRect.y, (int)tRect.width, (int)tRect.height);
    }
}
