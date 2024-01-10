package towerdefense.game.enemies;

import towerdefense.game.Game;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConversions;
import towerdefense.game.towers.Tower;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

    // Constants defining enemy types and their attributes
    public static final float BULLET = 0.3f;
    public static final float NINJA = 0.4f;
    public static final float FAST = 0.6f;

    public static final float NORMAL = 0.7f;

    public static final float STRONG = 0.9f;
    public static final float BEAST = 1.2f;
    public static final float TANK = 1.5f;

    public static final float FRICTION = 0.96f;

    // Enemy attributes
    public float type;

    private float x, y;
    private float vx, vy;
    private int size;
    public float speed;

    private float health;

    private float animationFrame;

    // Damage indication
    private int damaged;

    // Pointers used for acceleration calculation
    private static final int[] POINTER_X = { -1, 1, -1, 1, 0 };
    private static final int[] POINTER_Y = { -1, -1, 1, 1, 0 };

    public Enemy(float enemy_type) {
        type = enemy_type;

        // Set initial position and attributes based on enemy type
        x = Game.instance.map.getEntranceX();
        y = Game.instance.map.getEntranceY()
                + (float) (Math.random() - 0.5) * MapConversions.gridToCord(Game.instance.map.map.getOpenRows());

        float tileSize = Game.instance.map.getTileSize();
        health = type * 100;
        size = (int) (type * tileSize);
        speed = (2 - enemy_type) * tileSize / 1000;

        vx = 0;
        vy = 0;

        damaged = 0;

        animationFrame = 0;
    }

    public void draw(Graphics g) {
        // Rendering logic to draw the enemy on the screen
        int viewX = MapConversions.xToViewX(x);
        int viewY = MapConversions.yToViewY(y);

        // Calculate hand locations
        double f = Math.sin(animationFrame / 10);

        double X1 = -(f - 2) / 3.0;
        double X2 = (f + 2) / 3.0;
        double Y1 = (2 * f + 9) / 12.0;
        double Y2 = (2 * f - 9) / 12.0;

        double factor = size / (2 * Math.sqrt(vx * vx + vy * vy));

        int x1 = (int) (factor * (X1 * vx - Y1 * vy));
        int y1 = (int) (factor * (X1 * vy + Y1 * vx));

        int x2 = (int) (factor * (X2 * vx - Y2 * vy));
        int y2 = (int) (factor * (X2 * vy + Y2 * vx));

        // Draw hands
        if ( damaged > 0 ) g.setColor(new Color(220, 200, 100));
        else g.setColor(new Color(200, 30, 30));
        drawCircle(g, viewX + x1, viewY + y1, size / 3);
        drawCircle(g, viewX + x2, viewY + y2, size / 3);

        // Draw body
        if ( damaged > 0 ) g.setColor(new Color(220, 200, 100));
        else g.setColor(new Color(240, 15, 15));
        drawCircle(g, viewX, viewY, size);

        if ( damaged > 0 ) damaged--;
    }

    // Helper method to draw a circle
    private void drawCircle(Graphics g, int x, int y, int diameter) {
        g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }

    public void move() {
        // Method controlling the movement of the enemy

        // Check if acceleration leads to damage
        if (handleAcceleration()) {
            // TODO: damage the player
            Game.instance.map.enemies.remove(this);
            return;
        }

        animationFrame += Math.sqrt(vx * vx + vy * vy);

        // Apply friction to decelerate
        vx *= FRICTION;
        vy *= FRICTION;

        // Move the enemy
        if (checkIfOnWall(x, y) || !checkIfOnWall(x, y + vy))
            y += vy;
        if (checkIfOnTower(x, y) || !checkIfOnTower(x + vx, y)) {
            x += vx;
            return;
        }

        // Check for collisions with towers and damage them if collided
        for (int m = -1; m < 2; m += 2) {
            for (int n = -1; n < 2; n += 2) {
                int column = (int) ((x + vx + m * size / 2) / Game.instance.map.getTileSize());
                int row = (int) ((y + vy + n * size / 2) / Game.instance.map.getTileSize());
                for (Tower tower : Game.instance.map.towers) {
                    if (tower.getColumn() <= column && column < tower.getColumn() + tower.getSize()
                            && tower.getRow() <= row && row < tower.getRow() + tower.getSize()) {
                        tower.damage(type);
                        return;
                    }
                }
            }
        }
    }

    // Helper methods for collision detection
    private boolean checkIfOnWall(float x, float y) {
        return pointOnWall(x - size / 2, y - size / 2)
                || pointOnWall(x + size / 2, y - size / 2)
                || pointOnWall(x - size / 2, y + size / 2)
                || pointOnWall(x + size / 2, y + size / 2);
    }

    private boolean pointOnWall(float x, float y) {
        return !Game.instance.map.map.isOpen((int) (x / Game.instance.map.getTileSize()),
                (int) (y / Game.instance.map.getTileSize()));
    }

    private boolean checkIfOnTower(float x, float y) {
        return pointOnTower(x - size / 2, y - size / 2)
                || pointOnTower(x + size / 2, y - size / 2)
                || pointOnTower(x - size / 2, y + size / 2)
                || pointOnTower(x + size / 2, y + size / 2);
    }

    private boolean pointOnTower(float x, float y) {
        float c = x / Game.instance.map.getTileSize() - (Map.COLUMNS - Game.instance.map.map.getOpenColumns()) / 2;
        float r = y / Game.instance.map.getTileSize() - (Map.ROWS - Game.instance.map.map.getOpenRows()) / 2;
        return !(c < 0 || r < 0 || c > Game.instance.map.map.getOpenColumns()
                || r > Game.instance.map.map.getOpenRows())
                && !Game.instance.map.map.isOpen((int) (x / Game.instance.map.getTileSize()),
                        (int) (y / Game.instance.map.getTileSize()));
    }

    // Helper method to handle enemy acceleration
    private boolean handleAcceleration() {
        final int LEFT_COLUMN = (Map.COLUMNS - Game.instance.map.map.getOpenColumns()) / 2;
        final int TOP_ROW = (Map.ROWS - Game.instance.map.map.getOpenRows()) / 2;

        int mx = 0, my = 0;
        for (int i = 0; i < POINTER_X.length; i++) {
            int pc = (int) (x / Game.instance.map.getTileSize() - LEFT_COLUMN - POINTER_X[i] / 3.0);
            if (Game.instance.map.map.getOpenColumns() + 2 < pc)
                return true;
            if (pc < 0 || Game.instance.map.map.getOpenColumns() <= pc) {
                mx += 1;
                continue;
            }

            int pr = (int) (y / Game.instance.map.getTileSize() - TOP_ROW - POINTER_Y[i] / 3.0);
            if (pr < 0 || Game.instance.map.map.getOpenRows() <= pr)
                continue;

            int dir = -Game.instance.em.path[pc][pr];

            if (Math.abs(dir) == 1) {
                // Accelerate in the respective direction
                my += dir;
                continue;
            }
            mx += dir / 2;
        }

        double hyp = Math.sqrt(mx * mx + my * my);

        if (hyp == 0) {
            vx += speed;
            return false;
        }

        double dx = mx * speed / hyp;
        double dy = my * speed / hyp;

        // Apply calculated acceleration
        vx += dx;
        vy += dy;

        return false;
    }

    // Return false if dead
    public boolean damage(float amount, float xImpact, float yImpact) {
        health -= amount;
        damaged = 3;

        if ( health <= 0 )
            return die();

        // Knockback
        //vx += xImpact;
        //vy += yImpact;

        return true;
    }

    public boolean die() {
        Game.instance.map.enemies.remove( this );
        return false;
    }

    // Getter methods
    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public float getVelocityX() {
        return vx;
    }

    public float getVelocityY() {
        return vy;
    }

    public float getSize() {
        return size;
    }
}
