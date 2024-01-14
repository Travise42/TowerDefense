package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import towerdefense.func.Calc;
import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.MapConversions;

public class WizardTower extends Tower {

    final private static String TOWER_ID = "wizard_tower";

    final private static String[] entities = {"star", "fire"};
    final private static int STAR = 0;
    final private static int FIRE = 1;

    final private static int DEFAULT_DX = -5;
    final private static int DEFAULT_DY = 1;
    final private static float DEFAULT_DISTANCE = Calc.pythag(DEFAULT_DX, DEFAULT_DY);

    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerGraphics graphics = new TowerGraphics( TOWER_ID, PATHS, TIERS, entities );

    final private static String[][] NAME = {
            { "Wizard Tower" },
            { "Bigger Cast", "Intense Magic", "Powerful Magic", "Keen Sorcerer" },
            { "Faster Cast", "Casting Proficiency", "Seeking Projectile", "Casting Mastery" } };
    final private static int[][] COST = {
            { 500 },
            { 400, 1000, 1200, 3200 },
            { 200, 1500, 2000, 5000 } };
    final private static int[][] HEALTH = {
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
            { 2 },
            { 2, 2, 2, 2 },
            { 2, 2, 4, 4 } };
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
            NAME,
            HEALTH,
            COST,
            DAMAGE,
            PIERCE,
            RELOAD_TIME,
            PROJECTILE_SPEED,
            PROJECTILE_LIFETIME,
            RANGE);

    final private static float[][] ENTITY_HEIGHT = {
            { 0.35f },
            { 0.7f, 1, 1.6f, 1.6f },
            { 0.6f, 0.7f, 0.8f, 1.2f }};

    private BufferedImage spellImage;

    private float dx;
    private float dy;
    private float distance;

    private boolean firing;
    private int fireTick;

    public WizardTower( int column, int row ) {
        super( column, row, TOWER_ID );
    }

    public WizardTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );

        drawEntity(g);
    }

    private void drawEntity(Graphics g) {
        int size = (int) Game.instance.map.getTileSize() * 4 / 5;

        float entityHeight = ENTITY_HEIGHT[path][Math.max(0, tier-1)];
        int xOffset = (int) MapConversions.cordToScreenCord(MapConversions.gridToCord(getSize() / 2));
        int yOffset = (int) MapConversions.cordToScreenCord(MapConversions.gridToCord( getSize() - entityHeight));

        int x = getScreenX() + xOffset;
        int y = getScreenY() + yOffset;

        // Body
        drawPart(g, x, y, size);

        float dxd = 5 * dx / distance;
        float dyd = 5 * dy / distance;

        // Hands
        drawPart(g, x + 5*dxd + 3*dyd, y + 2*dyd - 2*dxd, size / 3);
        drawPart(g, x + 5*dxd - 3*dyd, y + 2*dyd + 2*dxd, size / 3);
    }

    private void drawPart(Graphics g, float x, float y, int size) {
        g.setColor(new Color(227, 172, 34));
        g.fillOval((int) x - size / 2, (int) y - size / 2, size, size);

        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.setColor(new Color(50, 30, 10));
        g.drawOval((int) x - size / 2, (int) y - size / 2, size, size);
    }

    @Override
    public void update() {
        // Increase fireTick if firing, otherwise, decrease fireTick
        if (firing) {
            // Stop firing after 'projectile_lifetime' frames
            if (++fireTick >= upgradeInfo.getReloadTime(path, tier))
                firing = false;
        } else {
            // Decrease fireTick if positive
            if (fireTick > 0)
                fireTick--;
        }

        // Rotate arrow towards first enemy in range
        Enemy firstEnemyInRange = getFirstEnemyInRange(upgradeInfo.getRange(path, tier));


        float entityHeight = ENTITY_HEIGHT[path][Math.max(0, tier-1)];
        float yOffset = entityHeight;
        if (firstEnemyInRange != null) {
            float projectionFactor = 500 * firstEnemyInRange.speed / upgradeInfo.getProjectileSpeed(path, tier) * Calc.pythag(
                    getColumnsFrom(firstEnemyInRange, 0),
                    getRowsFrom(firstEnemyInRange, 0, -yOffset));
            dx = getColumnsFrom(firstEnemyInRange, projectionFactor);
            dy = getRowsFrom(firstEnemyInRange, projectionFactor, -yOffset);
            distance = Calc.pythag(dx, dy);
        }

        else {
            dx = DEFAULT_DX;
            dy = DEFAULT_DY;
            distance = DEFAULT_DISTANCE;
        }

        double angleToEnemy = Calc.getAngle(dx, dy);

        int spell = (tier > 2 && path == 1) ? FIRE : STAR;

        // Load rotated arrow image
        spellImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(spell),
                        (int) Game.instance.map.getTileSize(),
                        (int) Game.instance.map.getTileSize()),
                angleToEnemy);

        // Fire when ready
        if (firstEnemyInRange != null && fireTick <= 0)
            fire();
    }

    public void fire() {
        firing = true;
        fireTick = 0;

        int x = getX();
        int y = getImageY();

        float entityHeight = ENTITY_HEIGHT[path][Math.max(0, tier-1)];
        int xOffset = MapConversions.gridToCord(getSize() / 2);
        int yOffset = MapConversions.gridToCord(getSize() - entityHeight);

        float velocityFactor = upgradeInfo.getProjectileSpeed(path, tier) / distance;

        System.out.println( Calc.pythag( dx/distance, dy/distance ) );
        Game.instance.ph.add(new Projectile(
                spellImage,
                x + xOffset,
                y + yOffset,
                dx * velocityFactor,
                dy * velocityFactor,
                upgradeInfo.getDamage(path, tier), upgradeInfo.getPierce(path, tier), upgradeInfo.getProjectileLifetime(path, tier)));
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
