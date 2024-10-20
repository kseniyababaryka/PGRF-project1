package objectdata;

/**
 * Trida pro reprezentaci polygonu skaldaijici se s 2d bodu
 */



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

}
