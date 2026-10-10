package cz.uhk.fim.rasterops;

import cz.uhk.fim.objectData.LineSegment;
import cz.uhk.fim.objectData.Polygon;
import cz.uhk.fim.rasterData.RasterBI;

import java.awt.*;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;

public class PolygonRasterizer {
    public void draw(final RasterBI raster, final Polygon polygon, final LineRasterizer lineRasterizer, final int color){
        List<LineSegment> listSegments = polygon.getLineSegments();

        for (int i = 0; i < listSegments.size(); i++) {
            int x1,x2,y1,y2;
            x1 = listSegments.get(i).p1.x;
            y1 = listSegments.get(i).p1.y;
            x2 = listSegments.get(i).p2.x;
            y2 = listSegments.get(i).p2.y;
                lineRasterizer.draw(raster, x1, y1, x2, y2, color);

            }

    }

    public void drawPreview(final RasterBI raster, final Polygon polygon,Point newPoint, final LineRasterizer lineRasterizer, final int color){
        List<LineSegment> listSegments = polygon.getLineSegmentsForPreview(newPoint);

        for (int i = 0; i < listSegments.size(); i++) {
            int x1,x2,y1,y2;
            x1 = listSegments.get(i).p1.x;
            y1 = listSegments.get(i).p1.y;
            x2 = listSegments.get(i).p2.x;
            y2 = listSegments.get(i).p2.y;
            if (i>listSegments.size()-3) {
                lineRasterizer.draw(raster, x1, y1, x2, y2, 0xFF0000);
            }else {
                lineRasterizer.draw(raster, x1, y1, x2, y2, color);
            }
        }
    }
}
