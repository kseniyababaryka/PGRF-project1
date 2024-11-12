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
            Point2D p1 = (Point2D)clipPoints.get(clipPoints.size() - 1);

            Point2D p2;
            for(Iterator var6 = clipPoints.iterator(); var6.hasNext(); p1 = p2) {
                p2 = (Point2D)var6.next();
                newPoints = this.clipEdge(points, new Line(p1, p2));
                points = newPoints;
            }

            return newPoints;
        }
    }

//    private List<Point2D> clipEdge(List<Point2D> points, Line e) {
//        if (points.size() < 2) {
//            return points;
//        } else {
//            List<Point2D> out = new ArrayList();
//            out.clear();
//            Point2D v1 = (Point2D)points.get(points.size() - 1);
//
//            Point2D v2;
//            for(Iterator var6 = points.iterator(); var6.hasNext(); v1 = v2) {
//                v2 = (Point2D)var6.next();
//                if (e.inside(v2)) {
//                    if (!e.inside(v1)) {
//                        out.add(e.intersection(v1, v2));
//                    }
//
//                    out.add(v2);
//                } else if (e.inside(v1)) {
//                    out.add(e.intersection(v1, v2));
//                }
//            }
//
//            return out;
//        }
//    }

    private List<Point2D> clipEdge(List<Point2D> points, Line e) {
        if (points.size() < 2) {
            return points;
        }

        List<Point2D> out = new ArrayList<>();
        out.clear();

        // Get the last point in input list
        Point2D s = points.get(points.size() - 1);

        // Process all vertices
        for (Point2D p : points) {
            if (e.inside(p)) { // If current point is inside
                if (!e.inside(s)) { // If previous point was outside
                    // Add intersection point
                    out.add(e.intersection(s, p));
                }
                // Add current point
                out.add(p);
            } else if (e.inside(s)) { // If current point is outside but previous was inside
                // Add intersection point
                out.add(e.intersection(s, p));
            }
            s = p;
        }

        return out;
    }


}




