package rasterops;

/**
 * Trida pro kresleni usecky na platno
 *
 * Pro rasterizace usecky byl pouzit Bresenhamuv algoritmus
 *
 * Princip fungovani:
 * Algoritmus spocita rozdil mezi souradnicemi koncovych bodu (dc, dr)
 * Urci smer pohybu pro x a y (sc, sr)
 * Inicializuje chybu err a osvetluje pixely
 * Upravuje souradnice na zaklade chyby
 *
 * Vyhody:
 * Presne vykresleni primky
 * Celociselne vyposcty
 */



import rasterdata.RasterBufferedImage;

public class FilledLineRasterizer extends LineRasterizer{


    @Override
    /**
     * Kresli usecku mezi dvema zadanymi body na zadanem rastru
     * @param raster Rastrovy obraz na kterem bude usecka vykreslena
     * @param c1 Slopucova souradnice pocatecniho bodu
     * @param r1 Radkova souradnice pocatecniho bodu
     * @param c2 Sloupcova souradnice koncoveho bodu
     * @param r2 Radkova souradnice koncoveho bodu
     * @param color Barva usecky
     */


    public void drawLine(RasterBufferedImage raster, int c1, int r1, int c2, int r2, int color) {

        int dc = Math.abs(c2 - c1); //Vypocita rozdily v souradnicich
        int dr = Math.abs(r2 - r1);

        int sc = c1 < c2 ? 1 : -1; //Urci smer pro pohyby
        int sr = r1 < r2 ? 1 : -1;

        int err = dc - dr; //Inicializuje chybu pro Bresenhamuv algoritmus

        while (true){ //Kresli usecky na zaklade algoritmu

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
