package com.team2.pacman.framework;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Entity {

    protected Point position;
    protected Point velocity;
    protected Point size;
    protected boolean active;
    protected Sprite sprite;
    protected Rectangle bBox;

    public Entity(Point position) {
        this.position = position;
    }

    public abstract void update();
    public abstract void render(Graphics g);
    public abstract boolean isColliding(Tile tile);
    public abstract boolean isColliding(Entity entity);
    public abstract void collide(Tile tile);
    public abstract void collide(Entity entity);
    
    
    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public boolean getActive() {
        return active;
    }

    public void setVelocity(Point newVel) {
        velocity = newVel;
    }

    public Point getVelocity() {
        return velocity;
    }

    public void setPosition(Point newPos) {
        position = newPos;
    }

    public Point getPosition() {
        return position;
    }

    public void setSprite(Sprite newSprite) {
        sprite = newSprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSize(Point newSize) {
        size = newSize;
    }

    public Point getSize() {
        return size;
    }
}
