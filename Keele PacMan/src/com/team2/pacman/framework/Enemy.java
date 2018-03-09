package com.team2.pacman.framework;

import java.awt.Point;
import java.util.Random;

public class Enemy extends Controllable{
    
    private int deathTimer;
    
    public Enemy(Map map, Point.Float pos, Point size)
    {
        super(map, pos, size);
        deathTimer = 0;
    }
    
    @Override
    public void collide(Tile tile)
    {
        if(isColliding(tile))
        {
            velocity.setLocation(new Point.Float(0, 0)); // Placeholder code
        }
    }
    
    @Override
    public void collide(Entity entity)
    {
        if(entity instanceof Player && isColliding(entity)) 
        {
            entity.deactivate();
        }
        else if(isColliding(entity))
        {
            velocity.setLocation(new Point.Float(0, 0)); // Placeholder code
        }
    }
    
    public void calculateJunction(Player player)
    {
        if(isAtJunction()) 
        {
            Random rand = new Random();
            int direction = rand.nextInt(4);
            switch (direction)
            {
                case 0: turnUp();
                case 1: turnDown();
                case 2: turnLeft();
                case 3: turnRight();
            }
        }
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
    
    public void kill()
    {
        deactivate();
    }
}
