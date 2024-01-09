package towerdefense.game.towers;

import java.awt.Graphics;

public class WallTower extends Tower {

    final private static String TOWER_ID = "wall_tower";
    final private static int PATHS = 1;
    final private static int TIERS = 2;
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

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
