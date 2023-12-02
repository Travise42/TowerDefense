package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class ArcherTower extends Tower {

    final private static String TOWER_ID = "archer_tower";

    public static TowerUpgrade upgradeInfo = new TowerUpgrade( TOWER_ID );

    public ArcherTower( Game game, int column, int row ) {
        super( game, column, row, TOWER_ID );
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
