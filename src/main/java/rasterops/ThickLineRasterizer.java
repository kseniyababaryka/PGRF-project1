package rasterops;

//Trida pro kresleni silne cary

import rasterdata.RasterBufferedImage;

public class ThickLineRasterizer extends LineRasterizer{

    @Override
    /**
     * Kresli tucnou caru na danem rastru
     *
     * @param raster Rastrovy obraz, na kterem bude cara vykreslena
     * @param c1 Sloupcova souradnice pocatecniho bodu
     * @param r1 Radkova souradnice pocatecniho bodu
     * @param c2 Sloupcova souradnice koncoveho bodu
     * @param r2 Radkova souradnice koncoveho bodu
     * @param color Barva cary
     */

    public void drawLine(RasterBufferedImage raster, int c1, int r1, int c2, int r2, int color) {
        int thickness = 3; //Nastaveni tloustky cary

        double vc = c2 - c1; //Rozdil v souradnicich
        double vr = r2 - r1;

        double length = Math.sqrt(vc * vc + vr * vr); //Delka usecky

//        double nc = -vr / length; Vypocitani normaloveho vektoru k usecce pro zakladni ukol
//        double nr = -vc / length;

        double nc = -vr / length * thickness / 2; //Vypocitani normaloveho vektoru k usecce pro implementace bonusoveho ukolu
        double nr = -vc / length * thickness / 2;

        for (int i = -thickness/ 2; i <= thickness / 2 ; i++) {
            int offsetX = (int) (i * nc);
            int offsetY = (int) (i * nr);

            new FilledLineRasterizer().drawLine(raster, c1 + offsetX, r1 + offsetY, c2 + offsetX, r2 + offsetY, color);
        }

        drawCircle(raster, c1, r1, thickness / 2, color); //Vykresleni zaoblenych koncu usecky pro reseni bonusoveho ukolu
        drawCircle(raster, c2, r2, thickness / 2, color);
    }

    /**
     * Vykresli kruznice na zadanem rastru
     *
     * @param raster Rastrovy obraz, na kterem se kruznice vykresli
     * @param cx Sloupcova souradnice stredu kruznice
     * @param cy Radkova souradnice stredu kruznice
     * @param r Polomer kruznice
     * @param color Barva kruznice
     */
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
