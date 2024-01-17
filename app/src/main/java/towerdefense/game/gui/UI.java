package towerdefense.game.gui;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import towerdefense.game.Game;
import towerdefense.game.Panel;

public class UI {

    final private static String HEALTH_IMAGE_LOCATION = "gui/ui/health.png";
    final private static String GOLD_IMAGE_LOCATION = "gui/ui/gold.png";

    final private static int DATA_SIZE = 32;

    private BufferedImage health;
    private BufferedImage gold;

    final private static int HEALTH_DISPLAY_X = 10;
    final private static int GOLD_DISPLAY_X = 140;

    private Font font;

    final private static int TOWER_SELECT = 0;
    final private static int TOWER_PLACEMENT = 1;
    final private static int TOWER_UPGRADE = 2;
    final private static int PAUSE_MENU = 3;
    final private static int SETTINGS_MENU = 4;

    private int mode;

    final private static int PAUSE_BUTTON = 0;
    final private static int CANCEL_BUTTON = 1;
    final private static int NEXT_ROUND_BUTTON = 2;

    private Button[] optionButtons;

    final private static int WIZARD_BUTTON = 0;
    final private static int CANNON_BUTTON = 1;
    final private static int ARCHER_BUTTON = 2;
    final private static int WALL_BUTTON = 3;

    private Button[] towerButtons;

    final private static int LEFT_PATH_BUTTON = 0;
    final private static int RIGHT_PATH_BUTTON = 1;
    
    private Button[] upgradeButtons;

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

        mode = TOWER_SELECT;

        int right = Panel.WIDTH;
        int bottom = Panel.HEIGHT;
        optionButtons = new Button[] {
                // Pause button
                new Button(right - 90, 20, 60, 60, "gui/ui/pause_button.png", "", ""),
                // Trash button
                new Button(right - 90, 20, 60, 60, "gui/ui/trash_button.png", "", ""),
                // Next round button
                new Button(right - 100, bottom - 100, 80, 80, "gui/ui/next_round_button.png", "", "") };

        towerButtons = new Button[] {
                // Wizard tower button
                new Button(20, bottom - 140, 120, 120, "gui/towers/wizard_tower/icon.png", "Wizard\nTower", "500 G"),
                // Cannon button
                new Button(160, bottom - 140, 120, 120, "gui/towers/cannon_tower/icon.png", "Cannon\nTower", "500 G"),
                // Archer tower button
                new Button(300, bottom - 140, 120, 120, "gui/towers/archer_tower/icon.png", "Archer\nTower", "500 G"),
                // Wall button
                new Button(440, bottom - 140, 120, 120, "gui/towers/wall_tower/icon.png", "Walls", "500 G") };

        upgradeButtons = new Button[] {
                // Path 1
                new Button(160, bottom - 140, 120, 120, null, "", "500 G"),
                // Path 2
                new Button(300, bottom - 140, 120, 120, null, "", "500 G") };
    }

    public void draw(Graphics g, int i) {
        // Upper
        drawData(g);

        // Lower
        drawPanel(g);
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

        switch (mode) {
            case TOWER_SELECT -> drawTowerSelect(g);
            case TOWER_UPGRADE -> drawTowerUpgrade(g);
            case TOWER_PLACEMENT -> drawTowerPlacement(g);
            case PAUSE_MENU -> drawPauseMenu(g);
            case SETTINGS_MENU -> drawSettingsMenu(g);
        }
    }

    private void drawTowerSelect(Graphics g) {
        optionButtons[PAUSE_BUTTON].draw(g);
        optionButtons[NEXT_ROUND_BUTTON].draw(g);

        for (Button button : towerButtons) {
            button.draw(g);
        }
    }

    private void drawTowerUpgrade(Graphics g) {
        optionButtons[PAUSE_BUTTON].draw(g);
        optionButtons[NEXT_ROUND_BUTTON].draw(g);

        for (Button button : upgradeButtons) {
            button.draw(g);
        }
    }

    private void drawTowerPlacement(Graphics g) {
        optionButtons[CANCEL_BUTTON].draw(g);
    }

    // Menu

    private void drawPauseMenu(Graphics g) {
        
    }

    private void drawSettingsMenu(Graphics g) {

    }

}
