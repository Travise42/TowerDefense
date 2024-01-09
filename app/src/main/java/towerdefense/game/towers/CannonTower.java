package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.func.Calc;
import towerdefense.func.ImageHandler;
import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.MapConversions;

public class CannonTower extends Tower {

    final private static String[] entities = { "cannon", "cannon_ball" };
    final private static int CANNON = 0;
    final private static int CANNON_BALL = 1;

    final private static int DEFAULT_DX = -5;
    final private static int DEFAULT_DY = -1;
    final private static float DEFAULT_DISTANCE = Calc.pythag(DEFAULT_DX, DEFAULT_DY);

    final private static int RELOAD_TIME = 50;
    final private static int PROJECTILE_SPEED = 10;
    final private static int PROJECTILE_LIFETIME = 40;

    final private static int RANGE = 4;

    final private static String TOWER_ID = "cannon_tower";
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics(TOWER_ID, entities);

    private BufferedImage cannonImage;
    private int cannonImageSize;
    private BufferedImage cannonBallImage;

    private boolean firing = false;
    private int fireTick = 0;

    private float dx;
    private float dy;
    private float distance;

    public CannonTower(int column, int row) {
        super(column, row, TOWER_ID);

        cannonImageSize = (int) (Game.instance.map.getTileSize() * 2);
        cannonBallImage = resizeImage(graphics.getEntityImage(CANNON_BALL),
                (int) Game.instance.map.getTileSize(),
                (int) Game.instance.map.getTileSize());
    }

    public CannonTower() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        drawTower(g);
        drawCannon(g);
        drawWick(g);
    }

    private void drawCannon(Graphics g) {
        g.drawImage(cannonImage, getScreenX(), MapConversions.yToViewY(getY()), Game.instance.panel);
    }

    private void drawWick(Graphics g) {

    }

    @Override
    public void update() {
        // Increase fireTick if firing
        if (firing) {
            // Stop firing after 'projectile_lifetime' frames
            if (++fireTick >= RELOAD_TIME) {
                firing = false;
                fireTick = 0;
            }
        }

        // Rotate arrow towards first enemy in range
        Enemy firstEnemyInRange = getFirstEnemyInRange(RANGE);

        if (firstEnemyInRange != null) {
            float projectionFactor = 2 * PROJECTILE_SPEED * firstEnemyInRange.speed * Calc.pythag(
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

        cannonImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(CANNON),
                        cannonImageSize,
                        cannonImageSize),
                Calc.getAngle(dx, dy));

        if (!(fireTick > 0 || firstEnemyInRange == null))
            fire();
    }

    public void fire() {
        firing = true;
        fireTick = 0;

        int x = getX() + MapConversions.gridToCord(getSize() / 2);
        int y = getY() + MapConversions.gridToCord(getSize() / 2);

        Game.instance.ph.add(new Projectile(x + (int) (dx * 30 / distance), y + (int) (dy * 30 / distance),
                dx * 15 / distance, dy * 15 / distance, cannonBallImage, PROJECTILE_LIFETIME));
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
