package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    private static void init() {
        // Create and name window
        JFrame window = new JFrame("TowerDefense");
        // Allow game to close
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel
        TowerDefense td = new TowerDefense();
        window.add(td);
        window.addKeyListener(td);
        window.addMouseListener(td);

        // Edit window attributes
        window.setResizable(false);
        window.pack();
        //window.setLocation(100,100);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });

    }

}
