package towerdefense.game.gui;

import towerdefense.game.Game;

import java.awt.Rectangle;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.resizeImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class Button {

    final private static int ARC_SIZE = 15;
    final private static int BORDER = 5;

    final private static int FONT_SIZE = 20;

    private static Font font;

    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage icon;
    private String text1;
    private String text2;

    private boolean hovered;

    private BufferedImage buttonImage;
    private BufferedImage hoveredImage;
    private BufferedImage clickedImage;
    private BufferedImage detailsImage;

    public Button(int x, int y, int width, int height, String icon, String text1, String text2) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.icon = icon == null ? null : resizeImage(loadImage(icon), width * 2 / 3, height * 2 / 3);
        this.text1 = text1;
        this.text2 = text2;

        hovered = false;

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
        int w = width - 2 * BORDER;
        int h = height - 2 * BORDER;

        buttonImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        hoveredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        clickedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        drawBackground(buttonImage.createGraphics(), new Color(100, 230, 120), w, h);
        drawBackground(hoveredImage.createGraphics(), new Color(200, 250, 215), w, h);
        drawBackground(clickedImage.createGraphics(), new Color(70, 160, 80), w, h);

        updateButtonDetails();
    }

    private void drawBackground( Graphics2D g2d, Color color, int w, int h ) {
        g2d.setColor(color);
        g2d.fillRect(BORDER, BORDER, w, h);
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(new Color(70, 200, 110));
        g2d.drawRoundRect(BORDER, BORDER, w, h, ARC_SIZE, ARC_SIZE);
    }

    private void updateButtonDetails() {
        detailsImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = detailsImage.createGraphics();

        // Icon
        if (icon != null) {
            g2d.drawImage(icon, (width - icon.getWidth()) / 2, (height - icon.getHeight()) / 2,
                    Game.instance.panel);
        }

        // Text
        g2d.setFont(font);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);

        String[] text = text1.split("\n");
        for (int i = 0; i < text.length; i++) {
            Rectangle stringMetrics = font.getStringBounds(text[i], g2d.getFontRenderContext()).getBounds();
            g2d.drawString(text[i],
                    (int) (width - stringMetrics.getWidth()) / 2,
                    (int) (text2 == "" ? (width - stringMetrics.getWidth()) / 2
                            : 8 + (i + 1) * (stringMetrics.getHeight() - 5)));
        }

        Rectangle text2Metrics = font.getStringBounds(text2, g2d.getFontRenderContext()).getBounds();
        g2d.drawString(text2,
                (int) (width - text2Metrics.getWidth()) / 2,
                height - 12);
    }

    public void draw(Graphics g) {
        hovered = Game.instance.mx < x != Game.instance.mx < x + width && Game.instance.my < y != Game.instance.my < y + height;

        g.drawImage(hovered ? Game.instance.mousePressed ? clickedImage : hoveredImage : buttonImage, x, y, Game.instance.panel);
        g.drawImage(detailsImage, x, y, Game.instance.panel);
    }

}
