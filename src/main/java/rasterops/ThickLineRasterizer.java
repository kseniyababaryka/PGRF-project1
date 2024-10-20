package rasterops;

import rasterdata.RasterBufferedImage;

public class ThickLineRasterizer extends LineRasterizer{

    @Override
    public void drawLine(RasterBufferedImage raster, int c1, int r1, int c2, int r2, int color) {
        int thickness = 3;

        double vc = c2 - c1;
        double vr = r2 - r1;

        double length = Math.sqrt(vc * vc + vr * vr);

//        double nc = -vr / length;
//        double nr = -vc / length;

        double nc = -vr / length * thickness / 2;
        double nr = -vc / length * thickness / 2;

        for (int i = -thickness/ 2; i <= thickness / 2 ; i++) {
            int offsetX = (int) (i * nc);
            int offsetY = (int) (i * nr);

            new FilledLineRasterizer().drawLine(raster, c1 + offsetX, r1 + offsetY, c2 + offsetX, r2 + offsetY, color);
        }

        drawCircle(raster, c1, r1, thickness / 2, color);
        drawCircle(raster, c2, r2, thickness / 2, color);
    }

    public void drawCircle(RasterBufferedImage raster, int cx, int cy, int r, int color){
        int x = r;
        int y = 0;
        int error = 0;

        while(x >= y) {
            raster.setColor(cx + x, cy + y, color);
            raster.setColor(cx + y, cy + x, color);
            raster.setColor(cx - y, cy + x, color);
            raster.setColor(cx - x, cy + y, color);
            raster.setColor(cx - x, cy - y, color);
            raster.setColor(cx - y, cy - x, color);
            raster.setColor(cx + y, cy - x, color);
            raster.setColor(cx + x, cy - y, color);

            y++;
            error += 1 + 2 * y;
            if (2 * (error - x) + 1 > 0){
                x--;
                error += 1 - 2 * x;
            }
        }
    }
}
