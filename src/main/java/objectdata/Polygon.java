package objectdata;

/**
 * Trida pro reprezentaci polygonu skaldaijici se s 2d bodu
 */



import objectdata.Line;
import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Point2D> points; //Seznam vrcholu

    // Konstruktor
    public Polygon() {
        points = new ArrayList<>(); //Implementovano jako pole
    }

    /**
     * Pridava vrchol (bod) do polygonu
     * @param point Vrchol (bod), ktery se ma pridat
     */

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public Point2D getPoint(int idx) {
        return points.get(idx);
    }

    public int size() {
        return points.size(); //Vraci pocet vrcholu (bodu) polygonu
    }

    public List<Line> toLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point2D nextPoint = points.get((i + 1) % points.size());
            lines.add(new Line(points.get(i), nextPoint));
        }
        return lines;
    }

    public List<Point2D> getPoints() {
        return points;
    }
}
