package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class WizardTower extends Tower {

    final private static String TOWER_ID = "wizard_tower";
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade( TOWER_ID );
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, null );

    public WizardTower( Game game, int column, int row ) {
        super( game, column, row, TOWER_ID );
    }

    public WizardTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public void update() {
        
    }

    @Override
    public WizardTower createNew( Game game, int column, int row ) {
        return new WizardTower( game, column, row );
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
