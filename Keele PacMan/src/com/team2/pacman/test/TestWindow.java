package com.team2.pacman.test;

import java.awt.Dimension;
import javax.swing.JFrame;

public class TestWindow {

    public TestWindow(int w, int h, String title, TestGame game) {
        game.setPreferredSize(new Dimension(w, h));
        game.setMaximumSize(new Dimension(w, h));
        game.setMinimumSize(new Dimension(w, h));

        JFrame frame = new JFrame(title);
        frame.addKeyListener(game);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }
}
