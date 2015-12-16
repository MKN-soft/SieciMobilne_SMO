package com.siecimobilne.smo.narzedzia;

import java.util.Scanner;

/**
 * Klasa symulujaca SMO
 * Created by MSI on 2015-12-15.
 */
public class SMO {

    private int lambda;    // Srednia liczba przychodzacych zgloszen
    private int mi;        // Srednia liczba zgloszen obsluzonych przez kanal
    private int L;         // Max rozmiar kolejki
    private int T;         // Czas
    private int K;         // Liczba kanałów

    private double t;      // Czas biezacy

    private int[] tablica; // Tablica zdarzeń

    private Zdarzenie[] kolejka; //Kolejka zdarzen

    private Kanal[] kanaly;

    public SMO() {
        System.out.println("Podaj lambda: ");
        Scanner odczyt = new Scanner(System.in);
        this.lambda = Integer.parseInt(odczyt.nextLine());

        System.out.println("Podaj mi: ");
        odczyt = new Scanner(System.in);
        this.lambda = Integer.parseInt(odczyt.nextLine());

        this.L = 10;
        this.T = 10;
        this.K = 1;

        this.t = 0;

        this.kolejka = new Zdarzenie[L];
        this.tablica = new int[this.K + 1];

        this.kanaly = new Kanal[K];

        //tworzenie obiektow symulujacych kanaly
        for (int i = 0; i < K; i++) {
            this.kanaly[i] = new Kanal();
        }
    }

    /**
     * Dodaje obiekt klasy Zdarzenie do kolejki jesli tylko jest wolne miejsce
     *
     * @param typ  typ zdarzenia
     * @param czas czas zdarzenia
     */
    public void dodajZdarzenie(int typ, double czas) {
        //TODO rzucic wyjatkiem albo po prostu upewnic sie ze typ jest 1 lub 2 i zaden inny.
        //jezeli zdarzenie jest typu pierwszego
        if (typ == 1) {
            //Sprawdzanie czy jest wolny kanal
            for (Kanal aux : kanaly) {
                if (aux.isWolny()) {
                    //TODO obsluga zdarzenia przez kanal
                    return;
                }
            }
            //jesli nie ma wolnego kanalu
            for (int i = 0; i < L; i++) {
                if (kolejka[i] != null) {
                    kolejka[i] = new Zdarzenie(typ, czas);
                    return;
                }
            }
        }
    }

    /**
     * Petla WHILE  sumulujaca SMO.
     */
    public void simulate() {
        double t = 0;

        while (t < T) {
            t += 1;
            //TODO symulacja
        }

    }

}
