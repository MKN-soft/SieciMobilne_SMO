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
    private int l;         // Zapełnienie kolejki

    private Zdarzenie[] tablica; // Tablica zdarzeń

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
        this.l = 0;

        this.kolejka = new Zdarzenie[L];
        this.tablica = new Zdarzenie[this.K + 1];

        this.kanaly = new Kanal[K];

        //tworzenie obiektow symulujacych kanaly
        for (int i = 0; i < K; i++)
            this.kanaly[i] = new Kanal();

        //wstawiamy nieskończoność do tablicy
        for (int i = 0; i < tablica.length; i++)
            tablica[i] = new Zdarzenie(0, Double.POSITIVE_INFINITY);
    }

    /**
     * Dodaje obiekt klasy Zdarzenie do kolejki jesli tylko jest wolne miejsce
     *
     * @param typ  typ zdarzenia
     * @param czas czas zdarzenia
     */
    public void dodajZdarzenie(int typ, double czas) {
        //TODO rzucic wyjatkiem albo po prostu upewnic sie ze typ jest 1 lub 2 i zaden inny.
        for (int i = 0; i < L; i++) {
            if (kolejka[i] == null) {
                kolejka[i] = new Zdarzenie(typ, czas);
            }
        }
    }

    /**
     * Petla WHILE  sumulujaca SMO.
     */
    public void simulate() {

        // Tworzymy pierwsze "zdarzenie"
        Zdarzenie zdarzenie = new Zdarzenie(1, 1/lambda);
        tablica[0] = zdarzenie;

        // Szukamy minimum w tablicy
        Zdarzenie minimum = min(tablica);

        while (t < T) {
            //TODO symulacja

            // Sprawdza czy zdarzenie jest typu 1
            if (minimum.getTyp() == 1) {
                t = minimum.getCzas();
                // Czy kolejka jest pełna
                if (l < L) {
                    // Dodaj zdarzenie do kolejki
                    l++;
                    // Kanał obsługi jest pusty

                }
                else {
                    // Kolejka jest pełna

                }
            }
            // Sprawdza czy zdarzenie jest typu 2
            else {
                t = minimum.getCzas();
                // Czy kolejka jest pusta
                
            }
        }

    }

    private Zdarzenie min(Zdarzenie[] tablica) {
        Zdarzenie wynik = tablica[0];
        for (int i=1; i<tablica.length; i++) {
            if (wynik.getCzas() > tablica[i].getCzas()) {
                wynik = tablica[i];
            }
        }
        return wynik;
    }

}
