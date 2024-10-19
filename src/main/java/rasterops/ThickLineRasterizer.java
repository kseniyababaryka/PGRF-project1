package rasterops;

import rasterdata.RasterBufferedImage;

public class ThickLineRasterizer extends LineRasterizer{

    @Override
    public void drawLine(RasterBufferedImage raster, int c1, int r1, int c2, int r2, int color) {
        int thickness = 3;

        double vc = c2 - c1;
        double vr = r2 - r1;

        double length = Math.sqrt(vc * vc + vr * vr);

        double nc = -vr / length;
        double nr = -vc / length;

        for (int i = -thickness/ 2; i <= thickness ; i++) {
            int offsetX = (int) (i * nc);
            int offsetY = (int) (i * nr);

            new FilledLineRasterizer().drawLine(raster, c1 + offsetX, r1 + offsetY, c2 + offsetX, r2 + offsetY, color);
        }
    }
}
