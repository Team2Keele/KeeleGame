package com.team2.pacman.framework;

import java.awt.Point;

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
        if(entity instanceof Player && isColliding(entity)) {
            entity.deactivate();
        }
        else if(isColliding(entity))
        {
            velocity.setLocation(new Point.Float(0, 0)); // Placeholder code
        }
    }
    
    public void calculateJunction(Player player)
    {
        // TODO
    }
    
    public void kill()
    {
        deactivate();
    }
}
