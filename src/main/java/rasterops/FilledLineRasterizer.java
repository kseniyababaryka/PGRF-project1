package rasterops;

import rasterdata.RasterBufferedImage;

public class FilledLineRasterizer extends LineRasterizer{


    @Override
    public void drawLine(RasterBufferedImage raster, int c1, int r1, int c2, int r2, int color) {

        int dc = Math.abs(c2 - c1);
        int dr = Math.abs(r2 - r1);

        int sc = c1 < c2 ? 1 : -1;
        int sr = r1 < r2 ? 1 : -1;

        int err = dc - dr;

        while (true){

            raster.setColor(c1,r1,color);

            if (c1 == c2 && r1 == r2) break;

            int err2 = 2 * err;

            if (err2 > - dr){
                err -= dr;
                c1 += sc;
            }

            if (err2 < dc){
                err += dc;
                r1 += sr;
            }
        }
    }
}
