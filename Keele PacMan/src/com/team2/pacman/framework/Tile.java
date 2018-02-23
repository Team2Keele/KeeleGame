package com.team2.pacman.framework;

/**
 *
 * @author Herbie Bradley
 */
public class Tile {
    
    private boolean isWall;
    private boolean isEdge;
    private int[] gridIndex = new int[2];
    private Entity collectable;
    
    public void Tile(Map tileMap, int xCoord, int yCoord, boolean isWall, boolean isEdge, Entity collectable) {
        this.tileMap = tileMap;
        gridIndex[0] = xCoord;
        gridIndex[1] = yCoord;
        if (isWall && isEdge) { // If the tile is both a wall and an edge it is made into a wall.
            this.isWall = isWall;
        }
        else {
            this.isWall = isWall;
            this.isEdge = isEdge;
        }
        this.collectable = collectable;
    }
    
    public void collect(Player player) {
        // TODO: Blocked by Player/Acorn/Powerup
    }
    
    // The following 4 methods work on the assumption that the array of arrays holding the tiles
    // is indexed from (0, 0) in the top left, and that the first parameter of getTile
    // refers to the left to right (x-axis) coordinates, the second parameter to the y coordinates.
    public Tile getAdjacentUp() {
        return tileMap.getTile(gridIndex[0], gridIndex[1] - 1);
    }
    
    public Tile getAdjacentDown() {
        return tileMap.getTile(gridIndex[0], gridIndex[1] + 1);
    }
    
    public Tile getAdjacentLeft() {
        return tileMap.getTile(gridIndex[0] - 1, gridIndex[1]);
    }
    
    public Tile getAdjacentRight() {
        return tileMap.getTile(gridIndex[0] + 1, gridIndex[1]);
    }
    
    public boolean isJunction() {
        boolean leftWall = getAdjacentLeft().getIsWall();
        boolean rightWall = getAdjacentRight().getIsWall();
        boolean upWall = getAdjacentUp().getIsWall();
        boolean downWall = getAdjacentDown().getIsWall();
        return !((leftWall && rightWall) || (upWall && downWall));
    }
    
    public boolean getIsWall() {
        return isWall;
    }
    
    public boolean getIsEdge() {
        return isEdge;
    }
}
