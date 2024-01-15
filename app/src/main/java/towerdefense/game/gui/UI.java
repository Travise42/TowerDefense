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

public class UI {

    final private static String HEALTH_IMAGE_LOCATION = "gui/ui/health.png";
    final private static String GOLD_IMAGE_LOCATION = "gui/ui/gold.png";

    final private static int DATA_SIZE = 32;

    private BufferedImage health;
    private BufferedImage gold;

    private Font font;
    
    public UI() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/gui/font/dataFont.otf").getFile()));
            font = font.deriveFont((float) DATA_SIZE); // Set size to DATA_SIZE
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        health = resizeImage(loadImage(HEALTH_IMAGE_LOCATION), DATA_SIZE, DATA_SIZE);
        gold = resizeImage(loadImage(GOLD_IMAGE_LOCATION), DATA_SIZE, DATA_SIZE);
    }

    public void draw( Graphics g, int i ) {
        // Draw health and gold amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(Game.instance.player.health), 20 + DATA_SIZE, 10 + DATA_SIZE);
        g.drawString(String.valueOf(Game.instance.player.gold), 140 + DATA_SIZE, 10 + DATA_SIZE);

        // Draw health and gold icons
        g.drawImage(health, 10, 15, Game.instance.panel);
        g.drawImage(gold, 130, 15, Game.instance.panel);
    }
    
}
