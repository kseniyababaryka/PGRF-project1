package rasterops;

import objectdata.Polygon;
import rasterdata.Raster;
import rasterdata.RasterBufferedImage;

public class PolygonRasterizer {

    public void drawPolygon(RasterBufferedImage raster, LineRasterizer liner, Polygon polygon, int color){

        int listSize = polygon.size();
        for (int i = 0; i < listSize; i++) {
            int nextPoint = (i + 1) % listSize;
            liner.drawLine(raster, polygon.getPoint(i).getC1(), polygon.getPoint(i).getR1(), polygon.getPoint(nextPoint).getC1(), polygon.getPoint(nextPoint).getR1(), color);
        }
    }
}
