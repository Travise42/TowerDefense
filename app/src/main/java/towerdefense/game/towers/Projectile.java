package towerdefense.game.towers;

import java.util.List;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.MapConversions;

public class Projectile {

    public int x;
    public int y;

    private float xVel;
    private float yVel;

    private int damage;
    private int pierce;
    private int lifetime;

    private BufferedImage image;

    private List<Enemy> hitEnemies;

    public Projectile(BufferedImage image, int x, int y, float xVel, float yVel, int damage, int pierce, int lifetime) {
        this.image = image;

        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.damage = damage;
        this.pierce = pierce;
        this.lifetime = lifetime;

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

            float dx = x + image.getWidth() / 2 - enemy.getX() - enemy.getSize()/2;
            float dy = y + image.getWidth() / 2 - enemy.getY() - enemy.getSize()/2;
            float squareDistance = dx * dx + dy * dy;

            if (squareDistance < Game.instance.map.getTileSize()*Game.instance.map.getTileSize()) {
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
