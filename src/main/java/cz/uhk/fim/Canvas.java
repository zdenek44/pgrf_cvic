package cz.uhk.fim;

import cz.uhk.fim.rasterData.RasterBI;
import cz.uhk.fim.rasterops.LineRasteruzerTrivial;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas {
    private final JFrame frame;
    private final JPanel panel;
    private final RasterBI image;

    public Canvas(final int width,final int height) {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        image = new RasterBI(width, height);
        /*
        for (int i = 0; i < height; i++) {
            image.setRGB(i,i, 0xFF00FF);
            image.setRGB(height-1-i,i, 0xFF00FF);

            image.setRGB(i,0,0xff0000);
            image.setRGB(0,i,0xff0000);
            image.setRGB(i,499,0xff0000);
            image.setRGB(499,i,0xff0000);
        }
        */
//        image.clear(0x000000);
//            int x = height/4;
//        int y = (height/4)*3;
//
//        for (int i = x; i < y; i++) {
//            image.setColor(i,x,0x00FF00);
//            image.setColor(x,i,0x00FF00);
//            image.setColor(i,y-1,0x00FF00);
//            image.setColor(y-1,i,0x00FF00);
//
//        }

        LineRasteruzerTrivial line = new LineRasteruzerTrivial();
        line.draw(image,50,0,30,200,0xFF00FF);


        panel =new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                image.present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
    }

}
