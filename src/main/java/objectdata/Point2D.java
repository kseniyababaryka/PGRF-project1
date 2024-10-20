package objectdata;

/**
 * trida pro reprezentaci 2D bod s koordinatami ve sloupcich c a radcich r
 */


public class Point2D {

    //promenne pro uschovani souradnic bodu (radek r1 a sloupec c1)
    private int r1;
    private int c1;

    /**
     * Konstruktor
     * @param c1 souradnice po ose sloupcu
     * @param r1 souradnice po ose radku
     */

    public Point2D(int c1, int r1){
        this.c1 = c1;
        this.r1 = r1;
    }

    public int getR1(){
        return r1;
    }

    public void setR1(int r1){
        this.r1 = r1;
    }

    public int getC1(){
        return c1;
    }

    public void setC1(int c1){
        this.c1 = c1;
    }


}
