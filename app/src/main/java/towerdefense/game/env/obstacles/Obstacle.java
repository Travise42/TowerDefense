package towerdefense.game.env.obstacles;

import static towerdefense.func.ImageHandler.loadImage;

import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.env.MapConversions;

public class Obstacle {

    private BufferedImage image;

    private int x;
    private int y;
    private int column;
    private int row;

    public int destroyed;
    
    public Obstacle( String image_name, int x, int y ) {
        this.image = loadImage(image_name);

        this.x = x;
        this.y = y;

        this.column = MapConversions.cordToGrid(x);
        this.row = MapConversions.cordToGrid(y);

        destroyed = 0;
    }

    // return false if deleted
    public boolean update() {
        if ( destroyed > 0 ) destroyed++;
        else if (Game.instance.map.map.isOpen(column, row)) destroyed = 1;
        return destroyed >= 60;
    }

    public void draw() {
        
    }

}
