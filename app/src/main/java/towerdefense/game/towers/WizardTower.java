package towerdefense.game.towers;

import java.awt.Graphics;

public class WizardTower extends Tower {

    final private static String TOWER_ID = "wizard_tower";
    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

    public WizardTower( int column, int row ) {
        super( column, row, TOWER_ID );
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
    public WizardTower createNew( int column, int row ) {
        return new WizardTower( column, row );
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public int getPaths() {
        return PATHS;
    }

    @Override
    public int getTiers() {
        return TIERS;
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
