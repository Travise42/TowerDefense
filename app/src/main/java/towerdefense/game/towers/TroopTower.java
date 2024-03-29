package towerdefense.game.towers;

import java.awt.Graphics;

public class TroopTower extends Tower {

    final private static String TOWER_ID = "troop_tower";

    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

    final private static String[][] NAME = {
            { "Archer Tower" },
            { "Sharper Arrows", "Faster Shooting", "Even Faster Shooting", "Elite Firing" },
            { "Greater Range", "Double Shot", "triple Shot", "Special Arrows" } };
    final private static int[][] COST = {
            { 500 },
            { 400, 1000, 1200, 3200 },
            { 200, 1500, 2000, 5000 } };
    final private static int[][] HEALTH = {
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
    final private static int[][] RANGE = {
            { 4 },
            { 4, 4, 4, 4 },
            { 4, 4, 4, 4 } };

    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(
        TOWER_ID,
        PATHS,
        TIERS,
        NAME,
        COST,
        HEALTH,
        DAMAGE,
        PIERCE,
        RELOAD_TIME,
        PROJECTILE_SPEED,
        RANGE);

    public TroopTower( int column, int row ) {
        super( column, row, TOWER_ID );
    }

    public TroopTower() { super(); }

    @Override
    public void drawTower( Graphics g, boolean selected ) {
        drawTower( g );
    }

    @Override
    public void drawEntity( Graphics g, boolean selected ) {
        
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
    public TowerUpgrade getUpgradeInfo() {
        return upgradeInfo;
    }

    @Override
    public TowerGraphics getGraphics() {
        return graphics;
    }
    
}
