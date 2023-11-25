package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class TroopTower extends Tower {

    final private static int width = 2;
    final private static int height = 2;

    final private static String IMG_PATH = "map/towers/troop_tower/example.png";

    public TroopTower( Game game, int column, int row ) {
        super( game, column, row, width, height, IMG_PATH );
    }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }
    
}
