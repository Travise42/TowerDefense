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

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;

public class Button {

    final private static int ARC_SIZE = 15;
    final private static int BORDER_WIDTH = 5;

    final private static int FONT_SIZE = 20;

    final public static int GREEN = 0;
    final public static int RED = 1;

    final private static int BORDER = 0;
    final private static int STANDARD = 1;
    final private static int HIGHLIGHT = 2;
    final private static int CONTRAST = 3;

    final private static Color[][] colors = {
            { new Color(70, 200, 110), new Color(100, 230, 120), new Color(200, 250, 215), new Color(70, 160, 80) },
            { new Color(191, 61, 61), new Color(181, 20, 20), new Color(242, 94, 94), new Color(148, 41, 41) }
    };

    private static Font font;

    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage icon;
    private String text1;
    private String text2;

    private boolean hovered;
    private boolean visible;
    private int color;

    private BufferedImage buttonImage;
    private BufferedImage hoveredImage;
    private BufferedImage clickedImage;
    private BufferedImage detailsImage;

    private Function function;

    private static final List<Button> instances = new ArrayList<>();

    public interface Function {
        void run();
    }

    public Button(int x, int y, int width, int height, int color, String icon, String text1, String text2,
            Function function) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.icon = icon == null ? null : resizeImage(loadImage(icon), width * 2 / 3, height * 2 / 3);
        this.text1 = text1;
        this.text2 = text2;
        this.function = function;

        hovered = false;
        visible = false;

        if (font == null)
            loadFont();

        createButtonImage();
        updateButtonDetails();

        instances.add(this);
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
        int w = width - 2 * BORDER_WIDTH;
        int h = height - 2 * BORDER_WIDTH;

        buttonImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        hoveredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        clickedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        drawBackground(buttonImage.createGraphics(), STANDARD, w, h);
        drawBackground(hoveredImage.createGraphics(), HIGHLIGHT, w, h);
        drawBackground(clickedImage.createGraphics(), CONTRAST, w, h);
    }

    private void drawBackground(Graphics2D g2d, int type, int w, int h) {
        g2d.setColor(colors[color][type]);
        g2d.fillRect(BORDER_WIDTH, BORDER_WIDTH, w, h);
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(colors[color][BORDER]);
        g2d.drawRoundRect(BORDER_WIDTH, BORDER_WIDTH, w, h, ARC_SIZE, ARC_SIZE);
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
        hovered = visible && Game.instance.mx < x != Game.instance.mx < x + width
                && Game.instance.my < y != Game.instance.my < y + height;

        g.drawImage(hovered ? Game.instance.mousePressed ? clickedImage : hoveredImage : buttonImage, x, y,
                Game.instance.panel);
        g.drawImage(detailsImage, x, y, Game.instance.panel);
    }

    public void click() {
        function.run();
    }

    public void hide() {
        visible = false;
        hovered = false;
    }

    public void show() {
        visible = true;
    }

    public void moveY(int newY) {
        y = newY;
    }

    public int getY() {
        return y;
    }

    public void setColor(int newColor) {
        if (color == newColor)
            return;
        color = newColor;
        createButtonImage();
    }

    public int getColor() {
        return color;
    }

    public static boolean clickHovered() {
        for (Button button : instances) {
            if (button.hovered) {
                button.click();
                return true;
            }
        }
        return false;
    }

    public static void hideAll() {
        for (Button button : instances) {
            button.hide();
        }
    }

    public static void drawVisibleButtons(Graphics g) {
        for (Button button : instances) {
            if (button.visible)
                button.draw(g);
        }
    }

}
