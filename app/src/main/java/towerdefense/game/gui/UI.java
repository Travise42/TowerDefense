package towerdefense.game.gui;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        drawPauseButton(g);
    }

    private void drawTowerUpgrade(Graphics g) {
        drawPauseButton(g);
    }

    private void drawTowerPlacement(Graphics g) {
        drawCancelButton(g);
    }

    // Menu

    private void drawPauseButton(Graphics g) {

    }

    private void drawCancelButton(Graphics g) {

    }

    private void drawPauseMenu(Graphics g) {

    }

    private void drawSettingsMenu(Graphics g) {

    }

}
