/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Point;

/**
 *
 * @author elliott
 */
public class Player extends Controllable
{
    
    private Powerup currentPower;
    
    public Player(Map map, Point.Float pos, Point size)
    {
        super(map, pos, size);
    }
    
    @Override
    public void update()
    {
        
    }
    
    @Override
    public void collide(Tile tile)
    {
        if(tile.isWall())
        {
            stop();
        }
    }
    
    @Override
    public void collide(Entity entity)
    {
        
    }    
    
    public void turnUp()
    {
        velocity.setLocation(new Point.Float(0, -velocityMag));
    }
    
    public void turnDown()
    {
        velocity.setLocation(new Point.Float(0, velocityMag));
    }
    
    public void turnLeft()
    {
        velocity.setLocation(new Point.Float(-velocityMag, 0));
    }
    
    public void turnRight()
    {
        velocity.setLocation(new Point.Float(velocityMag, 0));
    }
    
    public void stop()
    {
        velocity.setLocation(new Point.Float(0, 0));
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
