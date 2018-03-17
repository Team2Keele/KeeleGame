package com.team2.pacman.framework;

import com.team2.pacman.window.Game;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public abstract class Controllable extends Entity
{
 
    protected Game gameObj;
    protected Tile currentTile;
    protected Tile nextTile;
    protected Direction currentDirection;
    protected Direction nextMove;
    protected int xOffset;
    protected int yOffset;
    
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
    
    public Controllable(Map map, Tile startTile, float relativeSize) throws InvalidStartTileException
    {
        super(map, getOffsetPos(relativeSize, startTile, map), getOffsetSize(relativeSize, startTile, map));
        currentTile = startTile;
        currentDirection = Direction.NONE;
        nextMove = Direction.NONE;
        if(currentTile.isWall())
        {
            throw(new Controllable.InvalidStartTileException("Start Tile cannot be a wall"));
        }
    }
    
    private static Point.Float getOffsetPos(float percentOffset, Tile tile, Map map)
    {
        Rectangle2D.Float tileRect = map.getBoundingBox(tile);
        
        float xPos = tileRect.x + (tileRect.width * ((1 - percentOffset) / 2)); 
        float yPos = tileRect.y + (tileRect.height * ((1 - percentOffset) / 2));
                        
        return new Point.Float(xPos, yPos);
    }
    
    private static Point getOffsetSize(float percentOffset, Tile tile, Map map)
    {
        Rectangle2D.Float tileRect = map.getBoundingBox(tile);
        
        int width = (int)(tileRect.width * percentOffset); 
        int height = (int)(tileRect.height * percentOffset);
        
        return new Point(width, height);
    }
    
    public abstract void collide(Tile tile);
    public abstract void collide(Entity entity);
    
    public boolean isAtJunction() 
    {
        return tileMap.isTileJunction(currentTile);
    }
    
        public void center()
    {
        position.x = tileMap.getBoundingBox(currentTile).x + xOffset;
        position.y = tileMap.getBoundingBox(currentTile).y + yOffset;
    }

    public void turn(Direction dir)
    {
        
        nextMove = dir;
        switch(dir)
        {
            case UP:
                calculateTurn(dir, Direction.DOWN, 0, -velocityMag);
                break;
                
            case DOWN:
                calculateTurn(dir, Direction.UP, 0, velocityMag);
                break;
                
            case LEFT:
                calculateTurn(dir, Direction.RIGHT, -velocityMag, 0);
                break;
                
            case RIGHT:
                calculateTurn(dir, Direction.LEFT, velocityMag, 0);
                break;
        }
    }
    
    private void calculateTurn(Direction moveDir, Direction checkDir, float velocityX, float velocityY)
    {
        if((isContainedBy(currentTile) && isAtJunction()) ||
            currentDirection == checkDir  || currentDirection == Direction.NONE)
        {
            velocity.setLocation(new Point.Float(velocityX, velocityY));
            nextTile = tileMap.getTileAdjacent(currentTile, moveDir);
            currentDirection = moveDir;
            nextMove = Direction.NONE;
            if(isContainedBy(currentTile))
            {
                center();
            }
        }
    }
    
    public void stop()
    {
        velocity.setLocation(new Point.Float(0, 0));
        nextTile = null;
        currentDirection = Direction.NONE;
        center();
    }
}
