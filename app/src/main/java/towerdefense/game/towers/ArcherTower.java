package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class ArcherTower extends Tower {

    final private static String IMG_PATH = "map/towers/archer_tower/example.png";

    public ArcherTower( Game game, int column, int row ) {
        super( game, column, row, IMG_PATH );
    }

    public ArcherTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public ArcherTower createNew( Game game, int column, int row ) {
        return new ArcherTower( game, column, row );
    }

    @Override
    public int getSize() {
        return 2;
    }
    
}
