/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Sprite {

    private static HashMap<String, BufferedImage[]> loadedSprites = new HashMap();
    
    private int numOfFrames = 0;
    private int currentFrame = 0;
    private int widthOfSprite = 0;
    private long nextFrameTime;
    private int frameLengthMillis;
    private BufferedImage image = null;
    private BufferedImage[] sprites = null;

    public Sprite(String fileName, int widthOfSprite, int numOfFrames, int frameTimeMillis) {
        this.widthOfSprite = widthOfSprite;
        this.numOfFrames = numOfFrames;
        frameLengthMillis = frameTimeMillis;
        nextFrameTime = System.currentTimeMillis() + frameLengthMillis;

        if(!loadedSprites.containsKey(fileName))
        {
            //Load spriteSheet
            if (loadImage(fileName)) {
                //split all sprites into the sprites array.
                sprites = new BufferedImage[numOfFrames];
                for (int frame = 0; frame < numOfFrames; frame++) {
                    sprites[frame] = getSpriteSheetSubImage(frame);
                }
                loadedSprites.put(fileName, sprites);
            }
        }
        else
        {
            sprites = loadedSprites.get(fileName);
        }
    }

    //returns true if the spritesheet was found, false if not.
    private boolean loadImage(String fileName) {
        try {
            URL path = Sprite.class.getResource("../res/" + fileName);
            image = ImageIO.read(path);
        } catch (IOException e) {
            System.out.println(e + "\nImage cannot be found!\n");
            return false;
        }
        return true;
    }

    //returns full image, for map backgound, or like splash screen (game over or something)
    public BufferedImage getImage() {
        return image;
    }

    //increments the currentFrame index and loops back to 0 if
    public void nextFrame() {
        if (System.currentTimeMillis() >= nextFrameTime) {
            currentFrame = (currentFrame + 1) % numOfFrames;
            nextFrameTime = System.currentTimeMillis() + frameLengthMillis;
        }
    }

    //returns the current frames bufferedImage.
    public BufferedImage getCurrentFrame() {
        return sprites[currentFrame];
    }

    private BufferedImage getSpriteSheetSubImage(int frame) {
        return image.getSubimage(frame * widthOfSprite, 0, widthOfSprite, image.getHeight());
    }

    public void render(Graphics g, int x, int y, int xSize, int ySize) {
        g.drawImage(getCurrentFrame(), x, y, xSize, ySize, null);
    }

    public int getFrameLength() {
        return frameLengthMillis;
    }

    public int getFrameCount() {
        return numOfFrames;
    }

    public void render(Graphics g, int x, int y, int xSize, int ySize, Controllable.Direction rotateDir) {

        switch (rotateDir) {
            case LEFT:
                g.drawImage(getCurrentFrame(), x + xSize, y, -xSize, ySize, null);
                break;
            case UP:
            case DOWN:
            case RIGHT:
            case NONE:
                g.drawImage(getCurrentFrame(), x, y, xSize, ySize, null);
                break;
        }
    }
}
