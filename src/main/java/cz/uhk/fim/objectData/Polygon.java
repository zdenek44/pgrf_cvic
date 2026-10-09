package cz.uhk.fim.objectData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private final List<Point> points;

    public Polygon(List<Point> points){
        this.points = points;
    }

    public Polygon(){
        points = new ArrayList<>();
    }

    public void add_points(Point pt){
        points.addFirst(pt);
    }

    public List<LineSegment> getLineSegments() {
        final List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < points.size()-1; i++) {
                lineSegments.add(new LineSegment(points.get(i), points.get(i + 1)));
            }
        lineSegments.add(new LineSegment(points.getFirst(),points.getLast()));
        return lineSegments;
    }

    public List<LineSegment> getLineSegmentsForPreview(Point pt) {
        final List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < points.size()-1; i++) {
            lineSegments.add(new LineSegment(points.get(i), points.get(i + 1)));
        }
        lineSegments.add(new LineSegment(points.getFirst(),pt));
        lineSegments.add(new LineSegment(pt,points.getLast()));
        return lineSegments;
    }

    public boolean isEmpty(){
        if (points.isEmpty()){
            return true;
        }else {
            return false;
        }
    }



}
