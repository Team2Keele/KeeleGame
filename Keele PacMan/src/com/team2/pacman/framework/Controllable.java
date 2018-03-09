package com.team2.pacman.framework;

import java.awt.Point;

public abstract class Controllable extends Entity
{
    
    public Controllable(Map map, Point.Float pos, Point size)
    {
        super(map, pos, size);
    }
    
    public abstract void collide(Tile tile);
    public abstract void collide(Entity entity);
    
    public boolean isAtJunction() 
    {
        Tile currentTile = tileMap.getTile((int)position.x, (int)position.y);
        return tileMap.isTileJunction(currentTile);
    }
}
