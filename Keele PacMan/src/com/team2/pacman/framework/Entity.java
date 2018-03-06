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
        position = position;
        this.size = size;
        tileMap = map;
        velocity = new Point.Float(1, 1);
        velocityMag = 0;
    }
    
    public void render(Graphics g)
    {
        //TODO: render sprite image for entity, blocked by sprite.
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
        Rectangle2D.Float bBox1 = new Rectangle2D.Float(entity.getPosition().x, 
                entity.getPosition().y, entity.getSize().x, entity.getSize().y); 
        
        Rectangle2D.Float bBox2 = new Rectangle2D.Float(entity.getPosition().x, 
                entity.getPosition().y, entity.getSize().x, entity.getSize().y);
                
        return rectsColliding(bBox1, bBox2);
    }
    
    protected boolean rectsColliding(Rectangle2D.Float rect1, Rectangle2D.Float rect2)
    {
        boolean result = (rect1.x < rect2.x + rect2.width && 
                    rect1.x + rect1.width > rect2.x &&
                    rect1.y < rect2.y + rect2.height && 
                    rect1.y + rect1.height > rect2.y) ;
        
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

    public boolean getActive() 
    {
        return active;
    }

    public void setVelocity(Point.Float newVel) 
    {
        velocity = newVel;
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
    
    public float getVelocityMag()
    {
        return velocityMag;
    }
    
    public void setVelocityMag(float newMag)
    {
        velocityMag = newMag;
    }
}
