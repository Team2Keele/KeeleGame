package com.team2.pacman.framework;

public class Tile {

    private TileType tileType;
    private Entity collectable;

    public void Tile(TileType tileType, Entity collectable) {
        this.tileType = tileType;
        this.collectable = collectable;
    }

    public void collect(Player player) {
        // TODO: Blocked by Player/Acorn/Powerup
    }

    public TileType getTileType(){
        return tileType;
    }
}
