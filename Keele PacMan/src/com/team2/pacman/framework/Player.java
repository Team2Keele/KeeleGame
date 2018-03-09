/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author elliott
 */
public class Player extends Controllable
{
    
    private Powerup currentPower;
    private Direction nextMove;
    private int xOffset;
    private int yOffset;

    
    public Player(Map map, Tile currentTile, float relativeSize) throws InvalidStartTileException
    {
        super(map, currentTile, getOffsetPos(relativeSize, currentTile, map), getOffsetSize(relativeSize, currentTile, map));
        
        this.xOffset = (int)(map.getBoundingBox(currentTile).width * ((1 - relativeSize) / 2));
        this.yOffset = (int)(map.getBoundingBox(currentTile).height * ((1 - relativeSize) / 2));
        
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
        
    @Override
    public void update()
    {
        position.x += velocity.x;
        position.y += velocity.y;
        
        sprite.nextFrame();

        if(nextTile != null && isColliding(nextTile))
        {
            collide(nextTile);
        }
        
        if(isAtJunction() && isContainedBy(currentTile) && nextMove != Direction.NONE)
        {
            turn(nextMove);
        }
        
        //only case where this could happen is if player left edge
        //therefore gets looped to other side
        if(!isColliding(currentTile))
        {
            Rectangle2D.Float nextBBox = tileMap.getBoundingBox(nextTile);
            
            switch(currentDirection)
            {
                case UP:
                    position.y = nextBBox.y + nextBBox.height - yOffset - 5;
                    break;
                case DOWN:
                    position.y = nextBBox.y - nextBBox.height + yOffset + 5;
                    break;
                case LEFT:
                    position.x = nextBBox.x + nextBBox.width - xOffset - 5;
                    break;
                case RIGHT:
                    position.x = nextBBox.x - nextBBox.width + xOffset + 5;
                    break;
            }
            
            currentTile = nextTile;
            nextTile = tileMap.getTileAdjacent(currentTile, currentDirection);
        }
        
        //TODO: update powerup
    }
    
    @Override
    public void collide(Tile tile)
    {
        if(tile.isWall())
        {
            stop();
        }
        else
        {
            currentTile = nextTile;
            nextTile = tileMap.getTileAdjacent(currentTile, currentDirection);
        }
    }
    
    @Override
    public void collide(Entity entity)
    {
        
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
    
    public void buff(Powerup power)
    {
        currentPower = power;
    }
    
    public void debuff()
    {
        
    }
    
    public void collect(Acorn points)
    {
        
    }
}
