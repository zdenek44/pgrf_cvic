package cz.uhk.fim.rasterops;

import cz.uhk.fim.rasterData.RasterBI;

import static java.lang.Math.abs;

public class LineRasterizerTrivial implements LineRasterizer {

    @Override
    public void draw(RasterBI raster, int x1, int y1, int x2, int y2, int color) {
        final double k = (double) (y2 - y1) / (x2 - x1);
        final double q = y1 - k * x1;

        if (x2 - x1 == 0) {
            for (int i = y1; i < y2; i++) {

                raster.setColor(x1, i, color);
            }
        }
        if (x2 < x1) {
            for (int x = x2; x < x1; x++) {
                int y = (int) (k * x + q);
                raster.setColor(x, y, color);
            }
        } else {
            for (int x = x1; x < x2; x++) {
                int y = (int) (k * x + q);
                raster.setColor(x, y, color);
            }
        }
        if (y2 < y1) {
            for (int y = y2; y < y1; y++) {
                int x = (int) ((y - q) / k);
                raster.setColor(x, y, color);
            }
        } else {
            for (int y = y1; y < y2; y++) {
                int x = (int) ((y - q) / k);
                raster.setColor(x, y, color);
            }
        }

    }

}

