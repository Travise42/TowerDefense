package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public abstract class Tower {

    protected int column;
    protected int row;

    protected int columnspan;
    protected int rowspan;

    protected Game game;

    protected Tower( Game game, int column, int row, int columnspan, int rowspan ) {
        this.game = game;
        
        this.column = column;
        this.row = row;
        this.columnspan = columnspan;
        this.rowspan = rowspan;
    }

    public abstract void draw( Graphics g );
    
}
