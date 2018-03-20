package com.team2.pacman.framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Herbie Bradley
 */
public class Tile {

    private TileType type;
    private Point gridIndex;
    private Entity collectable;
    private Sprite sprite;
    private Point tileSize;

    public Tile(Point index, TileType type, Point tileSize, Entity collectable) {
        this.gridIndex = index;
        this.tileSize = tileSize;
        this.type = type;
        this.collectable = collectable;

        switch (this.type) {
            case WALL:
                this.sprite = new Sprite("wall.png", 16, 1, 0);
                break;
            case EDGE:
                this.sprite = new Sprite("edge.png", 16, 1, 0);
                break;
            case NONE:
                this.sprite = new Sprite("floor.png", 16, 1, 0);
                break;
        }
    }
    
    public Entity getCollectable() {
        return collectable;
    }

    public void removeCollectable() {
        collectable = null;
    }

    public boolean isWall() {
        return type == TileType.WALL;
    }

    public boolean isEdge() {
        return type == TileType.EDGE;
    }

    public Point getGridIndex() {
        return gridIndex;
    }

    public void render(Graphics g) {

        sprite.render(g, gridIndex.x * tileSize.x, gridIndex.y * tileSize.y, tileSize.x, tileSize.y);
    }
    
    public void setCollectable(Entity newCollectable)
    {
        collectable = newCollectable;
    }
}
