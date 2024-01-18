package towerdefense.game.gui;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.env.MapInteractions;

public class UI {

    final private static String HEALTH_IMAGE_LOCATION = "gui/ui/health.png";
    final private static String GOLD_IMAGE_LOCATION = "gui/ui/gold.png";

    final private static int DATA_SIZE = 32;

    private BufferedImage health;
    private BufferedImage gold;

    final private static int HEALTH_DISPLAY_X = 10;
    final private static int GOLD_DISPLAY_X = 140;

    private Font font;

    final public static int TOWER_SELECT = 0;
    final public static int TOWER_PLACEMENT = 1;
    final public static int TOWER_UPGRADE = 2;
    final public static int PAUSE_MENU = 3;
    final public static int SETTINGS_MENU = 4;

    private int mode;

    private Button[] buttons;

    final private static int PAUSE_BUTTON = 0;
    final private static int TRASH_BUTTON = 1;
    final private static int CLOSE_BUTTON = 2;
    final private static int NEXT_ROUND_BUTTON = 3;

    final private static int WIZARD_BUTTON = 4;
    final private static int CANNON_BUTTON = 5;
    final private static int ARCHER_BUTTON = 6;
    final private static int WALL_BUTTON = 7;

    final private static int LEFT_PATH_BUTTON = 8;
    final private static int RIGHT_PATH_BUTTON = 9;

    private int selectedTowerButton;

    public UI() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(getClass().getResource("/gui/font/dataFont.otf").getFile()));
            font = font.deriveFont((float) DATA_SIZE); // Set size to DATA_SIZE
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        health = resizeImage(loadImage(HEALTH_IMAGE_LOCATION), DATA_SIZE, DATA_SIZE);
        gold = resizeImage(loadImage(GOLD_IMAGE_LOCATION), DATA_SIZE, DATA_SIZE);

        int right = Panel.WIDTH;
        int bottom = Panel.HEIGHT;
        buttons = new Button[] {
                // Pause button
                new Button(right - 90, 20, 60, 60, Button.GREEN, "gui/ui/pause_button.png", "", "", () -> {

                }),
                // Trash button
                new Button(right - 90, 20, 60, 60, Button.RED, "gui/ui/trash_button.png", "", "", () -> {
                    Game.instance.mi.selectTowerPlacement(MapInteractions.NO_TOWER);
                    setMode(TOWER_SELECT);
                }),
                // Close button
                new Button(right - 90, 20, 60, 60, Button.RED, "gui/ui/close_button.png", "", "", () -> {
                    Game.instance.mi.deselectTower();
                    setMode(TOWER_SELECT);
                }),
                // Next round button
                new Button(right - 100, bottom - 100, 80, 80, Button.GREEN, "gui/ui/next_round_button.png", "", "",
                        () -> {

                        }),

                // Wizard tower button
                new Button(20, bottom - 140, 120, 120, Button.RED, "gui/towers/wizard_tower/icon.png",
                        "Wizard\nTower",
                        getCost(MapInteractions.WIZARD_TOWER, 0, 0) + " G", () -> {
                            Game.instance.mi.selectTowerPlacement(MapInteractions.WIZARD_TOWER);
                            setMode(TOWER_PLACEMENT);
                            selectedTowerButton = WIZARD_BUTTON;
                        }),
                // Cannon button
                new Button(160, bottom - 140, 120, 120, Button.RED, "gui/towers/cannon_tower/icon.png",
                        "Cannon\nTower",
                        getCost(MapInteractions.CANNON_TOWER, 0, 0) + " G", () -> {
                            Game.instance.mi.selectTowerPlacement(MapInteractions.CANNON_TOWER);
                            setMode(TOWER_PLACEMENT);
                            selectedTowerButton = CANNON_BUTTON;
                        }),
                // Archer tower button
                new Button(300, bottom - 140, 120, 120, Button.RED, "gui/towers/archer_tower/icon.png",
                        "Archer\nTower",
                        getCost(MapInteractions.ARCHER_TOWER, 0, 0) + " G", () -> {
                            Game.instance.mi.selectTowerPlacement(MapInteractions.ARCHER_TOWER);
                            setMode(TOWER_PLACEMENT);
                            selectedTowerButton = ARCHER_BUTTON;
                        }),
                // Wall button
                new Button(440, bottom - 140, 120, 120, Button.RED, "gui/towers/wall_tower/icon.png", "Walls",
                        getCost(MapInteractions.WALL_TOWER, 0, 0) + " G", () -> {
                            Game.instance.mi.selectTowerPlacement(MapInteractions.WALL_TOWER);
                            setMode(TOWER_PLACEMENT);
                            selectedTowerButton = WALL_BUTTON;
                        }),

                // Path 1
                new Button(160, bottom - 140, 120, 120, Button.GREEN, null, "", "500 G", () -> {

                }),
                // Path 2
                new Button(300, bottom - 140, 120, 120, Button.GREEN, null, "", "500 G", () -> {

                }) };

        setMode(TOWER_SELECT);
    }

    private int getCost(int tower, int path, int tier) {
        return MapInteractions.TOWER.get(tower).getUpgradeInfo().getCost(path, tier);
    }

    public void draw(Graphics g, int i) {
        // Upper
        drawData(g);

        // Lower
        drawPanel(g);

        Button.drawVisibleButtons(g);
    }

    private void drawData(Graphics g) {
        // Text
        g.setFont(font);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);

        final int OFFSET = 10 + DATA_SIZE;
        g.drawString(String.valueOf(Game.instance.player.getHealth()), HEALTH_DISPLAY_X + OFFSET, OFFSET);
        g.drawString(String.valueOf(Game.instance.player.getGold()), GOLD_DISPLAY_X + OFFSET, OFFSET);

        // Icons
        g.drawImage(health, HEALTH_DISPLAY_X, 15, Game.instance.panel);
        g.drawImage(gold, GOLD_DISPLAY_X, 15, Game.instance.panel);
    }

    // Panel

    private void drawPanel(Graphics g) {
        g.setColor(new Color(50, 45, 60, 120));
        g.fillRect(0, Panel.HEIGHT - 100, Panel.WIDTH, 100);

        handleButton(WIZARD_BUTTON);
        handleButton(CANNON_BUTTON);
        handleButton(ARCHER_BUTTON);
        handleButton(WALL_BUTTON);

        switch (mode) {
            case TOWER_SELECT -> drawTowerSelect(g);
            case TOWER_PLACEMENT -> drawTowerPlacement(g);
            case TOWER_UPGRADE -> drawTowerUpgrade(g);
            case PAUSE_MENU -> drawPauseMenu(g);
            case SETTINGS_MENU -> drawSettingsMenu(g);
        }
    }

    private void drawTowerSelect(Graphics g) {
        handleTowerPricing( WIZARD_BUTTON, MapInteractions.WIZARD_TOWER, 0, 0 );
        handleTowerPricing( CANNON_BUTTON, MapInteractions.CANNON_TOWER, 0, 0 );
        handleTowerPricing( ARCHER_BUTTON, MapInteractions.ARCHER_TOWER, 0, 0 );
        handleTowerPricing( WALL_BUTTON, MapInteractions.WALL_TOWER, 0, 0 );
    }

    private void drawTowerUpgrade(Graphics g) {

    }

    private void drawTowerPlacement(Graphics g) {

    }

    private void handleTowerPricing( int button, int tower, int path, int tier ) {
        if (Game.instance.player.canAfford(getCost(tower, path, tier)))
            buttons[button].setColor(Button.GREEN);
        else
            buttons[button].setColor(Button.RED);
    }

    private void handleButton(int tower) {
        Button button = buttons[tower];

        int dest = mode == TOWER_PLACEMENT ? tower == selectedTowerButton ? 150 : 130 : 140;
        if (button.getY() == dest)
            return;

        int distance = button.getY() - (Panel.HEIGHT - dest);

        button.moveY(Panel.HEIGHT - dest + distance * 7 / 10);
    }

    // Menu

    private void drawPauseMenu(Graphics g) {

    }

    private void drawSettingsMenu(Graphics g) {

    }

    // Setters

    public void setMode(int newMode) {
        mode = newMode;

        Button.hideAll();
        switch (mode) {
            case TOWER_SELECT -> {
                showTowerButtons();
                buttons[PAUSE_BUTTON].show();
                buttons[NEXT_ROUND_BUTTON].show();
            }
            case TOWER_PLACEMENT -> {
                showTowerButtons();
                buttons[TRASH_BUTTON].show();
                buttons[NEXT_ROUND_BUTTON].show();
            }
            case TOWER_UPGRADE -> {
                showUpgradeButtons();
                buttons[CLOSE_BUTTON].show();
                buttons[NEXT_ROUND_BUTTON].show();
            }
            case PAUSE_MENU -> {

            }
            case SETTINGS_MENU -> {

            }
        }
    }

    private void showTowerButtons() {
        buttons[WIZARD_BUTTON].show();
        buttons[CANNON_BUTTON].show();
        buttons[ARCHER_BUTTON].show();
        buttons[WALL_BUTTON].show();
    }

    private void showUpgradeButtons() {
        buttons[LEFT_PATH_BUTTON].show();
        buttons[RIGHT_PATH_BUTTON].show();
    }

}
