package rasterops;

// Abstraktni trida pro rasterizace usecky

import rasterdata.RasterBufferedImage;

public abstract class LineRasterizer {

    public abstract void drawLine(RasterBufferedImage raster, int c1, int r1, int c2, int r2, int color);

}
