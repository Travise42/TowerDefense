package towerdefense.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class UI {

    private Font font;
    
    public UI() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(getClass().getResource("/gui/font/dataFont.otf").getFile()));
            font = font.deriveFont(32f); // Set size to 32
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void draw( Graphics g, int i ) {
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Test", 10, 20);
    }
    
}
