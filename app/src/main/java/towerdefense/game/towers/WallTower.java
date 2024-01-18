package towerdefense.game.towers;

import java.awt.Graphics;

public class WallTower extends Tower {

    final private static String TOWER_ID = "wall_tower";

    final private static int PATHS = 1;
    final private static int TIERS = 2;
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

    final private static String[][] NAME = {
            { "Archer Tower" },
            { "Sharper Arrows", "Faster Shooting" }};
    final private static int[][] COST = {
            { 100 },
            { 400, 1000 }};
    final private static int[][] HEALTH = {
            { 500 },
            { 400, 1000 }};

    final private static int[][] DAMAGE = {
            { 10 },
            { 10, 10 }};
    final private static int[][] PIERCE = {
            { 3 },
            { 3, 3 }};
    final private static int[][] RELOAD_TIME = {
            { 50 },
            { 50, 50 }};
    final private static int[][] PROJECTILE_SPEED = {
            { 10 },
            { 10, 10}};
    final private static int[][] RANGE = {
            { 4 },
            { 4, 4}};

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

    public WallTower( int column, int row ) {
        super( column, row, TOWER_ID );
    }

    public WallTower() {
        super();
    }

    @Override
    public void drawTower( Graphics g, boolean selected ) {
        drawTower( g );
        
        if (selected)
            drawTowerHighlight(g);
    }

    @Override
    public void drawEntity( Graphics g, boolean selected ) {
        
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
