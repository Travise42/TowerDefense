package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class WallTower extends Tower {

    final private static String TOWER_ID = "wall_tower";
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, null, false );

    public WallTower( int column, int row ) {
        super( column, row, TOWER_ID );
    }

    public WallTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public void update() {
        
    }

    @Override
    public WallTower createNew( int column, int row ) {
        return new WallTower( column, row );
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public TowerUpgrade getUpgradeInfo() {
        return null;
    }

    @Override
    public TowerGraphics getGraphics() {
        return graphics;
    }
    
}
