package com.team2.pacman.framework;

import java.awt.Point;
import java.awt.Rectangle;


public abstract class Entity {

    private Point position;
    private Point velocity;
    private Point size;
    private boolean active;
    private Sprite sprite;
    private Rectangle bBox;

    public Entity(){
        
    }
    
    public void update(){
        
    }
    
    public void render(){
        
    }
    
    public void activate(){
        active = true;
    }
    
    public void deactivate(){
        active = false;
    }
    
    public void setVelocity(Point newVel){
        velocity = newVel;
    }
    
    public Point getVelocity(){
        return velocity;
    }
    
    public void setPosition(Point newPos){
        position = newPos;
    }
    
    public Point getPosition(){
        return position;
    }
    
    public boolean isColliding(Tile tile){
        return false;
    }
    
    public boolean isColliding(Entity entity){
        return false;
    }
    
    public void collide(Tile tile){
        
    }
    
    public void collide(Entity entity){
        
    }
    
    public void setSprite(Sprite newSprite){
        sprite = newSprite;
    }
    
    public Sprite getSprite(){
        return sprite;
    }
    
    public void setSize(Point newSize){
        size = newSize;
    }
    public Point getSize(){
        return size;
    }
}
