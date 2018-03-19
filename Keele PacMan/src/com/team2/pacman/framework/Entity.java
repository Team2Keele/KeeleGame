package com.team2.pacman.framework;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected Map tileMap;
    protected Point.Float position;
    protected Point.Float velocity;
    protected Point size;
    protected float velocityMag;
    protected boolean active;
    protected Sprite sprite;

    public Entity(Map map, Point.Float position, Point size) 
    {
        this.position = position;
        this.size = size;
        active = true;
        sprite = new Sprite("default.png", 16, 1);
        tileMap = map;
        velocity = new Point.Float(0, 0);
        velocityMag = 0;
    }
    
    public void render(Graphics g)
    {
        if(isActive())
        {
            sprite.render(g, (int)position.x, (int)position.y, size.x, size.y);
        }
    }
    
    public void update()
    {
        position.x += velocity.x;
        position.y += velocity.y;
        
        sprite.nextFrame();
    }
    
    public boolean isColliding(Tile tile)
    {
        Rectangle2D.Float tBBox = tileMap.getBoundingBox(tile);
        Rectangle2D.Float bBox = new Rectangle2D.Float(position.x, position.y, size.x, size.y);
                
        return rectsColliding(bBox, tBBox);
    }
    
    public boolean isColliding(Entity entity)
    {
        if(isActive() && entity != null && entity.isActive())
        {
            Rectangle2D.Float bBox1 = new Rectangle2D.Float(getPosition().x, 
                    getPosition().y, getSize().x, getSize().y); 

            Rectangle2D.Float bBox2 = new Rectangle2D.Float(entity.getPosition().x, 
                    entity.getPosition().y, entity.getSize().x, entity.getSize().y);

            return rectsColliding(bBox1, bBox2);
        }
        return false;
    }
    
    public boolean isContainedBy(Tile tile)
    {
        Rectangle2D.Float tileBBox = tileMap.getBoundingBox(tile);
        Rectangle2D.Float entityBBox = new Rectangle2D.Float(getPosition().x, 
                getPosition().y, getSize().x, getSize().y);
        
        boolean result = tileBBox.contains(entityBBox);
        
        return result;
    }
    
    protected boolean rectsColliding(Rectangle2D.Float rect1, Rectangle2D.Float rect2)
    {
        boolean result = (rect1.x < (rect2.x + rect2.width) && 
                    (rect1.x + rect1.width) > rect2.x &&
                    rect1.y < (rect2.y + rect2.height) && 
                    (rect1.y + rect1.height) > rect2.y) ;
        return result;
    }
    
    public void activate() 
    {
        active = true;
    }

    public void deactivate() 
    {
        active = false;
    }

    public boolean isActive() 
    {
        return active;
    }

    public Point.Float getVelocity() 
    {
        return velocity;
    }

    public void setPosition(Point.Float newPos) 
    {
        position = newPos;
    }

    public Point.Float getPosition() 
    {
        return position;
    }

    public void setSprite(Sprite newSprite) 
    {
        sprite = newSprite;
    }

    public Sprite getSprite() 
    {
        return sprite;
    }

    public void setSize(Point newSize) 
    {
        size = newSize;
    }

    public Point getSize() 
    {
        return size;
    }
    
    public float getSpeed()
    {
        return velocityMag;
    }
    
    public void setSpeed(float newSpeed)
    {
        velocityMag = newSpeed;
    }
}
