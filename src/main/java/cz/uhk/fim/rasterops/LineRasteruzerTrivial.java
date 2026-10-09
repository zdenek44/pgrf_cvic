package cz.uhk.fim.rasterops;

import cz.uhk.fim.rasterData.RasterBI;

import static java.lang.Math.abs;

public class LineRasteruzerTrivial implements LineRasterizer {

    @Override
    public void draw(RasterBI raster, int x1, int y1, int x2, int y2, int color) {
        int dx;
        int dy;
        int x;
        int y;

        dx = x2-x1;
        dy = y2-y1;

            if (dx==0) {
                for (int i = y1; i < y2; i++) {
                    raster.setColor(x1,i,color);
                }
                return;
            }
          final double k = (double) dy / dx;
            final double q = y1-(k*x1);
        if (abs(dy)<abs(dx)){
            if (x2<x1){
                for (x = x2; x < x1; x++) {
                    y= (int) (k*x+q);
                    raster.setColor(x,y,color);
                }
            }else {
                for (x = x1; x < x2; x++) {
                    y = (int) (k * x + q);
                    raster.setColor(x, y, color);
                }
            }
        }else {
            if (y2<y1){
                for (y = y1; y < y2; y++) {
                    x = (int) ((y-q)/k);
                    raster.setColor(x,y,color);
                }
            }else {
                for (y = y1; y < y2; y++) {
                    x = (int) ((y-q)/k);
                    raster.setColor(x,y,color);
                }
            }

        }

    }


}
