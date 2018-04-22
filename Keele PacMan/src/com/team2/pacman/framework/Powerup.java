/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

public class Powerup extends Entity {

    private long powerTime;
    private int timerLength;
    private PowerType type;

    public static enum PowerType {
        NONE, SPEED, SLOW, ENEMY_SLOW, MULTIPLIER, ENEMY_VULNERABLE;

        public static PowerType getRandomPower() {
            Random random = new Random();
            return values()[random.nextInt(values().length - 1) + 1];
        }
    }

    public Powerup(Map map, Point.Float pos, Point size) {
        super(map, pos, size);
        type = PowerType.getRandomPower();
        powerTime = -1;
        timerLength = 10000;

        switch (type) {
            case SPEED:
                super.setSprite(new Sprite("powerup-speed.png", 16, 8, 100));
                break;
            case SLOW:
                super.setSprite(new Sprite("powerup-slow.png", 16, 8, 100));
                break;
            case ENEMY_SLOW:
                super.setSprite(new Sprite("powerup-enemyslow.png", 16, 8, 100));
                break;
            case MULTIPLIER:
                super.setSprite(new Sprite("powerup-multi.png", 16, 8, 100));
                break;
            case ENEMY_VULNERABLE:
                super.setSprite(new Sprite("powerup-vun.png", 16, 8, 100));
                break;
        }

        super.setDeathSprite(new Sprite("powerup-explode.png", 16, 8, 50));
    }

    public Powerup(Map map, Point.Float pos, Point size, PowerType type) {
        super(map, pos, size);
        this.type = type;
        powerTime = -1;
        timerLength = 10000;
    }

    public boolean timerDone() {
        if (powerTime == -1) {
            powerTime = System.currentTimeMillis() + timerLength;
        }

        if (System.currentTimeMillis() > powerTime) {
            return true;
        }

        return false;
    }

    public PowerType getType() {
        return type;
    }
}
