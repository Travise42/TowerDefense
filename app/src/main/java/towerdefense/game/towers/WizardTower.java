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
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConv;

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
            RANGE);

    final private static float[][] ENTITY_HEIGHT = {
            { 0.35f },
            { 0.7f, 1, 1.6f, 1.6f },
            { 0.6f, 0.7f, 0.8f, 1.2f }};

    final private static float ENTITY_SIZE = 0.8f;

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
    public void drawTower( Graphics g, boolean selected ) {
        drawTower( g );

        if (selected)
            drawTowerHighlight(g);
    }

    @Override
    public void drawEntity( Graphics g, boolean selected ) {
        drawEntity(g);
    }

    private void drawEntity(Graphics g) {
        int size = (int) ( Game.instance.map.getTileSize() * ENTITY_SIZE );

        float entityHeight = ENTITY_HEIGHT[path][Math.max(0, tier-1)];
        int xOffset = (int) MapConv.cordToScreenCord(MapConv.gridToCord(getSize() / 2));
        int yOffset = (int) MapConv.cordToScreenCord(MapConv.gridToCord( getSize() - entityHeight));

        int x = getScreenX() + xOffset;
        int y = getScreenY() + yOffset;

        float dxd = MapConv.cordToScreenCord(0.5f) * dx / distance;
        float dyd = MapConv.cordToScreenCord(0.5f) * dy / distance;

        // Body
        drawPart(g, x, y, size);

        // Hands
        if( dx > 0 ) {
            drawPart(g, x + 3 * (int) (3*dxd + 2*dyd), y + 2 * (int) (3*dyd - 2*dxd), size/3);
            drawPart(g, x + 3 * (int) (3*dxd - 2*dyd), y + 2 * (int) (3*dyd + 2*dxd), size/3);
            return;
        }
        drawPart(g, x + 3 * (int) (3*dxd - 2*dyd), y + 2 * (int) (3*dyd + 2*dxd), size/3);
        drawPart(g, x + 3 * (int) (3*dxd + 2*dyd), y + 2 * (int) (3*dyd - 2*dxd), size/3);
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
            float projectionFactor = Map.TILE_SIZE / upgradeInfo.getProjectileSpeed(path, tier) * Calc.pythag(
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
        int xOffset = MapConv.gridToCord(getSize() / 2);
        int yOffset = MapConv.gridToCord(getSize() - entityHeight);

        Game.instance.ph.add(new Projectile(
                spellImage,
                x + xOffset,
                y + yOffset,
                dx / distance,
                dy / distance,
                upgradeInfo.getDamage(path, tier), upgradeInfo.getPierce(path, tier), upgradeInfo.getRange(path, tier), upgradeInfo.getProjectileSpeed(path, tier)));
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
