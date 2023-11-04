package towerdefense.func;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {

    private static String DIR = "app/src/main/resources/";

    public static BufferedImage loadImage( String file ) {
        try {
            return ImageIO.read(new File( DIR + file ));
        }
        catch (IOException e) {
            System.out.println("Error opening image file: " + e.getMessage());
            System.out.println("Input file: \"" + DIR + file + "\"");
        }
        // Return an empty image for missing textures
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    }

    public static BufferedImage[] loadImages( String... files ) {
        BufferedImage[] images = new BufferedImage[files.length];

        for ( int i = 0; i < files.length; i++ ) {
            images[i] = loadImage( files[i] );
        }
        return images;
    }

    public static BufferedImage resizeImage( BufferedImage image, int width, int height ) {
        // Create a new surface
        BufferedImage newImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

        // Draw the old image onto the new image with new dimensions
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage( image, 0, 0, width, height, null );
        g2d.dispose();

        return newImage;
    }
    
}
