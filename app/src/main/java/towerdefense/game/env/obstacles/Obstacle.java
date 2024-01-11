package towerdefense.game.env.obstacles;

import static towerdefense.func.ImageHandler.loadImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConversions;

public abstract class Obstacle {

    private int x;
    private int y;
    private int column;
    private int row;

    public int destroyed;
    
    public Obstacle( int x, int y ) {
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

    public void draw( Graphics g ) {
        int imgX = x - getImage().getWidth()/2;
        int imgY = y - getImage().getHeight();
        g.drawImage( getImage(), MapConversions.xToViewX(x), MapConversions.yToViewY(imgY), Game.instance.panel );
        System.out.println(MapConversions.gridToCord(Map.COLUMNS/2 + 0.5f));
        g.drawLine(MapConversions.xToViewX(MapConversions.gridToCord(Map.COLUMNS/2 + 0.5f)), 0, MapConversions.columnToViewX(Map.COLUMNS/2 + 0.5f), Panel.HEIGHT);
    }

    public abstract BufferedImage getImage();

}
