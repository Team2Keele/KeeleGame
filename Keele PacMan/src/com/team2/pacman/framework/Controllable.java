/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Point;

public abstract class Controllable extends Entity
{
    
    public Controllable(Map map, Point.Float pos, Point size)
    {
        super(map, pos, size);
    }
    
    public abstract void collide(Tile tile);
    public abstract void collide(Entity entity);
}
