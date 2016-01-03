package main.narzedzia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 2015-12-19.
 */
public class Wykresy {

    private int IdStacji;
    private String nazwaStacji;

    private PrintWriter wykres;
    private PrintWriter wykres1;    // Przyjście zgłoszenia do systemu
    private PrintWriter wykres2;    // Zajętość kolejki
    private PrintWriter wykres3;    // Zajętość kanału
    private PrintWriter wykres4;    // Liczba zgłoszeń przybytych do systemu
    private PrintWriter wykres5;    // Liczba zgłoszeń obsłużonych przez system
    private PrintWriter wykres6;    // Liczba odrzuconych zgłoszeń

    private List<Para> listaWykres4;
    private List<Para> listaWykres5;
    private List<Para> listaWykres6;

    public Wykresy(int IdStacji) throws FileNotFoundException {
        this.IdStacji = IdStacji;
        this.nazwaStacji = "stacja" + this.IdStacji;


        File dir = new File(this.nazwaStacji);
        dir.mkdir();

        this.wykres = new PrintWriter(this.nazwaStacji + "/wykres.txt");
        this.wykres1 = new PrintWriter(this.nazwaStacji + "/wykres1.txt");
        this.wykres2 = new PrintWriter(this.nazwaStacji + "/wykres2.txt");
        this.wykres3 = new PrintWriter(this.nazwaStacji + "/wykres3.txt");
        this.wykres4 = new PrintWriter(this.nazwaStacji + "/wykres4.txt");
        this.wykres5 = new PrintWriter(this.nazwaStacji + "/wykres5.txt");
        this.wykres6 = new PrintWriter(this.nazwaStacji + "/wykres6.txt");

        this.listaWykres4 = new ArrayList<>();
        this.listaWykres5 = new ArrayList<>();
        this.listaWykres6 = new ArrayList<>();
    }

    public void sum() {
        countSum(this.listaWykres4, this.wykres4);
        countSum(this.listaWykres5, this.wykres5);
        countSum(this.listaWykres6, this.wykres6);
    }

    public void countSum(List<Para> list, PrintWriter wykres) {
        double j = 0.0;
        int suma = 0;

        for (int i = 0; i < list.size(); i++) {
            if (j == Math.floor(list.get(i).v)) {
                suma += list.get(i).i;
            }
            else {
                wykres.println(j + " " + suma);
                j++;
                suma = 0;
            }
        }
        wykres.println(j + " " + suma);
    }

    public void close() {
        this.wykres.close();
        this.wykres1.close();
        this.wykres2.close();
        this.wykres3.close();
        this.wykres4.close();
        this.wykres5.close();
        this.wykres6.close();
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
        this.listaWykres4.add(new Para(liczba_zgloszen_przybylych, v));
    }

    public void dodajDoWykresu5(int liczba_zgloszen_obsluzonych, double v) {
        this.listaWykres5.add(new Para(liczba_zgloszen_obsluzonych, v));
    }

    public void dodajDoWykresu6(int i, double czas) {
        this.listaWykres6.add(new Para(i, czas));
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