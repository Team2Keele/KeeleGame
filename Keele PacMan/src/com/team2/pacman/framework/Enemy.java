package com.team2.pacman.framework;

import com.team2.pacman.window.Game;
import java.awt.Point;
import java.util.Random;

public class Enemy extends Controllable{
    
    private int deathTimer;
    
    public Enemy(Map map, Tile startTile, float relativeSize) throws InvalidStartTileException
    {
        super(map, startTile, relativeSize);
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
                case 0: turn(Direction.UP);
                case 1: turn(Direction.DOWN);
                case 2: turn(Direction.LEFT);
                case 3: turn(Direction.RIGHT);
            }
        }
    }
    
    public void kill()
    {
        deactivate();
    }
}
