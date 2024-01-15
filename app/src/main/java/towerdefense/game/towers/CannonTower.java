package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.flipImage;
import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.func.Calc;
import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConv;

public class CannonTower extends Tower {

    final private static String[] entities = { "cannon", "highlighted_cannon", "cannon_ball" };
    final private static int CANNON = 0;
    final private static int HIGHLIGHTED_CANNON = 1;
    final private static int CANNON_BALL = 2;

    final private static int DEFAULT_DX = -5;
    final private static int DEFAULT_DY = -1;
    final private static float DEFAULT_DISTANCE = Calc.pythag(DEFAULT_DX, DEFAULT_DY);

    final private static String TOWER_ID = "cannon_tower";

    final private static int PATHS = 2;
    final private static int TIERS = 4;
    final private static TowerGraphics graphics = new TowerGraphics(TOWER_ID, PATHS, TIERS, entities);

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
            { 2 },
            { 2, 2, 2, 2 },
            { 2, 2, 2, 2 } };
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

    private BufferedImage cannonImage;
    private BufferedImage highlightedCannonImage;
    private BufferedImage cannonBallImage;

    private boolean firing = false;
    private int fireTick = 0;

    private float dx;
    private float dy;
    private float distance;

    public CannonTower(int column, int row) {
        super(column, row, TOWER_ID);
    }

    public CannonTower() {
        super();
    }

    @Override
    public void drawTower(Graphics g, boolean selected) {
        drawTower(g);

        if (selected)
            drawTowerHighlight(g);
    }

    @Override
    public void drawEntity(Graphics g, boolean selected) {
        drawCannon(g, selected);
        drawWick(g);
    }

    private void drawCannon(Graphics g, boolean selected) {
        g.drawImage(selected ? highlightedCannonImage : cannonImage, getScreenX(), MapConv.yToViewY(getY()), Game.instance.panel);
    }

    private void drawWick(Graphics g) {

    }

    @Override
    public void update() {
        // Increase fireTick if firing
        if (firing) {
            // Stop firing after 'projectile_lifetime' frames
            if (++fireTick >= upgradeInfo.getReloadTime(path, tier)) {
                firing = false;
                fireTick = 0;
            }
        }

        // Rotate arrow towards first enemy in range
        Enemy firstEnemyInRange = getFirstEnemyInRange(upgradeInfo.getRange(path, tier));

        if (firstEnemyInRange != null) {
            float projectionFactor = Map.TILE_SIZE / upgradeInfo.getProjectileSpeed(path, tier) * Calc.pythag(
                    getColumnsFrom(firstEnemyInRange, 0),
                    getRowsFrom(firstEnemyInRange, 0));
            dx = getColumnsFrom(firstEnemyInRange, projectionFactor);
            dy = getRowsFrom(firstEnemyInRange, projectionFactor);
            distance = Calc.pythag(dx, dy);
        }

        else {
            dx = DEFAULT_DX;
            dy = DEFAULT_DY;
            distance = DEFAULT_DISTANCE;
        }

        double angleToEnemy = dx == 0 ? Math.PI / 2 : Math.atan(dy / dx);

        int cannonImageSize = (int) (Game.instance.map.getTileSize() * 2);
        cannonImage = rotateImage(
                resizeImage(
                        dx > 0 ? flipImage(graphics.getEntityImage(CANNON)) : graphics.getEntityImage(CANNON),
                        cannonImageSize,
                        cannonImageSize),
                angleToEnemy);
        highlightedCannonImage = rotateImage(
                resizeImage(
                        dx > 0 ? flipImage(graphics.getEntityImage(HIGHLIGHTED_CANNON)) : graphics.getEntityImage(HIGHLIGHTED_CANNON),
                        cannonImageSize,
                        cannonImageSize),
                angleToEnemy);

        if (!(fireTick > 0 || firstEnemyInRange == null))
            fire();
    }

    public void fire() {
        firing = true;
        fireTick = 0;
        
        float amplitude = MapConv.cordToScreenCord(2);
        float xRatio = dx / distance;
        float yRatio = dy / distance;

        int x = getX();
        int y = getY();
        int offset = MapConv.gridToCord(getSize() / 2);

        Game.instance.ph.add(new Projectile(
            cannonBallImage = resizeImage(
                graphics.getEntityImage(CANNON_BALL),
                (int) Game.instance.map.getTileSize(),
                (int) Game.instance.map.getTileSize()),
            x + offset + (int) (amplitude * xRatio),
            y + offset + (int) (amplitude * yRatio),
            xRatio,
            yRatio,
            upgradeInfo.getDamage(path, tier), upgradeInfo.getPierce(path, tier), upgradeInfo.getRange(path, tier), upgradeInfo.getProjectileSpeed(path, tier)));
    }

    @Override
    public CannonTower createNew(int column, int row) {
        return new CannonTower(column, row);
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
