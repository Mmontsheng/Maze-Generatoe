package com.mmontsheng.mazebuilder.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.mmontsheng.mazebuilder.entities.Cell;
import com.mmontsheng.mazebuilder.game.Controller;

public class Game extends Canvas implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // game dimensions
    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static final int COL_WIDTH = 50;
    public static final int COLS = WIDTH / COL_WIDTH;
    public static final int ROWS = HEIGHT / COL_WIDTH;
    private static final String GAME_TITLE = "Maze Generator";

    private boolean running = false;
    private transient Thread thread;
    
    private transient Controller controller;

    // graphics
    private transient  BufferedImage image;
    
    
    public static final Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 

    public Game() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        setFocusable(true);
        requestFocusInWindow();

        JFrame frame = new JFrame(GAME_TITLE);
        frame.setSize(WIDTH+15, HEIGHT+37);
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        start();
    }

    public void run() {
        initialize();
        long lasTime = System.nanoTime();
        final double amoutOfTicks = 10.0;
        double ns = 1000000000 / amoutOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lasTime) / ns;
            lasTime = now;
            if (delta >= 1) {
                tick();
                delta--;
            }
            render();
            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    private void initialize() {
        controller = new Controller();
    }

    private synchronized void start() {
        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "An error occured while stoppping thread", e); 
        }
        System.exit(1);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);


        controller.render(g);

        g.dispose();
        bs.show();

    }

    private void tick() {
        controller.tick();
    }

    
}
