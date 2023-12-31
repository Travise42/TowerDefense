package towerdefense.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import towerdefense.game.enemies.Enemy;
import towerdefense.game.enemies.EnemyMovement;
import towerdefense.game.env.Camera;
import towerdefense.game.env.MapHandler;
import towerdefense.game.env.MapInteractions;
import towerdefense.game.player.Player;
import towerdefense.game.towers.ProjectileHandler;

public class Game {

    public static Game instance;

    public Panel panel;
    public Player player;
    public Camera camera;
    public MapHandler map;
    public MapInteractions mi;
    public EnemyMovement em;
    public ProjectileHandler ph;

    public int gameTick = 0;
    public int mx = 0, my = 0;

    public Game(Panel jpanel) {
        instance = this;
        panel = jpanel;

        mi = new MapInteractions();
        camera = new Camera();
        map = new MapHandler();
        player = new Player();
        em = new EnemyMovement(EnemyMovement.BIRD);
        ph = new ProjectileHandler();

        newGame();
        System.out.println("Started!");
    }

    public void newGame() {
        player.newGame();
        map.newGame();

        gameTick = 0;
    }

    public void update() {
        gameTick++;

        camera.update();
        map.update();
        ph.update();
    }

    public void draw(Graphics g) {
        map.draw(g);

        mi.drawHighlightedRegion(g, mx, my);
        ph.draw(g);
    }

    public void click() {
        mi.interactWithMap(mx, my);
    }

    public void moveMouse(MouseEvent e) {

    }

    public void updateMouse(MouseEvent e) {
        mx = e.getX(); // -8
        my = e.getY() - 29; // -30
    }

    public void keyCalled(int key) {
        // Placing Towers
        if (KeyEvent.VK_0 <= key && key <= KeyEvent.VK_0 + 5) {
            mi.selectTowerPlacement(key - KeyEvent.VK_1);
            return;
        }
        switch (key) {
            case KeyEvent.VK_SPACE -> map.nextStage();
            case KeyEvent.VK_COMMA -> mi.upgradeSelectedTower(0);
            case KeyEvent.VK_PERIOD -> mi.upgradeSelectedTower(1);
            case KeyEvent.VK_BACK_SPACE -> mi.deleteSelectedTower();
            // Spawning enenmies
            case KeyEvent.VK_Q -> map.newEnemy(Enemy.BULLET);
            case KeyEvent.VK_W -> map.newEnemy(Enemy.NINJA);
            case KeyEvent.VK_E -> map.newEnemy(Enemy.FAST);
            case KeyEvent.VK_R -> map.newEnemy(Enemy.NORMAL);
            case KeyEvent.VK_T -> map.newEnemy(Enemy.STRONG);
            case KeyEvent.VK_Y -> map.newEnemy(Enemy.BEAST);
            case KeyEvent.VK_U -> map.newEnemy(Enemy.TANK);
        }
    }

    public int getStage() {
        return map.map.stage;
    }

}
