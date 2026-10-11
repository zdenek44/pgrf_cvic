package cz.uhk.fim.objectData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public Polygon() {
        points = new ArrayList<>();
    }

    public void add_points(Point pt) {
        points.addFirst(pt);
    }

    public List<LineSegment> getLineSegments() {
        final List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            lineSegments.add(new LineSegment(points.get(i), points.get(i + 1)));
        }
        lineSegments.add(new LineSegment(points.getFirst(), points.getLast()));
        return lineSegments;
    }

    public List<LineSegment> getLineSegmentsForPreview(Point pt) {
        final List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            lineSegments.add(new LineSegment(points.get(i), points.get(i + 1)));
        }
        lineSegments.add(new LineSegment(points.getFirst(), pt));
        lineSegments.add(new LineSegment(pt, points.getLast()));
        return lineSegments;
    }

    public boolean isEmpty() {
        if (points.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public int getInPointProximity(Point pt) {
        int x, y;
        int offset = 20;
        for (int i = 0; i < points.size(); i++) {
            x = points.get(i).x;
            y = points.get(i).y;
            if (pt.x < x + offset && pt.x > x - offset && pt.y > y - offset && pt.y < y + offset) {
                return i;
            }
        }
        return -1;
    }

    public void updatePoint(int which, int x, int y) {
        points.get(which).x = x;
        points.get(which).y = y;
    }

    public void emptyObject() {
        points = new ArrayList<>();
    }

    public void updatePoint(int selectedPoint, Point point) {
        points.get(selectedPoint).setLocation(point);

    }
}
