package com.team2.pacman.framework;

import com.team2.pacman.window.Game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public abstract class Controllable extends Entity {

    protected Tile currentTile;
    protected Tile nextTile;
    protected Direction currentDirection;
    protected Direction nextMove;
    protected float speedMult;
    protected int xOffset;
    protected int yOffset;

    public static enum Direction {
        NONE, UP, DOWN, LEFT, RIGHT;

        public static Direction getRandomDir() {
            Random random = new Random();
            return values()[random.nextInt(values().length - 1) + 1];
        }

        public static float getDirAsAngle(Direction dir) {
            switch (dir) {
                case UP:
                    return 270f;
                case DOWN:
                    return 90f;
                case LEFT:
                    return 180f;
                case RIGHT:
                    return 0f;
                case NONE:
                    return 1000000f;
                default:
                    return -1f;
            }
        }

        public static Direction getClosestDir(float angle) {
            float lastDiff = 1000;
            float diff;
            Direction dir = NONE;
            for (int i = 0; i < values().length; i++) {
                float dirAngle = getDirAsAngle(values()[i]);
                diff = Math.abs(Math.min(Math.abs(angle - dirAngle), 360 - Math.abs(angle - dirAngle)));
                if (diff < lastDiff) {
                    dir = values()[i];
                    lastDiff = diff;
                }
            }

            return dir;
        }
    }

    public static class InvalidStartTileException extends Exception {

        public InvalidStartTileException(String message) {
            super(message);
        }
    }

    public Controllable(Map map, Tile startTile, float relativeSize) throws InvalidStartTileException {
        super(map, getOffsetPos(relativeSize, startTile, map), getOffsetSize(relativeSize, startTile, map));
        currentTile = startTile;
        currentDirection = Direction.NONE;
        nextMove = Direction.NONE;
        speedMult = 1;
        this.xOffset = (int) (tileMap.getBoundingBox(currentTile).width * ((1 - relativeSize) / 2));
        this.yOffset = (int) (tileMap.getBoundingBox(currentTile).height * ((1 - relativeSize) / 2));
        if (currentTile.isWall()) {
            throw (new InvalidStartTileException("Start Tile cannot be a wall"));
        }
    }

    @Override
    public void render(Graphics g) {
        sprite.render(g, (int) position.x, (int) position.y, size.x, size.y, currentDirection);
    }

    protected void updatePosition() {
        position.x += velocity.x * speedMult;
        position.y += velocity.y * speedMult;

        sprite.nextFrame();

        if (nextTile != null && isColliding(nextTile)) {
            collide(nextTile);
        }

        //only case where this could happen is if entity left edge
        //therefore gets looped to other side
        if (!isColliding(currentTile)) {
            Rectangle2D.Float nextBBox = tileMap.getBoundingBox(nextTile);

            switch (currentDirection) {
                case UP:
                    position.y = nextBBox.y + nextBBox.height - yOffset - 5;
                    break;
                case DOWN:
                    position.y = nextBBox.y - nextBBox.height + yOffset + 5;
                    break;
                case LEFT:
                    position.x = nextBBox.x + nextBBox.width - xOffset - 5;
                    break;
                case RIGHT:
                    position.x = nextBBox.x - nextBBox.width + xOffset + 5;
                    break;
            }

            currentTile = nextTile;
            nextTile = tileMap.getTileAdjacent(currentTile, currentDirection);
        }
    }

    private static Point.Float getOffsetPos(float percentOffset, Tile tile, Map map) {
        Rectangle2D.Float tileRect = map.getBoundingBox(tile);

        float xPos = tileRect.x + (tileRect.width * ((1 - percentOffset) / 2));
        float yPos = tileRect.y + (tileRect.height * ((1 - percentOffset) / 2));

        return new Point.Float(xPos, yPos);
    }

    private static Point getOffsetSize(float percentOffset, Tile tile, Map map) {
        Rectangle2D.Float tileRect = map.getBoundingBox(tile);

        int width = (int) (tileRect.width * percentOffset);
        int height = (int) (tileRect.height * percentOffset);

        return new Point(width, height);
    }

    public abstract void collide(Tile tile);

    public abstract void collide(Entity entity);

    public boolean isAtJunction() {
        return tileMap.isTileJunction(currentTile);
    }

    public void center() {
        position.x = tileMap.getBoundingBox(currentTile).x + xOffset;
        position.y = tileMap.getBoundingBox(currentTile).y + yOffset;
    }

    public void turn(Direction dir) {

        nextMove = dir;
        switch (dir) {
            case UP:
                calculateTurn(dir, Direction.DOWN, 0, -velocityMag);
                break;

            case DOWN:
                calculateTurn(dir, Direction.UP, 0, velocityMag);
                break;

            case LEFT:
                calculateTurn(dir, Direction.RIGHT, -velocityMag, 0);
                break;

            case RIGHT:
                calculateTurn(dir, Direction.LEFT, velocityMag, 0);
                break;
        }
    }

    protected void calculateTurn(Direction moveDir, Direction checkDir, float velocityX, float velocityY) {
        if ((isContainedBy(currentTile) && isAtJunction()) && !tileMap.getTileAdjacent(currentTile, moveDir).isWall()
                || currentDirection == checkDir || currentDirection == Direction.NONE) {
            velocity.setLocation(new Point.Float(velocityX, velocityY));
            nextTile = tileMap.getTileAdjacent(currentTile, moveDir);
            currentDirection = moveDir;
            nextMove = Direction.NONE;
            if (isContainedBy(currentTile)) {
                center();
            }
        }
    }

    public void stop() {
        velocity.setLocation(new Point.Float(0, 0));
        nextTile = null;
        currentDirection = Direction.NONE;
        center();
    }

    public void setSpeedMult(float mult) {
        speedMult = mult;
    }

    public void moveToTile(Tile tile) throws InvalidStartTileException {
        if (!tile.isWall()) {
            stop();
            currentTile = tile;
            Rectangle2D.Float tileRect = tileMap.getBoundingBox(tile);

            float xPos = tileRect.x + xOffset;
            float yPos = tileRect.y + yOffset;

            position.setLocation(xPos, yPos);
        } else {
            throw (new InvalidStartTileException("New Tile cannot be a wall"));
        }
    }
}
