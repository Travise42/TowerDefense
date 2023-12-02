package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class WallTower extends Tower {

    final private static String TOWER_ID = "wall_tower";

    public static TowerUpgrade upgradeInfo = new TowerUpgrade( TOWER_ID );

    public WallTower( Game game, int column, int row ) {
        super( game, column, row, TOWER_ID );
    }

    public WallTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public WallTower createNew( Game game, int column, int row ) {
        return new WallTower( game, column, row );
    }

    @Override
    public int getSize() {
        return 1;
    }
    
}
