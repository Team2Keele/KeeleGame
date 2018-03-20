/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import com.team2.pacman.test.TestGame;
import com.team2.pacman.window.Game;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author elliott
 */
public class Player extends Controllable {

    private Powerup currentPower;
    private Game gameInstance;
    private Sprite runningHR, idleR, runningUp, runningDown;

    public Player(Game gameInst, Tile startTile, float relativeSize) throws InvalidStartTileException {
        super(gameInst.getMapInstance(), startTile, relativeSize);

        gameInstance = gameInst;
        currentPower = new Powerup(gameInst.getMapInstance(), new Point.Float(0, 0), new Point(0, 0), Powerup.PowerType.NONE);
        currentPower.deactivate();
        runningHR = new Sprite("player-runningHR.png", 16, 4, 100);
        idleR = new Sprite("player-idleR.png", 16, 4, 100);
        runningUp = new Sprite("player-runningUp.png", 16, 4, 100);
        runningDown = new Sprite("player-runningDown.png", 16, 4, 100);
    }

    @Override
    public void update() {
        Enemy[] enemies = gameInstance.getEnemies();

        updatePosition();

        if (isAtJunction() && isContainedBy(currentTile) && nextMove != Direction.NONE) {
            turn(nextMove);
        }

        //set the correct sprite for the players state
        if (velocity.getX() > 0 || velocity.getX() < 0) {
            setSprite(runningHR);
        } else if (velocity.getY() < 0) {
            setSprite(runningUp);
        } else if (velocity.getY() > 0){
            setSprite(runningDown);
        }else{
            setSprite(idleR);
        }

        sprite.nextFrame();

        if (isColliding(currentTile.getCollectable())) {
            collide(currentTile.getCollectable());
        }

        if (currentPower.getType() != Powerup.PowerType.NONE && currentPower.isActive()) {
            if (currentPower.timerDone()) {
                debuff();
            }
        }

        for (int i = 0; i < enemies.length; i++) {
            if (isColliding(enemies[i])) {
                if (enemies[i].isVulnerable()) {
                    enemies[i].kill();
                    gameInstance.incrementScore(100);
                } else {
                    gameInstance.endGame();
                }
            }

        }
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
        if (entity instanceof Acorn) {
            collect((Acorn) entity);
            currentTile.removeCollectable();
        } else {
            buff((Powerup) entity);
        }
    }

    public void buff(Powerup power) {
        debuff();
        switch (power.getType()) {
            case SPEED:
                speedMult = 2.0f;
                break;
            case SLOW:
                speedMult = 0.75f;
                break;
            case ENEMY_SLOW:
                gameInstance.setEnemySpeedMult(0.5f);
                break;
            case MULTIPLIER:
                gameInstance.setScoreMult(5);
                break;
            case ENEMY_VULNERABLE:
                gameInstance.setEnemiesVulnerable();
                break;
        }
        currentPower = power;
        currentPower.deactivate();
        currentPower.startDeath();
    }

    public void debuff() {
        currentPower = new Powerup(tileMap, new Point.Float(0, 0), new Point(0, 0), Powerup.PowerType.NONE);
        speedMult = 1.0f;
        gameInstance.setEnemySpeedMult(1.0f);
        gameInstance.setScoreMult(1);
        gameInstance.setEnemiesInvulnerable();
    }

    public void collect(Acorn points) {
        gameInstance.incrementScore(points.getValue());
        points.deactivate();
    }
}
