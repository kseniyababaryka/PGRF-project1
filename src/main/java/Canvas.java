import objectdata.*;
import objectdata.Polygon;
import objectdata.Rectangle;
import objectops.SutherlandHodgman;
import rasterdata.RasterBufferedImage;
import rasterops.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */


public class Canvas {

    private JFrame frame;
    private JPanel panel;
    private RasterBufferedImage img;

    private int c1, r1, c2, r2, x, y;

    private FilledLineRasterizer liner;
    private ThickLineRasterizer thickLiner;
    private Polygon polygon;
    private Polygon cuttingPolygon;
    private Polygon clippedPolygon;
    private PolygonRasterizer polygoner;
    private boolean shiftmode;
    private boolean linerMode = true;
    private boolean rectangleMode = false;
    private boolean ellipseMode = false;
    private boolean seedfillMode = false;
    private ScanLine scanLine;
    private List<Point2D> points;
    private List<Point2D> clipPoints;




    private SeedFill seedFill;
    private SutherlandHodgman cutter;

    public Canvas(int width, int height){
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = frame.getContentPane().getSize();
                img = new RasterBufferedImage(newSize.width, newSize.height);
                clear();
                polygoner.drawPolygon(img, thickLiner, cuttingPolygon, 0xff00ff);
                panel.repaint();
            }
        });

        img = new RasterBufferedImage(width, height);
        liner = new FilledLineRasterizer();
        polygon = new Polygon();
        cuttingPolygon = new Polygon();
        polygoner = new PolygonRasterizer();
        thickLiner = new ThickLineRasterizer();
        seedFill = new SeedFill();
        cutter = new SutherlandHodgman();
        scanLine = new ScanLine();
        points = new ArrayList();
        clipPoints = new ArrayList();




        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
                g.setFont(new Font("Sans-serif", Font.PLAIN, 12));
                g.setColor(Color.WHITE);
                g.drawString("L - for line drawing, P - for polygon drawing, R - for rectangle drawing, E - for ellipse drawing", 20, 30);
                g.drawString("Shift pressed - for vertical, horizontal and diagonal lines", 20, 60);
                g.drawString("C - for clear everything, S - for seedFill mode, RMB - for adding points to cutting polygon", 20, 90);

            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);



        frame.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();

                c2 = e.getX();
                r2 = e.getY();

                if (shiftmode){
                    int dc = Math.abs(c2 - c1);
                    int dr = Math.abs(r2 - r1);

                    if (dc > dr){
                        if (dr < 0.5 * dc){
                            r2 = r1;
                        } else {
                            if ((c2 - c1) * (r2 - r1) > 0){
                                r2 = r1 + dc;
                            } else r2 = r1 - dc;
                        }
                    } else {
                        if (dc < 0.5 * dr){
                            c2 = c1;
                        } else {
                            if ((c2 - c1) * (r2 - r1) > 0){
                                c2 = c1 + dr;
                            } else c2 = c1 - dr;
                        }
                    }
                }

                if (rectangleMode){
                        Rectangle rect = new Rectangle(c1, r1, c2, r2);
                        polygoner.drawRectangle(img,thickLiner, rect, 0xffff);

                } else if (linerMode){
                    liner.drawLine(img, c1, r1, c2, r2, 0xffff);
                } else if (!ellipseMode){
                    polygoner.drawPolygon(img, thickLiner, polygon, 0xffff);

                    if (polygon.size() > 0) {
                        liner.drawLine(img, polygon.getPoint(polygon.size() - 1).getC1(),
                                polygon.getPoint(polygon.size() - 1).getR1(),
                                c2, r2, 0xff00ff);
                    }
                    liner.drawLine(img, polygon.getPoint(0).getC1(), polygon.getPoint(0).getR1(), c2, r2, 0xffff);
                }

                polygoner.drawPolygon(img, thickLiner, cuttingPolygon, 0xff00ff);
                panel.repaint();

            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                c1 = e.getX();
                r1 = e.getY();
                Point2D newPoint = new Point2D(c1, r1);

                if (e.getButton() == 1) {
                    if(seedfillMode){
                        seedFill.fill(img,e.getX(), e.getY(), 0x2f2f2f, 0x00ff00, 0xffff);
                        seedfillMode = false;

                    } else polygon.addPoint(newPoint);
                }

                if (e.getButton() == 3) {
                    List<Point2D> testPolygon = new ArrayList<>(cuttingPolygon.getPoints());
                    testPolygon.add(newPoint);

                    if (isConvexPolygon(testPolygon)) {
                        cuttingPolygon.addPoint(newPoint);
                        System.out.println("Point added to convex clipping polygon: (" + c1 + ", " + r1 + ")");
                    } else {
                        System.out.println("Point rejected, would make polygon non-convex.");
                    }
                    clear();
                    updateClippingAndFill();
                }


                panel.repaint();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x = e.getX();
                y = e.getY();



                if(ellipseMode){
                    if (polygon.size() > 1){
                        Elipse ellipse = new Elipse(polygon.getPoint(0).getC1(),polygon.getPoint(0).getR1(), polygon.getPoint(1).getC1(), polygon.getPoint(1).getR1());
                        polygoner.drawElipse(img, ellipse, 0xff0000);
                        polygon = new Polygon();
                    }
                }


                panel.repaint();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                    shiftmode = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_C){
                    clear();
                    panel.repaint();
                    polygon = new Polygon();
                    cuttingPolygon = new Polygon();
                    cuttingPolygon.addPoint(new Point2D(10, 200));
                    cuttingPolygon.addPoint(new Point2D(100, 600));
                    cuttingPolygon.addPoint(new Point2D(500, 500));
                    polygoner.drawPolygon(img, thickLiner, cuttingPolygon, 0xff00ff);
                }

                if (e.getKeyCode() == KeyEvent.VK_L){
                    linerMode = true;
                    rectangleMode = false;
                    ellipseMode = false;
                    seedfillMode = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_P){
                    linerMode = false;
                    ellipseMode = false;
                    rectangleMode = false;
                    seedfillMode = false;
                    clear();
                }

                if(e.getKeyCode() == KeyEvent.VK_R){
                    linerMode = false;
                    ellipseMode = false;
                    rectangleMode = true;
                    seedfillMode = false;
                    clear();
                }

                if(e.getKeyCode() == KeyEvent.VK_E){
                    ellipseMode = true;
                    linerMode = false;
                    rectangleMode = false;
                    seedfillMode = false;
                    polygon = new Polygon();
                    clear();
                }

                if(e.getKeyCode() == KeyEvent.VK_S){
                    ellipseMode = false;
                    linerMode = false;
                    rectangleMode = false;
                    seedfillMode = true;
                }


            }


            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT){
                    shiftmode = false;
                }
            }
        });


    }
    private boolean isConvexPolygon(List<Point2D> points) {
        if (points.size() < 4) return true;

        boolean sign = false;
        for (int i = 0; i < points.size(); i++) {
            int dx1 = points.get((i + 2) % points.size()).getC1() - points.get((i + 1) % points.size()).getC1();
            int dy1 = points.get((i + 2) % points.size()).getR1() - points.get((i + 1) % points.size()).getR1();
            int dx2 = points.get(i).getC1() - points.get((i + 1) % points.size()).getC1();
            int dy2 = points.get(i).getR1() - points.get((i + 1) % points.size()).getR1();
            int crossProduct = dx1 * dy2 - dy1 * dx2;

            if (i == 0) {
                sign = crossProduct > 0;
            } else if (sign != (crossProduct > 0)) {
                return false;
            }
        }
        return true;
    }
    private void updateClippingAndFill() {
        polygoner.drawPolygon(img, new ThickLineRasterizer(), cuttingPolygon, 0xff00ff);


        List<Point2D> clippedPoints = cutter.clip(polygon.getPoints(), cuttingPolygon.getPoints());


        clippedPolygon = new Polygon();
        clippedPoints.forEach(clippedPolygon::addPoint);


        if (clippedPolygon.size() > 2) {
            System.out.println("Clipping successful with points: " + clippedPoints);


            scanLine.draw(img, clippedPolygon, 0xff0000); // Fill with red
        } else {
            System.out.println("Clipping failed");
        }

        polygoner.drawPolygon(img, new ThickLineRasterizer(), polygon, 0xffff);
    }

    public void clear(){
        img.clear(0x2f2f2f);
    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void start(){
        clear();

        cuttingPolygon.addPoint(new Point2D(100, 100));
        cuttingPolygon.addPoint(new Point2D(100, 600));
        cuttingPolygon.addPoint(new Point2D(500, 500));


        polygoner.drawPolygon(img, thickLiner, cuttingPolygon, 0xff00ff);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
