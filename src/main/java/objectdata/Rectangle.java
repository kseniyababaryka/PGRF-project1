package objectdata;

import java.awt.*;

public class Rectangle extends Polygon {

    public Rectangle(int c1, int r1, int c2, int r2) {
        int left = Math.min(c1, c2);
        int right = Math.max(c1, c2);
        int top = Math.min(r1, r2);
        int bottom = Math.max(r1, r2);


        addPoint(new Point2D(left, top));
        addPoint(new Point2D(right, top));
        addPoint(new Point2D(right, bottom));
        addPoint(new Point2D(left, bottom));
    }

    public int getLeft() {
        return getPoint(0).getC1();
    }

    public int getRight() {
        return getPoint(2).getC1();
    }

    public int getTop() {
        return getPoint(0).getR1();
    }

    public int getBottom() {
        return getPoint(2).getR1();
    }


}
