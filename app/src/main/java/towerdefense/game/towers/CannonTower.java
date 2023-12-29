package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class CannonTower extends Tower {

    final private static String TOWER_ID = "cannon_tower";
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade( TOWER_ID );
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID );

    public CannonTower( Game game, int column, int row ) {
        super( game, column, row, TOWER_ID );
    }

    public CannonTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public CannonTower createNew( Game game, int column, int row ) {
        return new CannonTower( game, column, row );
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public TowerUpgrade getUpgradeInfo() {
        return upgradeInfo;
    }

    @Override
    public TowerGraphics getGraphics() {
        return graphics;
    }
    
}