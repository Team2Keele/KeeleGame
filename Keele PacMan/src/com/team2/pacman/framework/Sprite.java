/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprite;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Adam
 */
public class Sprite {

    private int spriteState = 1;
    private BufferedImage img = null;
    BufferedImage sprite = null;

    public void loadSprite(String fileName) {

        try {
            img = ImageIO.read(new File(fileName));

        } catch (IOException e) {
            System.out.println(e + "\nImage cannot be found!\n");
        }
    }

    public void nextFrame() {
        if (spriteState == 1) {
            spriteState += 1;
        } else if (spriteState == 2) {
            spriteState -= 1;
        }
    }

    public BufferedImage getCurrentFrame() {

        if (spriteState == 1) {
            //sprite = img.getSubimage(x, y, img.getWidth/<however many images>, img.getHeight());
        } else if (spriteState == 2) {
            //sprite = img.getSubimage(x, y, img.getWidth/<however many images>, img.getHeight());
        }
        return sprite;

    }

}
