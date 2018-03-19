/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.util.Random;

/**
 *
 * @author elliott
 */
public enum PowerType {
    SPEED, SLOW, ENEMY_SLOW, MULTIPLIER, ENEMY_VULNERABLE;

    public static PowerType getRandomPower() {
        Random random = new Random();
        return values()[random.nextInt(values().length - 1) + 1];
    }
}
