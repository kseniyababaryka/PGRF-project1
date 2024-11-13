package objectdata;

public class Elipse extends Polygon{
    private int centerX;
    private int centerY;
    private int radiusX;
    private int radiusY;


    public Elipse(int c1, int r1, int c2, int r2) {
        int left = Math.min(c1, c2);
        int right = Math.max(c1, c2);
        int top = Math.min(r1, r2);
        int bottom = Math.max(r1, r2);


        centerX = (left + right) / 2;
        centerY = (top + bottom) / 2;
        radiusX = (right - left) / 2;
        radiusY = (bottom - top) / 2;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getRadiusX() {
        return radiusX;
    }

    public int getRadiusY() {
        return radiusY;
    }
}
