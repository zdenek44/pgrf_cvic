package cz.uhk.fim;

import cz.uhk.fim.objectData.Polygon;
import cz.uhk.fim.rasterData.RasterBI;
import cz.uhk.fim.rasterops.LineRasterizerTrivial;
import cz.uhk.fim.rasterops.PolygonRasterizer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.MouseEvent.*;

public class Canvas {
    private final JFrame frame;
    private final JPanel panel;
    private final JPanel menuListPane;
    private final JScrollPane scrollPane;
    private final RasterBI image;
    private int currentObject;
    private final ArrayList<cz.uhk.fim.objectData.Polygon> objects;

    private final LineRasterizerTrivial line = new LineRasterizerTrivial();
    private final PolygonRasterizer poly = new PolygonRasterizer();

    public Canvas(final int width,final int height) {

        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        image = new RasterBI(width, height);

        objects = new ArrayList<>();
        objects.add(new Polygon());



        panel =new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                image.present(g);
            }
        };

        panel.repaint();
        panel.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                image.clear(0x000000);
                objects.get(currentObject).add_points(new Point(e.getX(),e.getY()));
                poly.draw(image,objects.get(currentObject),line,0xFF00FF);
                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                image.clear(0x000000);
                objects.get(currentObject).add_points(new Point(e.getX(),e.getY()));
                poly.draw(image,objects.get(currentObject),line,0xFF00FF);
                panel.repaint();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                image.clear(0x000000);
                Point pt = new Point(e.getX(),e.getY());
                poly.drawPreview(image,objects.get(currentObject),pt,line,0xFF0000);
                panel.repaint();
            }
        });

        menuListPane = new JPanel();
        menuListPane.setLayout(new FlowLayout());
        menuListPane.setPreferredSize(new Dimension(110,1000));

        JButton add = new JButton("add object");
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        add.setAlignmentY(Component.CENTER_ALIGNMENT);
        add.setPreferredSize(new Dimension(100,30));

        add.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()){
                    case BUTTON1:
                        objects.add(new Polygon());
                        update_list();
                        menuListPane.repaint();
                        menuListPane.revalidate();
                        break;
                    case BUTTON2:
                        break;
                }
            }
        });
        menuListPane.add(add);

        scrollPane = new JScrollPane(menuListPane);
        scrollPane.createVerticalScrollBar();


        update_list();


        frame.add(panel, BorderLayout.CENTER);
        frame.add(scrollPane,BorderLayout.EAST);
        frame.pack();

    }

    private void render(){
        //todo
        }

    private void update_list(){

            JButton lb = new JButton(Integer.toString(objects.size()-1));
            lb.setAlignmentX(Component.CENTER_ALIGNMENT);
            lb.setAlignmentY(Component.CENTER_ALIGNMENT);
            lb.setPreferredSize(new Dimension(100,30));
            lb.addMouseListener(new MouseInputAdapter(){

                @Override
                public void mouseClicked(MouseEvent e) {

                    switch (e.getButton()) {
                        case BUTTON1:
                            setCurrentObject(Integer.parseInt(lb.getText()));
                            break;
                        case BUTTON2:
                            break;
                    }
                }
            });
            menuListPane.add(lb);
    }

    private void setCurrentObject(int i){
        currentObject = i;
       updateCanvas();

    }

    void updateCanvas(){
        image.clear(0x000000);
        if (objects.get(currentObject).isEmpty()){
            System.out.println("empty");
        }else {
            poly.draw(image, objects.get(currentObject), line, 0xFF00FF);
        }
        panel.repaint();
    }

}
