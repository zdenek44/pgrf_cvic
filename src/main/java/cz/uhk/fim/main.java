package cz.uhk.fim;

import cz.uhk.fim.rasterData.RasterBI;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new Canvas(500,500);

            }
        });
    }
    }