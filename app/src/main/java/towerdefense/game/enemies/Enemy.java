package towerdefense.game.enemies;

import java.awt.Graphics;
import java.awt.Color;

import towerdefense.game.Game;

public class Enemy {

    public static final float BULLET = 0.2f;
    public static final float NINJA = 0.4f;
    public static final float FAST = 0.6f;

    public static final float NORMAL = 0.75f;

    public static final float STRONG = 0.9f;
    public static final float BEAST = 1.2f;
    public static final float TANK = 1.8f;

    private Game game;

    private float type;
    private int x;
    private int y;
    private int size;
    private int speed;
    private int damage;
    private int health;

    public Enemy( Game gameInstance, float enemy_type ) {
        game = gameInstance;
        type = enemy_type;

        int[] pos = game.map.getEntrance();
        x = pos[0];
        y = pos[1];

        float tileSize = gameInstance.map.getTileSize();

        size = (int) ( type * tileSize ); // 20% - 180% of a square
        speed = (int) ( ( 2 - enemy_type ) * tileSize ); // 20% - 180% of a square per frame
    }

    public void draw( Graphics g ) {
        int screenX = ( int )( x - game.camera.getX() );
        int screenY = ( int )( y - game.camera.getY() );
        g.setColor( Color.RED );
        drawCircle( g, screenX, screenY, size );
    }

    private void drawCircle( Graphics g, int x, int y, int diameter ) {
        g.fillOval( x - diameter/2, y - diameter/2, diameter, diameter );
    }

    public void move() {
        x += 1;
    }
    
}
