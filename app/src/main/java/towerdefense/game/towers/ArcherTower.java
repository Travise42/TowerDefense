package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.MapConversions;

public class ArcherTower extends Tower {

    final private static String[] entities = { "arrow" };
    final private static int ARROW = 0;

    final private static String TOWER_ID = "archer_tower";
    final private static TowerUpgrade upgradeInfo = new TowerUpgrade(TOWER_ID);
    final private static TowerGraphics graphics = new TowerGraphics(TOWER_ID, entities);

    private BufferedImage arrowImage;

    public ArcherTower(Game game, int column, int row) {
        super(game, column, row, TOWER_ID);
    }

    public ArcherTower() {
        super();
    }

    @Override
    public void draw(Graphics g) {
        drawTower(g);
        drawEntity(g);
        drawArrow(g);
        drawBow(g);
    }

    private void drawEntity(Graphics g) {

    }

    private void drawArrow(Graphics g) {
        int x = getX() + (getSize() - 1) * (int) Game.instance.map.getTileSize() / 2;
        int y = getY() + (getSize() - 1) * (int) Game.instance.map.getTileSize() / 2;

        g.drawImage(arrowImage, x, y, game.panel);
    }

    private void drawBow(Graphics g) {

    }

    @Override
    public void update() {
        Enemy firstEnemyInRange = null;
        float dx = -5, dy = 1;
        for (Enemy enemy : Game.instance.map.enemies) {
            float temp_dx = MapConversions.cordToSoftGrid( enemy.getX() ) - (column + getSize()/2);
            float temp_dy = MapConversions.cordToSoftGrid( enemy.getY() ) - (row + getSize()/2);
            double distance = Math.sqrt( temp_dx*temp_dx + temp_dy*temp_dy );

            if ( distance > 5 ) continue;

            if (firstEnemyInRange == null || firstEnemyInRange.getX() < enemy.getX()) {
                firstEnemyInRange = enemy;

                dx = temp_dx;
                dy = temp_dy;
            }
        }

        if ( dx == 0 ) dx = 0.0001f;
        double angleToEnemy = Math.atan( dy / dx );
        if ( dx > 0 ) angleToEnemy += Math.PI;

        arrowImage = rotateImage(
                resizeImage(
                        graphics.getEntityImage(ARROW),
                        (int) Game.instance.map.getTileSize(),
                        (int) Game.instance.map.getTileSize()),
                angleToEnemy);
    }

    @Override
    public ArcherTower createNew(Game game, int column, int row) {
        return new ArcherTower(game, column, row);
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
