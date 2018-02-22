package com.team2.pacman.framework;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public abstract class Entity {

    private Point2D position;
    private Point2D velocity;
    private Point2D size;
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
    
    public void setVelocity(Point2D newVel){
        velocity = newVel;
    }
    
    public Point2D getVelocity(){
        return velocity;
    }
    
    public void setPosition(Point2D newPos){
        position = newPos;
    }
    
    public Point2D getPosition(){
        return position;
    }
    
    public boolean isColliding(Tile tile){
        return false;
    }
    
}
