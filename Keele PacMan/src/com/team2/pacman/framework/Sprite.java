/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Sprite {

    private int numOfFrames = 0;
    private int currentFrame = 0;
    private int widthOfSprite = 0;
    private BufferedImage image = null;
    private BufferedImage[] sprites = null;

    public Sprite(String fileName, int widthOfSprite, int numOfFrames) {
        this.widthOfSprite = widthOfSprite;
        this.numOfFrames = numOfFrames;

        //Load spriteSheet
        if (loadImage(fileName)) {
            //split all sprites into the sprites array.
            sprites = new BufferedImage[numOfFrames];
            for (int frame = 0; frame < numOfFrames; frame++) {
                sprites[frame] = getSpriteSheetSubImage(frame);
            }
        }
    }

    //just one big image sprite
    public Sprite(String fileName) {
        loadImage(fileName);
    }

    //returns true if the spritesheet was found, false if not.
    private boolean loadImage(String fileName) {
        try {
            image = ImageIO.read(new File(fileName));
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
        currentFrame = (currentFrame + 1) % numOfFrames;
    }

    //returns the current frames bufferedImage.
    public BufferedImage getCurrentFrame() {
        return sprites[currentFrame];
    }

    private BufferedImage getSpriteSheetSubImage(int frame) {
        return image.getSubimage(frame * widthOfSprite, 0, widthOfSprite, image.getHeight());
    }

    public void render(Graphics g, Image img, int x, int y) {
        g.drawImage(img, x, y, null);
    }
}
