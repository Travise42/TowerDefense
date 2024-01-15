package towerdefense.game.towers;

import java.util.List;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.func.Calc;
import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapConversions;

public class Projectile {

    public float x;
    public float y;

    private float xVel;
    private float yVel;

    private int damage;
    private int pierce;
    private int lifetime;

    private BufferedImage image;

    private List<Enemy> hitEnemies;

    public Projectile(BufferedImage image, int x, int y, float xVel, float yVel, int damage, int pierce, int range, int speed) {
        this.image = image;

        this.x = x;
        this.y = y;
        this.xVel = xVel * speed;
        this.yVel = yVel * speed;
        this.damage = damage;
        this.pierce = pierce;
        this.lifetime = MapConversions.gridToCord(range)/speed;

        hitEnemies = new ArrayList<>();
    }

    public void draw(Graphics g) {
        g.drawImage(image, MapConversions.xToViewX(this.x) - this.image.getWidth() / 2,
                MapConversions.yToViewY(this.y) - this.image.getHeight() / 2, Game.instance.panel);
    }

    // Return false if lifetime of projectile is over
    public boolean update() {
        lifetime--;
        if (lifetime <= 0)
            return false;

        x += xVel;
        y += yVel;

        for (int i = 0; i < Game.instance.map.enemies.size(); i++) {
            Enemy enemy = Game.instance.map.enemies.get(i);
            if (hitEnemies.contains(enemy))
                continue;

            float dx = x - enemy.getX() - enemy.getSize()/2;
            float dy = y - enemy.getY() - enemy.getSize()/2;
            float squareDistance = dx * dx + dy * dy;

            if (squareDistance < Map.TILE_SIZE*Map.TILE_SIZE) {
                // Hit enemy
                if ( enemy.damage(damage, xVel/15f, yVel/15f) ) {
                    // Enemy lives
                    hitEnemies.add(enemy);
                    return (--pierce != 0);
                }
                // Enemy dies
                i--;
                if (--pierce == 0)
                    return false;
            }

        }

        return true;
    }

}
