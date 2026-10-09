package cz.uhk.fim.objectData;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private final List<Point> points;

    public Polygon() {
        points = List.of();
    }

    private Polygon(final List<Point> points) {
        this.points = points;
    }

    /**
     *
     * @param point
     * @return
     */
    public Polygon withPoint(final Point point){
        final List<Point> newPoints = new ArrayList<>(this.points);
        newPoints.add(point);
        return new Polygon(newPoints);
    }
}
