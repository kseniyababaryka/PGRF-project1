package rasterops;

import objectdata.Point2D;
import objectdata.Polygon;
import rasterdata.RasterBufferedImage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine {
public void draw(RasterBufferedImage img, Polygon polygon, int color) {
    if (polygon.getPoints().size() < 3) return;

    // Find min and max y
    int ymin = Integer.MAX_VALUE;
    int ymax = Integer.MIN_VALUE;

    for (Point2D point : polygon.getPoints()) {
        ymin = Math.min(ymin, (int)point.getR1());
        ymax = Math.max(ymax, (int)point.getR1());
    }

    // For each scan line
    for (int y = ymin; y <= ymax; y++) {
        List<Integer> intersections = new ArrayList<>();

        // Find intersections with all edges
        for (int i = 0; i < polygon.getPoints().size(); i++) {
            Point2D current = polygon.getPoints().get(i);
            Point2D next = polygon.getPoints().get((i + 1) % polygon.getPoints().size());

            // Check if the edge crosses this scanline
            if ((current.getR1() <= y && next.getR1() > y) ||
                    (current.getR1() > y && next.getR1() <= y)) {

                // Calculate x-intersection
                int x = (int)(current.getC1() + (y - current.getR1()) *
                        (next.getC1() - current.getC1()) /
                        (next.getR1() - current.getR1()));
                intersections.add(x);
            }
        }

        // Sort intersections
        Collections.sort(intersections);

        // Fill between pairs of intersections
        for (int i = 0; i < intersections.size() - 1; i += 2) {
            int x1 = intersections.get(i);
            int x2 = intersections.get(i + 1);

            for (int x = x1; x <= x2; x++) {
                if (x >= 0 && x < img.width() && y >= 0 && y < img.height()) {
                    img.setColor(x, y, color);
                }
            }
        }
    }
}
}
