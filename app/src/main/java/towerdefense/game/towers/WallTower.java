package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class WallTower extends Tower {

    final private static int width = 1;
    final private static int height = 1;

    final private static String IMG_PATH = "";

    public WallTower( Game game, int column, int row ) {
        super( game, column, row, width, height, IMG_PATH );
    }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }
    
}