package com.team2.pacman.framework;

import com.team2.pacman.window.Game;
import java.awt.Point;
import java.util.Random;

public class Enemy extends Controllable {

    private Tile startTile;
    private boolean isVulnerable;
    private boolean turning;
    private Player gamePlayer;

    public Enemy(Map map, Player player, Tile startTile, float relativeSize) throws InvalidStartTileException {
        super(map, startTile, relativeSize);
        gamePlayer = player;
        this.startTile = startTile;
        isVulnerable = false;
        setDeathSprite(new Sprite("default.png", 16, 1, 5000));
    }

    @Override
    public void update() {
        if (isActive()) {
            updatePosition();
            if (!turning) {
                calculateJunction();
            }
        } else {
            updateDeath();
            if (finishedDying()) {
                activate();
            }
        }
    }

    @Override
    public void collide(Tile tile) {
        if (tile.isWall() || currentDirection == Direction.NONE) {
            stop();
            turn(Direction.getRandomDir());
        } else {
            currentTile = nextTile;
            nextTile = tileMap.getTileAdjacent(currentTile, currentDirection);
            turning = false;
        }
    }

    @Override
    public void collide(Entity entity) {
        if (entity instanceof Player && isColliding(entity)) {
            entity.deactivate();
        } else if (isColliding(entity)) {
            velocity.setLocation(new Point.Float(0, 0)); // Placeholder code
        }
    }

    public void calculateJunction() {
        if (isAtJunction() && isContainedBy(currentTile)) {
            turn(getDirectionToPlayer());
            turning = true;
        }
    }

    private Direction getDirectionToPlayer() {
        float angleToPlayer = (float) Math.toDegrees(Math.atan2(gamePlayer.getPosition().y - position.y, gamePlayer.getPosition().x - position.x));

        //random weighting so enemy doesnt go straight for player
        Random rand = new Random();
        angleToPlayer += rand.nextInt(45 + 46) - 45;

        if (isVulnerable) {
            angleToPlayer -= 180;
        }

        if (angleToPlayer < 0) {
            angleToPlayer += 360;
        }
        return Direction.getClosestDir(angleToPlayer);
    }

    public void kill() {
        deactivate();
        startDeath();
        try {
            moveToTile(startTile);
        } catch (Controllable.InvalidStartTileException e) {
            System.out.print(e.getMessage());
            System.exit(-1);
        }
    }

    public void setVulnerable() {
        isVulnerable = true;
    }

    public void setInvulnerable() {
        isVulnerable = false;
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }
}
