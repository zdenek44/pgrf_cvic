package cz.uhk.fim;

import cz.uhk.fim.objectData.Polygon;
import cz.uhk.fim.rasterData.RasterBI;
import cz.uhk.fim.rasterops.LineRasterizer;
import cz.uhk.fim.rasterops.LineRasterizerDDA;
import cz.uhk.fim.rasterops.LineRasterizerTrivial;
import cz.uhk.fim.rasterops.PolygonRasterizer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
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
    private final JButton switchLineRasterizer;
    private boolean isDraggingPoint = false;
    private int selectedPoint;
    private int whichLineRasterizer = 0;
    private final ArrayList<cz.uhk.fim.objectData.Polygon> objects;

    private LineRasterizer line = new LineRasterizerTrivial();
    private final PolygonRasterizer poly = new PolygonRasterizer();


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Canvas (500,500);

            }
        });
    }

    public Canvas(final int width,final int height) {

        add = new JButton("add object");
        switchLineRasterizer = new JButton("Trivial");
        frame = new JFrame();
        image = new RasterBI(width, height);
        objects = new ArrayList<>();
        menuListPane = new JPanel();
        scrollPane = new JScrollPane(menuListPane);


        initialize();
        setEvents();
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

        //set switching button
        switchLineRasterizer.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchLineRasterizer.setAlignmentY(Component.CENTER_ALIGNMENT);
        switchLineRasterizer.setPreferredSize(new Dimension(100,50));

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
        menuListPane.setPreferredSize(new Dimension(130,1000));
        menuListPane.add(switchLineRasterizer);
        menuListPane.add(add);

        scrollPane.createVerticalScrollBar();

        updateCanvas();
    }

    private void setEvents(){

        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke('c'), "actionC");
        actionMap.put("actionC", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               objects.get(currentObject).emptyObject();
               updateCanvas();
            }
        });

        panel.addMouseListener(new MouseInputAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()){
                    case BUTTON1:
                        updateCanvas();
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
                            objects.get(currentObject).add_points(withinBoundaries(e.getX(),e.getY()));
                        }
                        break;
                    case BUTTON3:
                        selectedPoint = objects.get(currentObject).getInPointProximity(withinBoundaries(e.getX(),e.getY()));
                        System.out.println("proximity" + selectedPoint);
                        if (selectedPoint != -1){
                            isDraggingPoint = true;
                        }

                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()){
                    case BUTTON1:
                       objects.get(currentObject).add_points(withinBoundaries(e.getX(),e.getY()));
                        break;
                    case BUTTON3:
                        if (selectedPoint!=-1) {
                            objects.get(currentObject).updatePoint(selectedPoint, withinBoundaries(e.getX(), e.getY()));
                        }
                        isDraggingPoint = false;
                        break;
                }
                updateCanvas();
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

        switchLineRasterizer.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (whichLineRasterizer+1 == 2){
                    whichLineRasterizer = 0;
                }else {
                    whichLineRasterizer++;
                }

                switch (whichLineRasterizer){
                    case 0:
                        switchLineRasterizer.setText("Trivial");
                        line = new LineRasterizerTrivial();
                        break;
                    case 1:
                        switchLineRasterizer.setText("DDA");
                        line = new LineRasterizerDDA();
                        break;
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                image.clear(0x000000);
                if (isDraggingPoint){
                    objects.get(currentObject).updatePoint(selectedPoint,withinBoundaries(e.getX(),e.getY()));
                    updateCanvas();
                }else {
                    Point pt = withinBoundaries(e.getX(), e.getY());
                    poly.drawPreview(image, objects.get(currentObject), pt, line, 0xFF00FF);
                }
                panel.repaint();
            }
        });
    }

    public Point withinBoundaries(int x,int y){
        int width = image.getWidth();
        int height = image.getHeight();
        if (x>width){
            x = width;
        }else if (x<0){
            x = 0;
        }

        if (y>height){
            y = height;
        } else if (y<0) {
            y = 0;
        }
        return new Point(x,y);
    }

}
