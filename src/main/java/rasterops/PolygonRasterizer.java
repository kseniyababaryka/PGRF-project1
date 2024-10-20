package rasterops;

//Trida pro rasterizace polygonu

import objectdata.Polygon;
import rasterdata.Raster;
import rasterdata.RasterBufferedImage;

public class PolygonRasterizer {
    /**
     * Kresli polygon na zadanem rastru
     *
     * @param raster Rastrovy obraz, na kterem bude polygon vykreslen
     * @param liner Algoritmus pro kresleni usecky
     * @param polygon Polygon, ktery se vykresli
     * @param color Barva polygonu
     */

    public void drawPolygon(RasterBufferedImage raster, LineRasterizer liner, Polygon polygon, int color){

        int listSize = polygon.size(); //Ziska pocet vrcholu polygonu
        for (int i = 0; i < listSize; i++) {
            int nextPoint = (i + 1) % listSize;
            liner.drawLine(raster, polygon.getPoint(i).getC1(), polygon.getPoint(i).getR1(), polygon.getPoint(nextPoint).getC1(), polygon.getPoint(nextPoint).getR1(), color);
        }
    }
}
