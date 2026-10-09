package cz.uhk.fim;

import cz.uhk.fim.rasterData.RasterBI;
import cz.uhk.fim.rasterops.LineRasteruzerTrivial;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
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

        LineRasteruzerTrivial line = new LineRasteruzerTrivial();
        line.draw(image,50,0,50,200,0xFF00FF);



        panel =new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                image.present(g);
            }
        };

        panel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                line.draw(image,0,0,(int)e.getX(),(int)e.getY(),0xFF00FF);
                System.out.println(e.getX()+" "+e.getY());
                panel.repaint();
            }

        });

        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
    }

    private void render(){
        //todo
        }

}
