package objectdata;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Point2D> points;

    // constructor
    public Polygon() {
        points = new ArrayList<>(); //implemented by array
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public Point2D getPoint(int idx) {
        return points.get(idx);
    }

    public int size() {
        return points.size();
    }

}
