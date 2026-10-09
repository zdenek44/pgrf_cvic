package cz.uhk.fim.rasterops;

import cz.uhk.fim.objectData.LineSegment;
import cz.uhk.fim.rasterData.RasterBI;

import java.awt.*;
import java.awt.image.Raster;

public interface LineRasterizer {

    /**
     * draws a straight line between two points into given raster
     *
     * @param raster two points
     * @param x1     column address of a starting pixel
     * @param y1     row address of a starting pixel
     * @param x2     column address of a ending pixel
     * @param y2     row address of a ending pixel
     * @param color
     */
    void draw(RasterBI raster, int x1, int y1, int x2, int y2, int color);
}

