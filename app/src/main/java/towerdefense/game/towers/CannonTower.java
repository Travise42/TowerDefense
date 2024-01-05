package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.func.ImageHandler;
import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.MapConversions;

public class CannonTower extends Tower {

    final private static String[] entities = { "cannon", "cannon_ball" };
    final private static int CANNON = 0;
    final private static int CANNON_BALL = 1;

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
        if (firing) {
            if (++fireTick >= 100)
                firing = false;
        } else {
            if (--fireTick < 0)
                fireTick = 0;
        }

        // Rotate arrow towards first enemy in range
        Enemy firstEnemyInRange = null;
        dx = -5;
        dy = 1;
        distance = 5.1f;
        for (Enemy enemy : Game.instance.map.enemies) {
            float temp_dx = MapConversions.cordToSoftGrid(enemy.getX() + 100f * enemy.speed * enemy.getVelocityX())
                    - (column + getSize() / 2);
            float temp_dy = MapConversions.cordToSoftGrid(enemy.getY() + 100f * enemy.speed * enemy.getVelocityY())
                    - (row - 1 + getSize() / 2);
            double temp_distance = Math.sqrt(temp_dx * temp_dx + temp_dy * temp_dy);

            if (temp_distance > 5)
                continue;

            if (firstEnemyInRange != null && firstEnemyInRange.getX() >= enemy.getX())
                continue;

            firstEnemyInRange = enemy;

            dx = temp_dx;
            dy = temp_dy;
            distance = (float) temp_distance;
        }

        if (dx == 0)
            dx = 0.0001f;
        double angleToEnemy = Math.atan(dy / dx);
        if (dx > 0)
            angleToEnemy += Math.PI;

        cannonImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(CANNON),
                        cannonImageSize,
                        cannonImageSize),
                angleToEnemy);

        if ( firing || firstEnemyInRange == null || fireTick > 0)
            return;

        fire();

        return;
    }

    public void fire() {
        firing = true;
        fireTick = 0;

        int x = getX() + MapConversions.gridToCord(getSize() / 2);
        int y = getY() + MapConversions.gridToCord(getSize() / 2);

        Game.instance.ph.add(new Projectile(x + (int) ( dx * 30 / distance ), y + (int) ( dy * 30 / distance ), dx * 15 / distance, dy * 15 / distance, cannonBallImage, 50));
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
