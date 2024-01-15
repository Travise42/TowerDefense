package towerdefense.game.env;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.enemies.Enemy;
import towerdefense.game.env.obstacles.Obstacle;
import towerdefense.game.env.obstacles.Tree;
import towerdefense.game.towers.Tower;

/**
 * Map Handler handles the game map and all the towers and enemies.
 * This means updating the towers and enemies.
 * 
 * Map Hander stores the map images and is equiped to draw the map. Towers and
 * enemies shoudl be drawn using their classes.
 */
public class MapHandler {

    final private static String MAP_DIR = "map/tiles/";

    private static BufferedImage[] tileImages;
    private static BufferedImage[] testTileImages;

    private static String[] tileDests = { "grass", "forest" };
    private static String[] testTileDests = { "testObstructed", "testOpen" };

    public Map map;

    public List<Tower> towers;
    public List<Enemy> enemies;

    public MapHandler() {
        map = new Map();

        loadTileImages();
        loadTestTileImages();

        towers = new ArrayList<>();
        enemies = new ArrayList<>();

        resize();
    }

    /// Graphics /// ------------------------------------------------------------
    /// ///

    private void loadTileImages() {
        tileImages = new BufferedImage[tileDests.length];
        for (int i = 0; i < tileDests.length; i++)
            tileImages[i] = loadImage(MAP_DIR + tileDests[i] + ".png");
    }

    private void loadTestTileImages() {
        testTileImages = new BufferedImage[testTileDests.length];
        for (int i = 0; i < testTileDests.length; i++)
            testTileImages[i] = loadImage(MAP_DIR + testTileDests[i] + ".png");
    }

    public void draw(Graphics g) {
        // temp for testing
        drawTestMap(g);

        drawMap(g);
        drawEnemies(g);
        drawTowers(g);
    }

    private void drawTestMap(Graphics g) {
        for (int row = 0; row < Map.ROWS; row++)
            for (int column = 0; column < Map.COLUMNS; column++)
                g.drawImage(
                        testTileImages[map.isOpen(column, row) ? 1 : 0],
                        (int) (column * getTileSize() - Game.instance.camera.getX()),
                        (int) (row * getTileSize() - Game.instance.camera.getY()),
                        Game.instance.panel);
    }

    private void drawEnemies(Graphics g) {
        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).draw(g);
    }

    private void drawMap(Graphics g) {
        // Draw tiles
        int firstColumn = MapConv.cordToGrid(MapConv.screenCordToCord(Game.instance.camera.getX() + 1));
        int lastColumn = MapConv.cordToGrid(MapConv.screenCordToCord(Game.instance.camera.getX() + Panel.WIDTH + 1));

        int firstRow = MapConv.cordToGrid(MapConv.screenCordToCord(Game.instance.camera.getY() + 1));
        int lastRow = MapConv.cordToGrid(MapConv.screenCordToCord(Game.instance.camera.getY() + Panel.HEIGHT + 1)) + 1;

        for (int c = firstColumn; c < lastColumn; c++) {
            for (int r = firstRow; r < lastRow; r++) {
                if ( map.isOpen(c, r)) {
                    g.drawImage(tileImages[0], MapConv.columnToViewX(c), MapConv.rowToViewY(r),
                            Game.instance.panel);
                    continue;
                }
                g.drawImage(tileImages[1], MapConv.columnToViewX(c), MapConv.rowToViewY(r),
                        Game.instance.panel);
            }
        }

        //Draw obstacles
        for (Obstacle obstacle : map.obstacles) {
            obstacle.draw(g);
        }
    }

    private void drawTowers(Graphics g) {
        Tower selectedTower = Game.instance.mi.getSelectedTower();

        // Draw towers top to bottom ( furthest to closest )
        for (int i = 0; i < towers.size(); i++) {
            Tower tower = towers.get(i);
            tower.update();

            if ( tower == selectedTower ) continue;
            tower.drawTower(g, false);
            tower.drawEntity(g, false);
        }

        // Draw selection outline infront of all the towers
        if (selectedTower != null) {
            selectedTower.drawRange(g);
            selectedTower.drawTower(g, true);
            selectedTower.drawEntity(g, true);
        }
    }

    public void resize() {
        // Resize tiles
        for (int i = 0; i < tileImages.length; i++)
            tileImages[i] = resizeImage(
                    tileImages[i],
                    (int) getTileSize() + 1,
                    (int) getTileSize() + 1);

        // Resize test tiles
        for (int i = 0; i < testTileImages.length; i++)
            testTileImages[i] = resizeImage(
                    testTileImages[i],
                    (int) getTileSize() + 1,
                    (int) getTileSize() + 1);

        // Resize towers
        for (int i = 0; i < towers.size(); i++)
            towers.get(i).resize();

        Tree.resize((int) getTileSize());
    }

    /// Game Loop /// ------------------------------------------------------------
    /// ///

    public void newGame() {
        map.reset();
        towers.clear();
        enemies.clear();
    }

    public void update() {
        // Move all the enemies
        for (int i = 0; i < enemies.size(); i++)
            enemies.get(i).move();

        // Update obstacles
        for (int i = 0; i < map.obstacles.size();) {
            if (map.obstacles.get(i).update()) {
                i++;
                continue;
            }
            map.obstacles.remove(i);
        }
    }

    public void nextStage() {
        map.nextStage();
        resize();
    }

    /// Enemies /// ------------------------------------------------------------ ///

    public void newEnemy(float enemy_type) {
        enemies.add(new Enemy(enemy_type));
    }

    /// Map /// ------------------------------------------------------------ ///

    public void addTower(Tower tower) {
        map.fill(tower.getColumn(), tower.getRow(), tower.getSize(), tower.getSize(), false);
        Game.instance.em.generatePath();

        // Insert tower in list ordered lowest to highest y values
        int pointer = -1;
        do
            if (towers.size() <= ++pointer)
                break;
        while (towers.get(pointer).getY() < tower.getY());
        towers.add(pointer, tower);
    }

    @Deprecated
    public void sortTowers() {
        Collections.sort(towers, Comparator.comparingInt(Tower::getRow));
    }

    public float getTileSize() {
        return Panel.WIDTH / (Game.instance.camera.viewx);
    }

    public float getEntranceX() {
        return MapConv.screenCordToCord(Game.instance.camera.getX()) - Map.TILE_SIZE;
    }

    public float getEntranceY() {
        return Map.ROWS * Map.TILE_SIZE / 2;
    }

}
