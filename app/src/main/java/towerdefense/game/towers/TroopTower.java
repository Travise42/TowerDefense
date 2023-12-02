package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class TroopTower extends Tower {

    final private static String TOWER_ID = "troop_tower";

    public static TowerUpgrade upgradeInfo = new TowerUpgrade( TOWER_ID );

    public TroopTower( Game game, int column, int row ) {
        super( game, column, row, TOWER_ID );
    }

    public TroopTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public TroopTower createNew( Game game, int column, int row ) {
        return new TroopTower( game, column, row );
    }

    @Override
    public int getSize() {
        return 2;
    }
    
}
