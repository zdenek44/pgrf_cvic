package cz.uhk.fim;

import cz.uhk.fim.objectData.Polygon;
import cz.uhk.fim.rasterData.RasterBI;
import cz.uhk.fim.rasterops.LineRasterizer;
import cz.uhk.fim.rasterops.LineRasterizerTrivial;
import cz.uhk.fim.rasterops.PolygonRasterizer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import static java.awt.event.MouseEvent.*;

public class Canvas {
    private final JFrame frame;
    private JPanel panel;
    private final JPanel menuListPane;
    private final JScrollPane scrollPane;
    private final RasterBI image;
    private final JButton add;
    private int currentObject;
    private boolean isDraggingPoint = false;
    private int selectedPoint;
    private final ArrayList<cz.uhk.fim.objectData.Polygon> objects;

    private final LineRasterizer line = new LineRasterizerTrivial();
    private final PolygonRasterizer poly = new PolygonRasterizer();

    public Canvas(final int width,final int height) {

        add = new JButton("add object");
        frame = new JFrame();
        image = new RasterBI(width, height);
        objects = new ArrayList<>();
        menuListPane = new JPanel();
        scrollPane = new JScrollPane(menuListPane);


        initialize();
        setMouseEvents();
        update_list();


        frame.add(panel, BorderLayout.CENTER);
        frame.add(scrollPane,BorderLayout.EAST);
        frame.pack();

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

    private void initialize(){
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialize first object
        objects.add(new Polygon());

        //set add button
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        add.setAlignmentY(Component.CENTER_ALIGNMENT);
        add.setPreferredSize(new Dimension(100,30));

        panel =new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                image.present(g);
            }
        };

        menuListPane.setLayout(new FlowLayout());
        menuListPane.setPreferredSize(new Dimension(110,1000));
        menuListPane.add(add);

        scrollPane.createVerticalScrollBar();

        panel.repaint();
    }

    private void setMouseEvents(){
        panel.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()){
                    case BUTTON1:
                        image.clear(0x000000);
                       // objects.get(currentObject).add_points(new Point(e.getX(),e.getY()));
                        poly.draw(image,objects.get(currentObject),line,0xFF00FF);
                        panel.repaint();
                        break;
                    case BUTTON3:

                        break;
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                switch (e.getButton()) {
                    case BUTTON1:
                        if (objects.get(currentObject).isEmpty()){
                            objects.get(currentObject).add_points(new Point(e.getX(),e.getY()));
                        }
                        break;
                    case BUTTON3:
                        selectedPoint = objects.get(currentObject).getInPointProximity(new Point(e.getX(),e.getY()));
                        System.out.println("proximity" + selectedPoint);
                        if (selectedPoint != -1){
                            isDraggingPoint = true;
                        }

                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(e.getButton());
                switch (e.getButton()){
                    case BUTTON1:
                        image.clear(0x000000);
                       objects.get(currentObject).add_points(new Point(e.getX(),e.getY()));
                        poly.draw(image,objects.get(currentObject),line,0xFF00FF);
                        break;
                    case BUTTON3:
                        objects.get(currentObject).updatePoint(selectedPoint,e.getX(),e.getY());
                        isDraggingPoint = false;
                        break;
                }
                panel.repaint();
            }
        });

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

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                image.clear(0x000000);
                if (isDraggingPoint){
                    objects.get(currentObject).updatePoint(selectedPoint,e.getX(),e.getY());
                    updateCanvas();
                }else {
                    System.out.println(e.getButton());

                    Point pt = new Point(e.getX(), e.getY());
                    poly.drawPreview(image, objects.get(currentObject), pt, line, 0xFF00FF);
                }
                panel.repaint();
            }

        });
    }

}
