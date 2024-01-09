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

public class ArcherTower extends Tower {

    final private static String[] entities = { "arrow", "pulled_bow", "bow" };
    final private static int ARROW = 0;
    final private static int PULLED_BOW = 1;
    final private static int FIRED_BOW = 2;

    final private static int DEFAULT_DX = -5;
    final private static int DEFAULT_DY = 1;
    final private static float DEFAULT_DISTANCE = Calc.pythag(DEFAULT_DX, DEFAULT_DY);

    final private static int DAMAGE = 5;
    final private static int PIERCE = 5;
    final private static int RELOAD_TIME = 40;
    final private static int PROJECTILE_SPEED = 20;
    final private static int PROJECTILE_LIFETIME = 30;

    final private static int RANGE = 6;

    final private static String TOWER_ID = "archer_tower";
    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics(TOWER_ID, PATHS, TIERS, entities);

    private BufferedImage arrowImage;
    private BufferedImage bowImage;

    private boolean firing = false;
    private int fireTick = 0;

    private float dx;
    private float dy;
    private float distance;

    public ArcherTower(int column, int row) {
        super(column, row, TOWER_ID);
    }

    public ArcherTower() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        drawTower(g);
        drawEntity(g);
        drawBowAndArrow(g);
    }

    private void drawEntity(Graphics g) {
        int size = (int) Game.instance.map.getTileSize() * 4 / 5;
        int offset = MapConversions.gridToCord(getSize() / 2);
        int x = getScreenX() + offset;
        int y = getScreenY() + offset;

        // Body
        drawPart(g, x, y, size);

        // Hands
        drawPart(g, x - (int) (15 * dx / distance), y - (int) (15 * dy / distance), size / 3);
        drawPart(g, x + (int) (30 * dx / distance), y + (int) (30 * dy / distance), size / 3);
    }

    private void drawPart(Graphics g, int x, int y, int size) {
        g.setColor(new Color(227, 172, 34));
        g.fillOval(x - size / 2, y - size / 2, size, size);

        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.setColor(new Color(50, 30, 10));
        g.drawOval(x - size / 2, y - size / 2, size, size);
    }

    private void drawBowAndArrow(Graphics g) {
        int x = getScreenX();
        int y = getScreenY();

        int commonOffset = MapConversions.gridToCord(getSize() - 1) / 2;
        int xOffset = (int) (20 * dx / distance) + commonOffset;
        int yOffset = (int) (20 * dy / distance) + commonOffset;

        // Draw bow
        g.drawImage(bowImage, x + xOffset, y + yOffset, Game.instance.panel);

        // Draw arrow
        if (!firing)
            g.drawImage(arrowImage, x + xOffset, y + yOffset, Game.instance.panel);
    }

    @Override
    public void update() {
        // Increase fireTick if firing, otherwise, decrease fireTick
        if (firing) {
            // Stop firing after 'projectile_lifetime' frames
            if (++fireTick >= RELOAD_TIME)
                firing = false;
        } else {
            // Decrease fireTick if positive
            if (fireTick > 0)
                fireTick--;
        }

        // Rotate arrow towards first enemy in range
        Enemy firstEnemyInRange = getFirstEnemyInRange(RANGE);

        if (firstEnemyInRange != null) {
            float projectionFactor = 2 * PROJECTILE_SPEED * firstEnemyInRange.speed * Calc.pythag(
                    getColumnsFrom(firstEnemyInRange, 0),
                    getRowsFrom(firstEnemyInRange, 0, -1));
            dx = getColumnsFrom(firstEnemyInRange, projectionFactor);
            dy = getRowsFrom(firstEnemyInRange, projectionFactor, -1);
            distance = Calc.pythag(dx, dy);
        }

        else {
            dx = DEFAULT_DX;
            dy = DEFAULT_DY;
            distance = DEFAULT_DISTANCE;
        }

        double angleToEnemy = Calc.getAngle(dx, dy);

        if (firing) {
            // Load rotated fired bow image
            bowImage = rotateImage(
                    resizeImage(
                            graphics.getEntityImage(FIRED_BOW),
                            (int) Game.instance.map.getTileSize(),
                            (int) Game.instance.map.getTileSize()),
                    angleToEnemy);
            return;
        }

        // Load rotated arrow image
        arrowImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(ARROW),
                        (int) Game.instance.map.getTileSize(),
                        (int) Game.instance.map.getTileSize()),
                angleToEnemy);

        // Load rotated pulled bow image
        bowImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(PULLED_BOW),
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
        int offset = MapConversions.gridToCord(getSize() / 2);

        float velocityFactor = PROJECTILE_SPEED / distance;

        Game.instance.ph.add(new Projectile(
                arrowImage,
                x + offset,
                y + offset,
                dx * velocityFactor,
                dy * velocityFactor,
                DAMAGE, PIERCE, PROJECTILE_LIFETIME));
    }

    @Override
    public ArcherTower createNew(int column, int row) {
        return new ArcherTower(column, row);
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
