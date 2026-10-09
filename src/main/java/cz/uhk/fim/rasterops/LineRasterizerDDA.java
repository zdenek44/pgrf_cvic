package cz.uhk.fim.rasterops;

import cz.uhk.fim.rasterData.RasterBI;

public class LineRasterizerDDA implements LineRasterizer {
    @Override
    public void draw(RasterBI raster, int x1, int y1, int x2, int y2, int color) {
        int dx,dy;
        dx = x2-x1;
        dy = y2-y1;

        int steps = Math.max(Math.abs(dx),Math.abs(dy));
        int xInkrement = dx/steps;
        int yInkrement = dy/steps;

        int x = x1;
        int y = y1;
        raster.setColor(x,y,color);

        for (int i = 0; i < steps; i++) {
            raster.setColor(x,y,color);
            x = x + xInkrement;
            y = y + yInkrement;
        }
    }
}
