package towerdefense.game.gui;

import towerdefense.game.Game;

import java.awt.Rectangle;

import static towerdefense.func.ImageHandler.loadImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class Button {

    final private static int ARC_SIZE = 5;

    final private static int FONT_SIZE = 30;

    private static Font font;

    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage icon;
    private String text1;
    private String text2;

    private BufferedImage buttonImage;
    private BufferedImage detailsImage;

    public Button(int x, int y, int width, int height, String icon, String text1, String text2) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.icon = loadImage(icon);
        this.text1 = text1;
        this.text2 = text2;

        if (font == null)
            loadFont();

        createButtonImage();
    }

    private void loadFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(getClass().getResource("/gui/font/dataFont.otf").getFile())).deriveFont((float) FONT_SIZE);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createButtonImage() {
        buttonImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buttonImage.createGraphics();

        g2d.setColor(new Color(100, 230, 120));
        g2d.fillRoundRect(0, 0, width, height, ARC_SIZE, ARC_SIZE);
        g2d.setColor(new Color(70, 200, 110));
        g2d.drawRoundRect(0, 0, width, height, ARC_SIZE, ARC_SIZE);

        updateButtonDetails();
    }

    private void updateButtonDetails() {
        detailsImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = detailsImage.createGraphics();

        // Icon
        g2d.drawImage(this.icon, x + (width - this.icon.getWidth()) / 2, y + (height - this.icon.getHeight()) / 2,
                Game.instance.panel);

        // Text
        g2d.setFont(font);
        Rectangle text1Metrics = font.getStringBounds(text1, g2d.getFontRenderContext()).getBounds();
        g2d.drawString(text1,
                x + (int) (width - text1Metrics.getWidth()) / 2,
                y + (int) (text2 == null ? (width - text1Metrics.getWidth()) / 2 : (5 + text1Metrics.getHeight())));

        if (text2 != null) {
            Rectangle text2Metrics = font.getStringBounds(text2, g2d.getFontRenderContext()).getBounds();
            g2d.drawString(text2,
                    x + (int) (width - text2Metrics.getWidth()) / 2,
                    y + height - 5);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(buttonImage, x, y, Game.instance.panel);
        g.drawImage(detailsImage, x, y, Game.instance.panel);
    }

}
