package towerdefense.game.towers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.env.MapConversions;

public class Projectile {

    public int x;
    public int y;

    private float vx;
    private float vy;

    private int life;

    private BufferedImage image;

    public Projectile( int x, int y, float vx, float vy, BufferedImage image, int life ) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        this.image = image;

        this.life = life;
    }

    public void draw( Graphics g ) {
        g.drawImage( image, MapConversions.xToViewX(this.x) - this.image.getWidth()/2, MapConversions.yToViewY(this.y) - this.image.getHeight()/2, Game.instance.panel );
    }

    // Return true if life of projectile is over
    public boolean update() {
        life--;
        if ( life <= 0 ) return false;

        x += vx;
        y += vy;

        return true;
    }
    
}
