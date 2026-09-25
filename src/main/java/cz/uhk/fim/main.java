package cz.uhk.fim;

import javax.swing.*;
import java.awt.*;

public Class Canvas {

    private final JFrame frame;
    private final JPanel panel;

    public canvas() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel=new JPanel();
        frame.add(panel, BorderLayout.CENTER);
    }

    static void main(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new canvas();
            }
        });
    }
}