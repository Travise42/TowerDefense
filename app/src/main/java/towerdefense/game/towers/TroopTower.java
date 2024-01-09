package towerdefense.game.towers;

import java.awt.Graphics;

public class TroopTower extends Tower {

    final private static String TOWER_ID = "troop_tower";
    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

    public TroopTower( int column, int row ) {
        super( column, row, TOWER_ID );
    }

    public TroopTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public void update() {
        
    }

    @Override
    public TroopTower createNew( int column, int row ) {
        return new TroopTower( column, row );
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
