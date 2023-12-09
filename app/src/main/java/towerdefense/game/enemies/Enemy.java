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
    private float x;
    private float y;
    private int size;
    private float speed;
    private int damage;
    private int health;

    private int animationFrame;

    public Enemy( Game gameInstance, float enemy_type ) {
        game = gameInstance;
        type = enemy_type;

        int[] pos = game.map.getEntrance();
        x = pos[0];
        y = pos[1];

        float tileSize = gameInstance.map.getTileSize();

        size = (int) ( type * tileSize ); // 20% - 180% of a square
        speed = ( 2 - enemy_type ) * tileSize; // 20% - 180% of a square per frame

        animationFrame = 0;
    }

    public void draw( Graphics g ) {
        int ex = ( int )( x - game.camera.getX() );
        int ey = ( int )( y - game.camera.getY() );
        g.setColor( Color.RED );
        drawCircle( g, ex, ey, size );

        ////double a = Math.PI/8 * ( Math.cos( animationFrame / 1000f ) + 2 );
        ////drawCircle( g, ex + ( int )( size*Math.cos( a ) / 2 ), ey + ( int )( size*Math.sin( a ) / 2 ), size/3 );
        ////drawCircle( g, ex + ( int )( size*Math.cos( a - Math.PI/2f ) / 2 ), ey - ( int )( size*Math.sin( a + Math.PI/2f ) / 2 ), size/3 );
    
        double a = Math.sin( animationFrame / 600f );
        drawCircle( g, ( int )( ex - size*( a - 1 ) / 4 ), ( int )( ey + size*( a + 5 ) / 12 ), size / 3 );
        drawCircle( g, ( int )( ex + size*( a + 1 ) / 4 ), ( int )( ey + size*( a - 5 ) / 12 ), size / 3 );
    }

    private void drawCircle( Graphics g, int x, int y, int diameter ) {
        g.fillOval( x - diameter/2, y - diameter/2, diameter, diameter );
    }

    public void move() {
        x += speed / 100f;
        animationFrame += speed;
    }
    
    public int getX() {
        return (int) x;
    }
    
    public int getY() {
        return (int) y;
    }
    
}
