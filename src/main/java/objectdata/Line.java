package objectdata;

import java.awt.*;
import java.util.Optional;
import objectdata.Point2D;

public class Line {
    private final Point2D start;
    private final Point2D end;

    public Line(Point2D start, Point2D end){
        this.start = start;
        this.end = end;
    }

    public Line(int x1, int y1, int x2, int y2){
        this.start = new Point2D(x1,y1);
        this.end = new Point2D(x2,y2);
    }

//    public Line withStart(Point2D point){
//        return new Line(point, this.end);
//    }



    public boolean isHorizontal(){
        return start.getR1() == end.getR1();
    }

    public Optional<Float> yIntercept(int y){
        if (!hasIntercept(y)) {
            return Optional.empty();
        } else {
            double k = (double) (end.getR1() - start.getR1()) / (end.getC1() - start.getC1());
            double q = start.getR1() - k * start.getC1();
            float x = (float) ((float) (y - q) / k);

            return Optional.of(x);
        }
    }

    public boolean hasIntercept(int y){
        if (start.getR1() < end.getR1()){
            return y > start.getR1() && y < end.getR1();
        } else return y > end.getR1() && y < start.getR1();

    }

    public Line reverse(){
        Point2D newStart = new Point2D(end.getC1(), end.getR1());
        Point2D newEnd = new Point2D(start.getC1(), start.getR1());

        return new Line(newStart, newEnd);
    }

    public Point2D toVec() {
        // return end - start
        return new Point2D(end.getC1() - start.getC1(), end.getR1() - start.getR1());
    }


    public boolean inside(Point2D p) {
        Point2D v1 = new Point2D(this.end.getC1() - this.start.getC1(), this.end.getR1() - this.start.getR1());
        Point2D n1 = new Point2D((int)-v1.getR1(), (int)v1.getC1());
        Point2D v2 = new Point2D(p.getC1() - this.start.getC1(), p.getR1() - this.start.getR1());
        return n1.getC1() * v2.getC1() + n1.getR1() * v2.getR1() < 0.0;
    }

    public boolean inside2(Point2D p) {
        return (this.end.getR1() - this.start.getR1()) * p.getC1() + (this.start.getC1() - this.end.getC1()) * p.getR1() + (this.end.getC1() * this.start.getR1() - this.start.getC1() * this.end.getR1()) > 0.0;
    }

    public double distance(Point2D p) {
        return Math.abs(((this.end.getR1() - this.start.getR1()) * p.getC1() - (this.start.getC1() - this.end.getC1()) * p.getR1() + this.end.getC1() * this.start.getR1() - this.start.getC1() * this.end.getR1()) / Math.sqrt((this.end.getR1() - this.start.getR1()) * (this.end.getR1() - this.start.getR1()) + (this.start.getC1() - this.end.getC1()) * (this.start.getC1() - this.end.getC1())));
    }

    public Point2D intersection(Point2D v1, Point2D v2) {
        int px = ((v1.getC1() * v2.getR1() - v1.getR1() * v2.getC1()) * (this.start.getC1() - this.end.getC1()) - (this.start.getC1() * this.end.getR1() - this.start.getR1() * this.end.getC1()) * (v1.getC1() - v2.getC1())) / ((v1.getC1() - v2.getC1()) * (this.start.getR1() - this.end.getR1()) - (this.start.getC1() - this.end.getC1()) * (v1.getR1() - v2.getR1()));
        int py = ((v1.getC1() * v2.getR1() - v1.getR1() * v2.getC1()) * (this.start.getR1() - this.end.getR1()) - (this.start.getC1() * this.end.getR1() - this.start.getR1() * this.end.getC1()) * (v1.getR1() - v2.getR1())) / ((v1.getC1() - v2.getC1()) * (this.start.getR1() - this.end.getR1()) - (this.start.getC1() - this.end.getC1()) * (v1.getR1() - v2.getR1()));
        return new Point2D(px, py);
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }
}
