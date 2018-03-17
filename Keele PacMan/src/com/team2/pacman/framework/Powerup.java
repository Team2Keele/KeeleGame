/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Point;

/**
 *
 * @author w4u50
 */
public class Powerup extends Entity {

    
    private Sprite explode;
    
    public Powerup(Map map, Point.Float pos, Point size) {
        super(map, pos, size);
        super.setSprite(new Sprite("powerup-sheet.png", 16, 8, 4));
        //on deactivate set the sprite to this animation and it will die.
        explode = new Sprite("powerup-explode.png", 16, 8, 8);
    }
}
