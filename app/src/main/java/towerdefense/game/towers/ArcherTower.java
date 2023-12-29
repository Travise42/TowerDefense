package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class ArcherTower extends Tower {

    final private static String TOWER_ID = "archer_tower";
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade( TOWER_ID );
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID );

    public ArcherTower( Game game, int column, int row ) {
        super( game, column, row, TOWER_ID );
    }

    public ArcherTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
        drawEntity( g );
        drawArrow( g );
        drawBow( g );
    }

    private void drawEntity( Graphics g ) {

    }

    private void drawArrow( Graphics g ) {

    }

    private void drawBow( Graphics g ) {

    }

    @Override
    public ArcherTower createNew( Game game, int column, int row ) {
        return new ArcherTower( game, column, row );
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
