package objectops;

import objectdata.Line;
import objectdata.Point2D;
import objectdata.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SutherlandHodgman {

    public List<Point2D> clip(List<Point2D> points, List<Point2D> clipPoints) {
        if (clipPoints.size() < 2) {
            return points;
        } else {
            List<Point2D> newPoints = points;
            Point2D p1 = clipPoints.get(clipPoints.size() - 1);

            Point2D p2;
            for(Iterator var6 = clipPoints.iterator(); var6.hasNext(); p1 = p2) {
                p2 = (Point2D)var6.next();
                newPoints = this.clipEdge(points, new Line(p1, p2));
                points = newPoints;
            }

            return newPoints;
        }
    }



    private List<Point2D> clipEdge(List<Point2D> points, Line e) {
        if (points.size() < 2) {
            return points;
        }

        List<Point2D> out = new ArrayList<>();
        out.clear();
        Point2D s = points.get(points.size() - 1);


        for (Point2D p : points) {
            if (e.inside(p)) {
                if (!e.inside(s)) {
                    out.add(e.intersection(s, p));
                }
                out.add(p);
            } else if (e.inside(s)) {
                out.add(e.intersection(s, p));
            }
            s = p;
        }

        return out;
    }


}




