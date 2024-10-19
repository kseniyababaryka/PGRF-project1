import objectdata.Point2D;
import objectdata.Polygon;
import rasterdata.RasterBufferedImage;
import rasterops.FilledLineRasterizer;
import rasterops.PolygonRasterizer;
import rasterops.ThickLineRasterizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private PolygonRasterizer polygoner;
    private boolean shiftmode;

    public Canvas(int width, int height){
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new RasterBufferedImage(width, height);
        liner = new FilledLineRasterizer();
        polygon = new Polygon();
        polygoner = new PolygonRasterizer();
        thickLiner = new ThickLineRasterizer();


        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
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

                polygoner.drawPolygon(img, thickLiner, polygon, 0xffff);
                if (polygon.size() > 0) {
                    liner.drawLine(img, polygon.getPoint(polygon.size() - 1).getC1(),
                            polygon.getPoint(polygon.size() - 1).getR1(),
                            c2, r2, 0xff00ff);
                }
                liner.drawLine(img, polygon.getPoint(0).getC1(), polygon.getPoint(0).getR1(), c2, r2, 0xffff);
                panel.repaint();

            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                c1 = e.getX();
                r1 = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                polygon.addPoint(new Point2D(e.getX(), e.getY()));
                polygoner.drawPolygon(img, thickLiner, polygon, 0xffff);
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

    public void clear(){
        img.clear(0x2f2f2f);
    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void start(){
        clear();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
