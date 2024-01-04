package towerdefense.game.towers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ProjectileHandler {

    private List<Projectile> projectiles;

    public ProjectileHandler() {
        projectiles = new ArrayList<>();
    }

    public void update() {
        for (Projectile projectile : projectiles) {
            projectile.update();
        }
    }

    public void draw(Graphics g) {
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }

    public void add(Projectile projectile) {
        projectiles.add(projectile);
    }

}
