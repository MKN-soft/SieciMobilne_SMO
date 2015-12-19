package com.siecimobilne.smo.narzedzia;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by MSI on 2015-12-19.
 */
public class Wykresy {

    private PrintWriter wykres1;    // Przyjście zgłoszenia do systemu
    private PrintWriter wykres2;    // Zajętość kolejki
    private PrintWriter wykres3;    // Zajętość kanału
    private PrintWriter wykres4;    // Liczba zgłoszeń przybytych do systemu
    private PrintWriter wykres5;    // Liczba zgłoszeń obsłużonych przez system

    public Wykresy() throws FileNotFoundException {
        this.wykres1 = new PrintWriter("wykres1.txt");
        this.wykres2 = new PrintWriter("wykres2.txt");
        this.wykres3 = new PrintWriter("wykres3.txt");
        this.wykres4 = new PrintWriter("wykres4.txt");
        this.wykres5 = new PrintWriter("wykres5.txt");
    }

    public void dodajDoWykresu1(double czas) {
        wykres1.println(czas);
    }

    public void dodajDoWykresu2(int L, double czas) {
        wykres2.println(L + " " + czas);
    }

    public void dodajDoWykresu3(Kanal[] kanaly, double czas) {
        int k = 0;
        for (int i = 0; i < kanaly.length; i++)
            if (!kanaly[i].isWolny())
                k++;

        wykres3.println(k + " " + czas);
    }

    public void dodajDoWykresu4(int i, double czas) {
        wykres4.println(i + " " + czas);
    }

    public void dodajDoWykresu5(int i, double czas) {
        wykres5.println(i + " " + czas);
    }

    public void close() {
        this.wykres1.close();
        this.wykres2.close();
        this.wykres3.close();
        this.wykres4.close();
        this.wykres5.close();
    }


}