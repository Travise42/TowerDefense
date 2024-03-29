package towerdefense.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import towerdefense.game.enemies.Enemy;
import towerdefense.game.enemies.EnemyMovement;
import towerdefense.game.env.Camera;
import towerdefense.game.env.MapHandler;
import towerdefense.game.env.MapInteractions;
import towerdefense.game.gui.Button;
import towerdefense.game.gui.UI;
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
    public UI ui;

    public int gameTick = 0;
    public int mx = 0, my = 0;
    public boolean mousePressed = false;

    public Game(Panel jpanel) {
        instance = this;
        panel = jpanel;

        mi = new MapInteractions();
        camera = new Camera();
        map = new MapHandler();
        player = new Player();
        em = new EnemyMovement(EnemyMovement.BIRD);
        ph = new ProjectileHandler();
        ui = new UI();

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
        ui.draw(g, gameTick);
    }

    public void mouseUp(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            mousePressed = false;
        if (!Button.clickHovered())
            mi.interactWithMap(mx, my);
    }

    public void mouseDown(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            mousePressed = true;
    }

    public void moveMouse(MouseEvent e) {

    }

    public void updateMouse(MouseEvent e) {
        mx = e.getX(); // -8
        my = e.getY() - 29; // -30
    }

    public void keyCalled(int key) {
        switch (key) {
            // Placing Towers
            case KeyEvent.VK_ESCAPE -> {
                if (ui.getMode() == UI.TOWER_SELECT) ui.getButton(UI.PAUSE_BUTTON).click();
                if (ui.getMode() == UI.TOWER_PLACEMENT) ui.getButton(UI.TRASH_BUTTON).click();
                if (ui.getMode() == UI.TOWER_UPGRADE) ui.getButton(UI.CLOSE_BUTTON).click();
            }
            case KeyEvent.VK_1 -> ui.getButton(UI.WIZARD_BUTTON).click();
            case KeyEvent.VK_2 -> ui.getButton(UI.CANNON_BUTTON).click();
            case KeyEvent.VK_3 -> ui.getButton(UI.ARCHER_BUTTON).click();
            case KeyEvent.VK_4 -> ui.getButton(UI.WALL_BUTTON).click();
            //case KeyEvent.VK_5 -> mi.selectTowerPlacement(MapInteractions.TROOP_TOWER);
            // Map interactions
            case KeyEvent.VK_SPACE -> map.nextStage();
            case KeyEvent.VK_COMMA -> ui.getButton(UI.LEFT_PATH_BUTTON).click();
            case KeyEvent.VK_PERIOD -> ui.getButton(UI.RIGHT_PATH_BUTTON).click();
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
