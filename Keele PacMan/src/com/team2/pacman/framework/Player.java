/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author elliott
 */
public class Player extends Controllable {

    private Powerup currentPower;
    private int xOffset;
    private int yOffset;
    private Sprite runningHR, idleR;

    public Player(Map map, Tile currentTile, float relativeSize) throws InvalidStartTileException {
        super(map, currentTile, relativeSize);
        runningHR = new Sprite("player-runningHR.png", 16, 4, 4);
        idleR = new Sprite("player-idleR.png", 16, 4, 4);

        super.setSprite(idleR);
        this.xOffset = (int) (map.getBoundingBox(currentTile).width * ((1 - relativeSize) / 2));
        this.yOffset = (int) (map.getBoundingBox(currentTile).height * ((1 - relativeSize) / 2));

    }

    @Override
    public void update() {
        position.x += velocity.x;
        position.y += velocity.y;

        //set the correct sprite for the players state
        if(velocity.getX() > 0 || velocity.getX() < 0){
            setSprite(runningHR);
        }else{
            setSprite(idleR);
        }
        
        sprite.nextFrame();

        if (nextTile != null && isColliding(nextTile)) {
            collide(nextTile);
        }

        if (isAtJunction() && isContainedBy(currentTile) && nextMove != Direction.NONE) {
            turn(nextMove);
        }

        //only case where this could happen is if player left edge
        //therefore gets looped to other side
        if (!isColliding(currentTile)) {
            Rectangle2D.Float nextBBox = tileMap.getBoundingBox(nextTile);

            switch (currentDirection) {
                case UP:
                    position.y = nextBBox.y + nextBBox.height - this.yOffset - 5;
                    break;
                case DOWN:
                    position.y = nextBBox.y - nextBBox.height + this.yOffset + 5;
                    break;
                case LEFT:
                    position.x = nextBBox.x + nextBBox.width - this.xOffset - 5;
                    break;
                case RIGHT:
                    position.x = nextBBox.x - nextBBox.width + this.xOffset + 5;
                    break;
            }

            currentTile = nextTile;
            nextTile = tileMap.getTileAdjacent(currentTile, currentDirection);
        }

        //TODO: update powerup
    }

    @Override
    public void collide(Tile tile) {
        if (tile.isWall()) {
            stop();
        } else {
            currentTile = nextTile;
            nextTile = tileMap.getTileAdjacent(currentTile, currentDirection);
        }
    }

    @Override
    public void collide(Entity entity) {

    }

    public void buff(Powerup power) {
        currentPower = power;
    }

    public void debuff() {

    }

    public void collect(Acorn points) {

    }
}
