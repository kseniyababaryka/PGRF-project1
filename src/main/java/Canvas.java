import rasterdata.RasterBufferedImage;
import rasterops.FilledLineRasterizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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

    private int c1, r1, c2, r2;

    private FilledLineRasterizer liner;

    public Canvas(int width, int height){
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new RasterBufferedImage(width, height);
        liner = new FilledLineRasterizer();


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

        frame.addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e){
                c1 = e.getX();
                r1 = e.getY();
            };

        });

        frame.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();
                liner.drawLine(img, c1, r1, e.getX(), e.getY(), 0xFF0000);
                panel.repaint();

            }
        });


    }

    public void clear(){
        img.clear(0xf2f2f2);
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
