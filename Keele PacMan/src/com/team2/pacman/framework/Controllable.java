package com.team2.pacman.framework;

import java.awt.Point;

public abstract class Controllable extends Entity
{
    
    protected Tile currentTile;
    protected Tile nextTile;
    
    public static enum Direction
    {
        UP, DOWN, LEFT, RIGHT, NONE;
    }
    
    public static class InvalidStartTileException extends Exception
    {
        public InvalidStartTileException(String message)
        {
            super(message);
        }
    }
    
    protected Direction currentDirection;
    
    public Controllable(Map map, Tile startTile, Point.Float pos, Point size)
    {
        super(map, pos, size);
        currentTile = startTile;
        currentDirection = Direction.NONE;
    }
    
    public abstract void collide(Tile tile);
    public abstract void collide(Entity entity);
    
    public boolean isAtJunction() 
    {
        return tileMap.isTileJunction(currentTile);
    }
}
