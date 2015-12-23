package main.narzedzia;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 2015-12-19.
 */
public class Wykresy {

    private PrintWriter wykres;
    private PrintWriter wykres1;    // Przyjście zgłoszenia do systemu
    private PrintWriter wykres2;    // Zajętość kolejki
    private PrintWriter wykres3;    // Zajętość kanału
    private PrintWriter wykres4;    // Liczba zgłoszeń przybytych do systemu
    private PrintWriter wykres5;    // Liczba zgłoszeń obsłużonych przez system

    private List<Para> listaWykres4;
    private List<Para> listaWykres5;

    public Wykresy() throws FileNotFoundException {
        this.wykres = new PrintWriter("wykres.txt");
        this.wykres1 = new PrintWriter("wykres1.txt");
        this.wykres2 = new PrintWriter("wykres2.txt");
        this.wykres3 = new PrintWriter("wykres3.txt");
        this.wykres4 = new PrintWriter("wykres4.txt");
        this.wykres5 = new PrintWriter("wykres5.txt");

        this.listaWykres4 = new ArrayList<>();
        this.listaWykres5 = new ArrayList<>();
    }

    public void sum() {
        double j = 0.0;
        int suma = 0;

        for (int i = 0; i < this.listaWykres4.size(); i++) {
            if (j == this.listaWykres4.get(i).v) {
                suma += this.listaWykres4.get(i).i;
            }
            else {
                this.wykres4.println(j + " " + suma);
                j++;
                suma = 0;
            }
        }

        j = 0;
        suma = 0;

        for (int i = 0; i < this.listaWykres5.size(); i++) {
            if (j == this.listaWykres5.get(i).v) {
                suma += this.listaWykres4.get(i).i;
            }
            else {
                this.wykres5.println(j + " " + suma);
                j++;
                suma = 0;
            }
        }
    }

    public void close() {
        this.wykres.close();
        this.wykres1.close();
        this.wykres2.close();
        this.wykres3.close();
        this.wykres4.close();
        this.wykres5.close();
    }

    public void dodajDoWykresu(double lambda, double mi, int K) {
        wykres.println("lambda: " + lambda);
        wykres.println("mi: " + mi);
        wykres.println("liczba kanałów: " + K);
    }

    public void dodajDoWykresu1(double czas) {
        wykres1.println(czas + "\t" + "0");
    }

    public void dodajDoWykresu2(int count, double t) {
        wykres2.println(t + "\t" + count);
    }

    public void dodajDoWykresu3(int count, double t) {
        wykres3.println(t + "\t" + count);
    }

    public void dodajDoWykresu4(int liczba_zgloszen_przybylych, double v) {
        wykres4.println(v);
        //this.listaWykres4.add(new Para(liczba_zgloszen_przybylych, Math.floor(v)));
    }

    public void dodajDoWykresu5(int liczba_zgloszen_obsluzonych, double v) {
        wykres5.println(v);
        //this.listaWykres5.add(new Para(liczba_zgloszen_obsluzonych, Math.floor(v)));
    }

    class Para {
        private int i;
        private double v;

        public Para(int i, double v) {
            this.i = i;
            this.v = v;
        }
    }
}