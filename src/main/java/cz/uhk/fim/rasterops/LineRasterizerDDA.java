package cz.uhk.fim.rasterops;

import cz.uhk.fim.rasterData.RasterBI;

public class LineRasterizerDDA implements LineRasterizer {

    @Override
    public void draw(RasterBI raster, int x1, int y1, int x2, int y2, int color) {
        int dx = x2-x1;
        int dy = y2-y1;

        int step;

        if (Math.abs(dx) > Math.abs(dy)) {
            step = Math.abs(dx);
        }else {
            step = Math.abs(dy);
        }

        float xIncrement = (float) dx / step;
        float yIncrement = (float) dy / step;

        float x = x1;
        float y = y1;

        for(int i = 0; i < step; i++) {
           raster.setColor(Math.round(x),Math.round(y),color);

            x += xIncrement;
            y+= yIncrement;
        }

        }
}
