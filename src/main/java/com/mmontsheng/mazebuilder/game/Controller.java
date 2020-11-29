package com.mmontsheng.mazebuilder.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;

import com.mmontsheng.mazebuilder.Game;
import com.mmontsheng.mazebuilder.entities.Cell;

public class Controller {
    private Random rand;

    private Cell[][] cells = new Cell[Game.COLS][Game.ROWS];
    
    private Stack<Cell> stack = new Stack<>();
    
    private Cell current;
    
    public Controller() {
        rand = new Random();

        for (int y = 0; y < Game.ROWS; y++) {
            for (int x = 0; x < Game.COLS; x++) {
                Cell cell = new Cell(x, y, rand);
                cells[x][y] = cell;

            }
        }
        int radndX = rand.nextInt(Game.COLS);
        int randY = rand.nextInt(Game.ROWS);
        current = cells[radndX][randY];
    }
    
    /**
     * Renders the cell
     * @param g
     */
    public void render(Graphics g) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].render(g);
            }
        }
        current.highlight(g, Color.green);
    }
    
    /**
     * Updates the cell
     */
    public void tick() {
        current.setVisited(true);
        Cell neighbor = checkNeighbours(current);
        if (neighbor != null) {
            stack.push(current);

            // right wall for neighbor and left wall for current
            if (neighbor.getI() + 1 == current.getI() && neighbor.getJ() == current.getJ()) {
                boolean[] nWall = { neighbor.getWalls()[0], false, neighbor.getWalls()[2], neighbor.getWalls()[3] };
                neighbor.setWalls(nWall);

                boolean[] cWall = { current.getWalls()[0], current.getWalls()[1], current.getWalls()[2], false };
                current.setWalls(cWall); 
            }

            // right wall for current and left for neighbor
            if (neighbor.getI() == current.getI() + 1 && neighbor.getJ() == current.getJ()) {
                boolean[] nWall = { neighbor.getWalls()[0], neighbor.getWalls()[1], neighbor.getWalls()[2], false };
                neighbor.setWalls(nWall);

                boolean[] cWall = { current.getWalls()[0], false, current.getWalls()[2], current.getWalls()[3] };
                current.setWalls(cWall);
            }

            // top of current and bottom of neighbor
            if (neighbor.getJ() + 1 == current.getJ() && neighbor.getI() == current.getI()) {
                boolean[] nWall = { neighbor.getWalls()[0], neighbor.getWalls()[1], false, neighbor.getWalls()[3] };
                neighbor.setWalls(nWall);

                boolean[] cWall = { false, current.getWalls()[1], current.getWalls()[2], current.getWalls()[3] };
                current.setWalls(cWall);
            }

            if (neighbor.getJ() == current.getJ() + 1 && neighbor.getI() == current.getI()) {
                boolean[] nWall = { false, neighbor.getWalls()[1], neighbor.getWalls()[2], neighbor.getWalls()[3] };
                neighbor.setWalls(nWall);

                boolean[] cWall = { current.getWalls()[0], current.getWalls()[1], false, current.getWalls()[3] };
                current.setWalls(cWall);
            }

            neighbor.setVisited(true);
            current = neighbor;
            
        } else if (!stack.isEmpty()) {
            current = stack.pop();
        }
        if(stack.isEmpty()) {
            Game.LOGGER.log(Level.INFO, "Done"); 
        }
    }
    
    /**
     * Checks the cell's neighbor to use 
     * @param thisCell
     * @return the cell to move to
     */
    public Cell checkNeighbours(Cell thisCell) {
        final List<Cell> neighbours = new ArrayList<>();
        Cell top = null;
        Cell right = null;
        Cell bottom = null;
        Cell left = null;
        
        if (thisCell.getI() < Game.COLS - 1) {
            right = cells[thisCell.getI() + 1][thisCell.getJ()];
            if (!right.isVisited()) {
                neighbours.add(right);
            }
        }
        if (thisCell.getJ() > 0) {
            top = cells[thisCell.getI()][thisCell.getJ() - 1];
            if (!top.isVisited()) {
                neighbours.add(top);
            }
        }
        if (thisCell.getJ() < Game.ROWS - 1) {
            bottom = cells[thisCell.getI()][thisCell.getJ() + 1];
            if (!bottom.isVisited()) {
                neighbours.add(bottom);
            }
        }
        if (thisCell.getI() > 0) {
            left = cells[thisCell.getI() - 1][thisCell.getJ()];
            if (!left.isVisited()) {
                neighbours.add(left);
            }
        }

        if (!neighbours.isEmpty()) {
            int randN = rand.nextInt(neighbours.size());
            return neighbours.get(randN);
        } else {
            return null;
        }
    }


}
