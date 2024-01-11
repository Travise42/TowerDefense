package towerdefense.game.env.obstacles;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;

import java.awt.image.BufferedImage;

import towerdefense.game.Game;

public class Tree extends Obstacle {

    final private static String IMAGE_DIR = "map/tiles/obstacles/tree.png";
    
    private static BufferedImage image;

    public Tree( int x, int y ) {
        super(x, y);
    }

    public static void resize( int tileSize) {
        image = resizeImage(loadImage(IMAGE_DIR), (int) tileSize * 2, (int) tileSize * 3);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
    
}
