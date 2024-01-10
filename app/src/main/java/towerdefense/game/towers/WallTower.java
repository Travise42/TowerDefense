package towerdefense.game.towers;

import java.awt.Graphics;

public class WallTower extends Tower {

    final private static String TOWER_ID = "wall_tower";

    final private static int PATHS = 1;
    final private static int TIERS = 2;
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

    final private static String[][] UPGRADE_NAMES = {
            { "Archer Tower" },
            { "Sharper Arrows", "Faster Shooting", "Even Faster Shooting", "Elite Firing" },
            { "Greater Range", "Double Shot", "triple Shot", "Special Arrows" } };
    final private static int[][] UPGRADE_COSTS = {
            { 500 },
            { 400, 1000, 1200, 3200 },
            { 200, 1500, 2000, 5000 } };

    final private static int[][] DAMAGE = {
            { 10 },
            { 10, 10, 10, 10 },
            { 10, 10, 10, 10 } };
    final private static int[][] PIERCE = {
            { 3 },
            { 3, 3, 3, 3 },
            { 3, 3, 3, 3 } };
    final private static int[][] RELOAD_TIME = {
            { 50 },
            { 50, 50, 50, 50 },
            { 50, 50, 50, 50 } };
    final private static int[][] PROJECTILE_SPEED = {
            { 10 },
            { 10, 10, 10, 10 },
            { 10, 10, 10, 10 } };
    final private static int[][] PROJECTILE_LIFETIME = {
            { 40 },
            { 40, 40, 40, 40 },
            { 40, 40, 40, 40 } };
    final private static int[][] RANGE = {
            { 4 },
            { 4, 4, 4, 4 },
            { 4, 4, 4, 4 } };

    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(
        TOWER_ID,
        PATHS,
        TIERS,
        UPGRADE_NAMES,
        UPGRADE_COSTS,
        DAMAGE,
        PIERCE,
        RELOAD_TIME,
        PROJECTILE_SPEED,
        PROJECTILE_LIFETIME,
        RANGE);

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
        return upgradeInfo;
    }

    @Override
    public TowerGraphics getGraphics() {
        return graphics;
    }
    
}
