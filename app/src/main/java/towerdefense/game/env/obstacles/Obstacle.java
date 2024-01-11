package towerdefense.game.env.obstacles;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;
import static towerdefense.func.ImageHandler.rotateImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConversions;

public abstract class Obstacle {

    private int x;
    private int y;
    public int column;
    public int row;

    public int destroyed;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;

        this.column = MapConversions.cordToGrid(x);
        this.row = MapConversions.cordToGrid(y);

        destroyed = 0;
    }

    // return false if deleted
    public boolean update() {
        if (destroyed > 0)
            destroyed++;
        else if (Game.instance.map.map.isOpen(column, row))
            destroyed = 1;
        return destroyed < 60;
    }

    public void draw(Graphics g) {
        int imgX = MapConversions.xToViewX(x - MapConversions.screenCordToCord(getImage().getWidth() / 2));
        int imgY = MapConversions.yToViewY(y - MapConversions.screenCordToCord(getImage().getHeight() * 3 / 4));
        if (destroyed > 0) {
            double factor = Math.sin(destroyed / 3f) / 5f;
            g.drawImage(
                    resizeImage(rotateImage(getImage(), factor), getImage().getWidth(),
                            getImage().getHeight() - (int) MapConversions.cordToScreenCord(destroyed / 3f)),
                    imgX + (int) MapConversions.cordToScreenCord((float) factor * 20),
                    imgY + (int) MapConversions.cordToScreenCord(destroyed / 3f), Game.instance.panel);
            return;
        }
        g.drawImage(getImage(), imgX, imgY, Game.instance.panel);
    }

    public abstract BufferedImage getImage();

}
