package main.narzedzia;

/**
 * Created by MSI on 2015-12-20.
 */
public class Zdarzenie {

    private int typ;
    private double czas;

    public Zdarzenie(int typ, double czas) {
        this.typ = typ;
        this.czas = czas;
    }

    public Zdarzenie(Zdarzenie minimum) {
        this.typ = minimum.getTyp();
        this.czas = minimum.getCzas();
    }

    public double getCzas() {
        return czas;
    }

    public int getTyp() {
        return typ;
    }
}
