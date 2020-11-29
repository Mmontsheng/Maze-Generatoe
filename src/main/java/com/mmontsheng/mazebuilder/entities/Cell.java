package com.mmontsheng.mazebuilder.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.mmontsheng.mazebuilder.game.Game;

import lombok.Data;

@Data
public class Cell {
    final int w = Game.COL_WIDTH;
    private Random rand;
    private int i;
    private int j;

    private boolean visited = false;
    private boolean[] walls = { true, true, true, true };
    private boolean neigbour;

    public Cell(int i, int j,Random rand) {
        this.i = i;
        this.j = j;
        this.rand = rand;
    }

    public void render(Graphics g) {
        final int y = j * w;
        final int x = i * w;
        
        if (visited) {
            highlight(g, Color.red);
            // g.setColor(Color.red);
            // g.fillRect(x, y, w, w);
        }
        g.setColor(Color.WHITE);
        if (walls[0]) {
            g.drawLine(x, y, x + w, y);
        }
        if (walls[1]) {
            g.drawLine(x + w, y, x + w, y + w);
        }
        if (walls[2]) {
            g.drawLine(x + w, y + w, x, y + w);
        }
        if (walls[3]) {
            g.drawLine(x, y + w, x, y);
        }

    }

    /**
     * 
     * @param g
     */
    public void highlight(Graphics g, Color color) {
        final int y = j * w;
        final int x = i * w;
        g.setColor(color);
        g.fillRect(x, y, w, w);
    }
}
