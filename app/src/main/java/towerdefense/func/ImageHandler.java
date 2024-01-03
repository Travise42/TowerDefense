package towerdefense.func;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {

    private static String DIR = "/app/src/main/resources/";

    public static BufferedImage loadImage( String path ) {
        try {
            return ImageIO.read( ImageHandler.class.getClassLoader().getResourceAsStream( path ) );
        }
        catch (IOException | IllegalArgumentException e) {
            System.out.println("Error opening image file: " + e.getMessage());
            System.out.println("Input file: \"" + DIR + path + "\"");
        }
        // Return an empty image for missing textures
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    public static BufferedImage[] loadImages( String... files ) {
        BufferedImage[] images = new BufferedImage[files.length];

        for ( int i = 0; i < files.length; i++ ) {
            images[i] = loadImage( files[i] );
        }
        return images;
    }

    public static BufferedImage restoreTransparencyOf( BufferedImage image ) {
        BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        transparentImage.getGraphics().drawImage(image, 0, 0, null);
        return transparentImage;
    }

    public static BufferedImage resizeImage( BufferedImage image, int width, int height ) {
        return resizeImage( image, width, height, BufferedImage.TYPE_INT_ARGB );
    }

    public static BufferedImage resizeImage( BufferedImage image, int width, int height, int type ) {
        // Create a new surface
        BufferedImage newImage = new BufferedImage( width, height, type );

        // Draw the old image onto the new image with new dimensions
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage( image, 0, 0, width, height, null );
        g2d.dispose();

        return newImage;
    }

    public static BufferedImage rotateImage( BufferedImage image, double radians) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        
        g2d.rotate(radians, width / 2, height / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
    
}
