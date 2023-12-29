package towerdefense.game.enemies;

import java.awt.Graphics;
import java.awt.Color;

import towerdefense.game.Game;
import towerdefense.game.env.Map;

public class Enemy {

    public static final float BULLET = 0.3f;
    public static final float NINJA = 0.4f;
    public static final float FAST = 0.6f;

    public static final float NORMAL = 0.7f;

    public static final float STRONG = 0.9f;
    public static final float BEAST = 1.2f;
    public static final float TANK = 1.8f;

    private Game game;

    private float type;
    private float x, y;
    private float vx, vy;
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
        speed = ( 2 - enemy_type ) * tileSize / 500; // 20% - 180% of a square per frame

        animationFrame = 0;
    }

    public void draw( Graphics g ) {
        int ex = ( int )( x - game.camera.getX() );
        int ey = ( int )( y - game.camera.getY() );

        g.setColor( new Color( 200, 30, 30 ) );
        double a = Math.sin( animationFrame / 600f );
        drawCircle( g, ( int )( ex - size*( a - 1 ) / 4 ), ( int )( ey + size*( a + 5 ) / 12 ), size / 3 );
        drawCircle( g, ( int )( ex + size*( a + 1 ) / 4 ), ( int )( ey + size*( a - 5 ) / 12 ), size / 3 );

        g.setColor( new Color( 240, 15, 15 ) );
        drawCircle( g, ex, ey, size );
    }

    private void drawCircle( Graphics g, int x, int y, int diameter ) {
        g.fillOval( x - diameter/2, y - diameter/2, diameter, diameter );
    }

    public void move() {
        if ( handleAcceleration() ) {
            //TODO damage the player
            game.map.enemies.remove( this );
            System.out.println("ded");
            return;
        }

        // Decellerate
        vx *= 0.96;
        vy *= 0.96;

        // Move
        if ( checkIfOnTower( x, y ) || !checkIfOnTower( x + vx, y ) ) x += vx;
        if ( checkIfOnWall( x, y ) || !checkIfOnWall( x, y + vy ) ) y += vy;

        animationFrame += 40 * Math.sqrt( vx*vx + vy*vy );
    }

    public boolean checkIfOnWall( float x, float y ) {
        return !game.map.map.isOpen( ( int )( x / game.map.getTileSize() ), ( int )( y / game.map.getTileSize() ) );
    }

    public boolean checkIfOnTower( float x, float y ) {
        float c = x / game.map.getTileSize() - ( Map.COLUMNS - game.map.map.openColumns ) / 2;
        float r = y / game.map.getTileSize() - ( Map.ROWS - game.map.map.openRows ) / 2;
        return !( c < 0 || r < 0 || c > game.map.map.openColumns || r > game.map.map.openRows ) && !game.map.map.isOpen( ( int )( x / game.map.getTileSize() ), ( int )( y / game.map.getTileSize() ) );
    }

    public boolean handleAcceleration() {
        final int LEFT_COLUMN = ( Map.COLUMNS - game.map.map.openColumns ) / 2;
        final int TOP_ROW = ( Map.ROWS - game.map.map.openRows ) / 2;

        boolean offMap = false;
        int mx = 0, my = 0;
        final int[][] pointers = { { -1, -1 }, { 1, -1 }, { -1, 1 }, { 1, 1 }, { 0, 0 } };
        for ( int[] pointer : pointers ) {
            int pc = ( int )( x / game.map.getTileSize() - LEFT_COLUMN - pointer[0] / 3 );
            if ( game.map.map.openColumns + 2 < pc ) return true;
            if ( pc <= 0 || game.map.map.openColumns <= pc) { offMap = true; break; }

            int pr = ( int )( y / game.map.getTileSize() - TOP_ROW - pointer[1] / 3 );
            if ( pr < 0 || game.map.map.openRows <= pr) { offMap = true; break; }

            int dir = -game.em.path[pc][pr];
            
            if ( Math.abs(dir) == 1 ) {
                // Accelerate
                my += dir;
                continue;
            }
            mx += dir;
        }

        if ( offMap ) {
            vx += speed;
            return false;
        }

        double hyp = Math.sqrt( mx*mx + my*my );

        double dx = mx * speed / hyp;
        double dy = my * speed / hyp;

        // Accelerate
        vx += dx;
        vy += dy;

        return false;
    }
    
    public int getX() {
        return (int) x;
    }
    
    public int getY() {
        return (int) y;
    }
    
}
