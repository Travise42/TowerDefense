package towerdefense.game.towers;

import java.awt.Graphics;

public class WizardTower extends Tower {

    final private static String TOWER_ID = "wizard_tower";

    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, null );

    final private static String[][] UPGRADE_NAMES = {
            { "Wizard Tower" },
            { "Bigger Cast", "Intense Magic", "Powerful Magic", "Keen Sorcerer" },
            { "Faster Cast", "Casting Proficiency", "Seeking Projectile", "Casting Mastery" } };
    final private static int[][] UPGRADE_COSTS = {
            { 500 },
            { 400, 1000, 1200, 3200 },
            { 200, 1500, 2000, 5000 } };

    final private static int[][] DAMAGE = {
            { 5 },
            { 5, 5, 5, 5 },
            { 5, 5, 5, 5 } };
    final private static int[][] PIERCE = {
            { 5 },
            { 5, 5, 5, 5 },
            { 5, 5, 5, 5 } };
    final private static int[][] RELOAD_TIME = {
            { 40 },
            { 40, 40, 40, 40 },
            { 40, 40, 40, 40 } };
    final private static int[][] PROJECTILE_SPEED = {
            { 10 },
            { 10, 10, 10, 10 },
            { 10, 10, 10, 10 } };
    final private static int[][] PROJECTILE_LIFETIME = {
            { 30 },
            { 30, 30, 30, 30 },
            { 30, 30, 30, 30 } };
    final private static int[][] RANGE = {
            { 6 },
            { 6, 6, 6, 6 },
            { 6, 6, 6, 6 } };

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
    public TowerUpgrade getUpgradeInfo() {
        return upgradeInfo;
    }

    @Override
    public TowerGraphics getGraphics() {
        return graphics;
    }
    
}
