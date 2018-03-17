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
import java.net.URL;
import javax.imageio.ImageIO;

public class Sprite {

    private int numOfFrames = 0;
    private int currentFrame = 0;
    private int widthOfSprite = 0;
    private int frameRate = 1;
    private int renderCalls = 0;
    private BufferedImage image = null;
    private BufferedImage[] sprites = null;

    public Sprite(String fileName, int widthOfSprite, int numOfFrames, int fps) {
        this.widthOfSprite = widthOfSprite;
        this.numOfFrames = numOfFrames;
        this.frameRate = fps;

        //Load spriteSheet
        if (loadImage(fileName)) {
            //split all sprites into the sprites array.
            sprites = new BufferedImage[numOfFrames];
            for (int frame = 0; frame < numOfFrames; frame++) {
                sprites[frame] = getSpriteSheetSubImage(frame);
            }
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
        if ((frameRate != 0) & renderCalls >= frameRate) {
            currentFrame = (currentFrame + 1) % numOfFrames;
            renderCalls = 0;
        } else {
            renderCalls++;
        }

    }

    //returns the current frames bufferedImage.
    public BufferedImage getCurrentFrame() {
        return sprites[currentFrame];
    }

    private BufferedImage getSpriteSheetSubImage(int frame) {
        return image.getSubimage(frame * widthOfSprite, 0, widthOfSprite, image.getHeight());
    }

    public void render(Graphics g, Image img, int x, int y, int xSize, int ySize, boolean flipH, boolean flipV) {
        if (flipH && flipV) {
            g.drawImage(img, x + xSize, y + ySize, -xSize, -ySize, null);
        } else if (flipH && !flipV) {
            g.drawImage(img, x + xSize, y, -xSize, ySize, null);
        } else if (!flipH && flipV) {
            g.drawImage(img, x, y + ySize, xSize, -ySize, null);
        } else {
            g.drawImage(img, x, y, xSize, ySize, null);
        }
    }
}
