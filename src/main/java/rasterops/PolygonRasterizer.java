package rasterops;

//Trida pro rasterizace polygonu

import objectdata.Elipse;
import objectdata.Polygon;
import objectdata.Rectangle;
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

    public void drawRectangle(RasterBufferedImage raster, ThickLineRasterizer liner, Rectangle rectangle, int color) {
        liner.drawLine(raster, rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getTop(), color);
        liner.drawLine(raster, rectangle.getRight(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), color);
        liner.drawLine(raster, rectangle.getRight(), rectangle.getBottom(), rectangle.getLeft(), rectangle.getBottom(), color);
        liner.drawLine(raster, rectangle.getLeft(), rectangle.getBottom(), rectangle.getLeft(), rectangle.getTop(), color);
    }

    public void drawElipse(RasterBufferedImage raster, Elipse ellipse, int color) {
        int centerX = ellipse.getCenterX();
        int centerY = ellipse.getCenterY();
        int radiusX = ellipse.getRadiusX();
        int radiusY = ellipse.getRadiusY();

        int x = radiusX;
        int y = 0;
        int radiusError = radiusX * radiusX - 2 * radiusX * radiusY;

        while (x >= y) {
            // Рисуем пиксели в восьми симметричных точках
            raster.setColor(centerX + x, centerY + y, color);
            raster.setColor(centerX - x, centerY + y, color);
            raster.setColor(centerX + x, centerY - y, color);
            raster.setColor(centerX - x, centerY - y, color);

            raster.setColor(centerX + y, centerY + x, color);
            raster.setColor(centerX - y, centerY + x, color);
            raster.setColor(centerX + y, centerY - x, color);
            raster.setColor(centerX - y, centerY - x, color);

            y++;
            if (radiusError <= 0) {
                radiusError += 2 * y + 1;
            } else {
                x--;
                radiusError += 2 * (y - x) + 1;
            }
        }
    }
}
