/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import com.team2.pacman.test.TestGame;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author elliott
 */
public class Player extends Controllable
{
    
    private Powerup currentPower;
    private TestGame gameInstance;
    private int xOffset;
    private int yOffset;

    
    public Player(TestGame gameInst, Map map, Tile currentTile, float relativeSize) throws InvalidStartTileException
    {
        super(map, currentTile, relativeSize);
        
        gameInstance = gameInst;
        this.xOffset = (int)(tileMap.getBoundingBox(currentTile).width * ((1 - relativeSize) / 2));
        this.yOffset = (int)(tileMap.getBoundingBox(currentTile).height * ((1 - relativeSize) / 2));
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
        
        if(isColliding(currentTile.getCollectable()))
        {
            collide(currentTile.getCollectable());
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
        if(entity.getClass() == Acorn.class)
        {
            collect((Acorn)entity);
        }
        else
        {
            buff((Powerup)entity);
        }
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
        gameInstance.incrementScore(points.getValue());
        points.deactivate();
    }
}
