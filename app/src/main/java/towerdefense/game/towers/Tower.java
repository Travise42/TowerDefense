package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;

import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConv;

public abstract class Tower {

    final private static int HIGHLIGHT_BORDER_THICKNESS = 4;

    protected int column;
    protected int row;

    protected float damageAmount;

    protected BufferedImage image;
    protected BufferedImage highlight;

    protected int path;
    protected int tier;

    // Empty tower
    protected Tower() {
        tier = 0;
        path = 0;
    }

    protected Tower(int column, int row, String tower_id) {
        this.column = column;
        this.row = row;

        this.tier = 0;
        this.path = 0;

        this.damageAmount = 0;

        resize();
    }

    public void select() {
        loadHighlight();
    }

    protected void drawTower(Graphics g) {
        g.drawImage(image, getScreenX(), getScreenY(), Game.instance.panel);
    }

    protected void drawTowerHighlight(Graphics g) {
        g.drawImage(highlight, getScreenX() - HIGHLIGHT_BORDER_THICKNESS, getScreenY() - HIGHLIGHT_BORDER_THICKNESS,
                Game.instance.panel);
    }

    public void drawRange(Graphics g) {
        int size = (int) MapConv.cordToScreenCord(MapConv.gridToCord(getUpgradeInfo().getRange(getPath(), getTier())));
        int offset = (int) MapConv.cordToScreenCord(MapConv.gridToCord((float) getSize() / 2));

        g.setColor(new Color( 50, 50, 50, 50 ));
        g.fillOval(MapConv.xToViewX(getX()) + offset - size,
                MapConv.yToViewY(getY()) + offset - size,
                2*size,
                2*size);
    }

    protected void loadHighlight() {
        // Create scaled copy of tower
        highlight = new BufferedImage(
                image.getWidth() + HIGHLIGHT_BORDER_THICKNESS * 2,
                image.getHeight() + HIGHLIGHT_BORDER_THICKNESS * 2,
                BufferedImage.TYPE_INT_ARGB);

        // Create highlight
        Graphics2D g2d = highlight.createGraphics();
        g2d.setStroke(new BasicStroke(HIGHLIGHT_BORDER_THICKNESS));
        g2d.setColor(Color.WHITE);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (isOpaque(x, y) && isEdgePixel(x, y))
                    g2d.drawRect(x + HIGHLIGHT_BORDER_THICKNESS, y + HIGHLIGHT_BORDER_THICKNESS, 1, 1);
            }
        }
        g2d.dispose();
    }

    private boolean isOpaque(int x, int y) {
        return (image.getRGB(x, y) >> 24) != 0x00;
    }

    private boolean isEdgePixel(int x, int y) {
        return x == 0 || y == 0 || x == image.getWidth() - 1 || y == image.getHeight() - 1 ||
                (image.getRGB(x - 1, y) >> 24) == 0x00 || (image.getRGB(x + 1, y) >> 24) == 0x00 ||
                (image.getRGB(x, y - 1) >> 24) == 0x00 || (image.getRGB(x, y + 1) >> 24) == 0x00;
    }

    protected Enemy getFirstEnemyInRange(int range) {
        Enemy firstEnemyInRange = null;
        for (Enemy enemy : Game.instance.map.enemies) {
            float dx = getColumnsFrom(enemy, 0);
            float dy = getRowsFrom(enemy, 0);

            int squareDistance = (int) (dx * dx + dy * dy);
            boolean IN_RANGE = range * range <= squareDistance;
            if (IN_RANGE)
                continue;

            if (firstEnemyInRange == null || firstEnemyInRange.getX() < enemy.getX())
                firstEnemyInRange = enemy;
        }
        return firstEnemyInRange;
    }

    protected float getColumnsFrom(Enemy enemy, float projectionFactor) {
        return MapConv.cordToSoftGrid(enemy.getX() + projectionFactor * enemy.getVelocityX())
                - (column + getSize() / 2);
    }

    protected float getRowsFrom(Enemy enemy, float projectionFactor) {
        return MapConv.cordToSoftGrid(enemy.getY() + projectionFactor * enemy.getVelocityY())
                - (row + getSize() / 2);
    }

    protected float getRowsFrom(Enemy enemy, float projectionFactor, float offset) {
        return MapConv.cordToSoftGrid(enemy.getY() + projectionFactor * enemy.getVelocityY())
                - (row + getSize() / 2 + offset);
    }

    public void resize() {
        // Retrive image
        image = tier == 0 ? getGraphics().getTowerImage()
                : getGraphics().getTowerImage(path, tier - 1);

        // Scale image
        int size = (int) (getSize() * Game.instance.map.getTileSize());
        image = resizeImage(image, size, image.getHeight() * size / image.getWidth());

        // Refresh highlight
        if (Game.instance.mi.getSelectedTower() == this)
            loadHighlight();
    }

    public void upgrade(int path) {
        if (this.path == 0)
            this.path = path;
        if (this.path != path)
            return;
        if (tier >= getUpgradeInfo().getTiers())
            return;

        tier += 1;

        Game.instance.player.spend( getUpgradeInfo().getCost(path, tier) );

        resize();
    }

    public void damage(float damage) {
        damageAmount += damage;
        if (damageAmount >= getUpgradeInfo().getHealth(getPath(), getTier()))
            remove();
    }

    public void remove() {
        Game.instance.map.towers.remove(this);
        Game.instance.mi.deselectTower();

        Game.instance.map.map.fill(column, row, getSize(), getSize(), true);
        Game.instance.em.generatePath();
    }

    public int getTier() {
        return tier;
    }

    public int getPath() {
        return path;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getX() {
        return MapConv.gridToCord(getColumn());
    }

    public int getY() {
        return MapConv.gridToCord(getRow());
    }

    public int getImageY() {
        return MapConv.gridToCord(getRow() + getSize() - image.getHeight() / Game.instance.map.getTileSize());
    }

    public int getScreenX() {
        return MapConv.xToViewX(getX());
    }

    public int getScreenY() {
        return MapConv.yToViewY(getImageY());
    }

    public abstract void drawTower(Graphics g, boolean selected);

    public abstract void drawEntity(Graphics g, boolean selected);

    public abstract void update();

    public abstract Tower createNew(int column, int row);

    public abstract int getSize();

    public abstract TowerUpgrade getUpgradeInfo();

    public abstract TowerGraphics getGraphics();

}
