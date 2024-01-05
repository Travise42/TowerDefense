package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.MapConversions;

public class ArcherTower extends Tower {

    final private static String[] entities = { "arrow", "pulled_bow", "bow" };
    final private static int ARROW = 0;
    final private static int PULLED_BOW = 1;
    final private static int FIRED_BOW = 2;

    final private static String TOWER_ID = "archer_tower";
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics(TOWER_ID, entities);

    private BufferedImage arrowImage;
    private BufferedImage pulledBowImage;
    private BufferedImage firedBowImage;

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
        drawBow(g);
        drawArrow(g);
    }

    private void drawEntity(Graphics g) {
        int size = (int) Game.instance.map.getTileSize() * 4 / 5;
        int offset = MapConversions.gridToCord(getSize() / 2);
        int x = getScreenX() + offset;
        int y = getScreenY() + offset;

        // Body
        drawPart(g, x, y, size);

        // Hands
        drawPart(g, x - (int) (15 * dx / distance), y - (int) (15 * dy / distance), size/3);
        drawPart(g, x + (int) (30 * dx / distance), y + (int) (30 * dy / distance), size/3);
    }

    private void drawPart(Graphics g, int x, int y, int size) {
        g.setColor(new Color(227, 172, 34));
        g.fillOval(x - size / 2, y - size / 2, size, size);

        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.setColor(new Color(50, 30, 10));
        g.drawOval(x - size / 2, y - size / 2, size, size);
    }

    private void drawArrow(Graphics g) {
        if (firing)
            return;

        int x = getScreenX() + MapConversions.gridToCord(getSize() - 1) / 2;
        int y = getScreenY() + MapConversions.gridToCord(getSize() - 1) / 2;

        g.drawImage(arrowImage, x + (int) (20 * dx / distance), y + (int) (20 * dy / distance), Game.instance.panel);
    }

    private void drawBow(Graphics g) {
        int x = getScreenX() + MapConversions.gridToCord(getSize() - 1) / 2;
        int y = getScreenY() + MapConversions.gridToCord(getSize() - 1) / 2;

        if (firing) {
            g.drawImage( firedBowImage, x + (int) (20 * dx / distance), y + (int) (20 * dy / distance), Game.instance.panel );
            return;
        }
        g.drawImage( pulledBowImage, x + (int) (20 * dx / distance), y + (int) (20 * dy / distance), Game.instance.panel );
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
            float temp_dx = MapConversions.cordToSoftGrid(enemy.getX() + 300f * enemy.speed * enemy.getVelocityX())
                    - (column + getSize() / 2);
            float temp_dy = MapConversions.cordToSoftGrid(enemy.getY() + 300f * enemy.speed * enemy.getVelocityY())
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

        if (!firing) {
            arrowImage = rotateImage(
                    resizeImage(
                            graphics.getEntityImage(ARROW),
                            (int) Game.instance.map.getTileSize(),
                            (int) Game.instance.map.getTileSize()),
                    angleToEnemy);
            pulledBowImage = rotateImage(
                    resizeImage(
                            graphics.getEntityImage(PULLED_BOW),
                            (int) Game.instance.map.getTileSize(),
                            (int) Game.instance.map.getTileSize()),
                    angleToEnemy);

            if (firstEnemyInRange == null || fireTick > 0)
                return;

            fire();

            return;
        }

        firedBowImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(FIRED_BOW),
                        (int) Game.instance.map.getTileSize(),
                        (int) Game.instance.map.getTileSize()),
                angleToEnemy);
    }

    public void fire() {
        firing = true;
        fireTick = 0;

        int x = getX() + MapConversions.gridToCord(getSize() / 2);
        int y = getImageY() + MapConversions.gridToCord(getSize() / 2);

        Game.instance.ph.add(new Projectile(x, y, dx * 20 / distance, dy * 20 / distance, arrowImage, 50));
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
    public TowerUpgrade getUpgradeInfo() {
        return upgradeInfo;
    }

    @Override
    public TowerGraphics getGraphics() {
        return graphics;
    }

}
