package com.siecimobilne.smo;

import java.util.Scanner;

/**
 * Created by MSI on 2015-12-15.
 */
public class SMO {

    private int lambda;    // Srednia liczba przychodzacych zgloszen
    private int mi;        // Srednia liczba zgloszen obsluzonych przez kanal
    private int L;         // Kolejka
    private int T;         // Czas
    private int K;         // Liczba kanałów

    private double t;      // Czas bierzacy

    private int[] tablica; // Tablica zdarzeń

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

        this.tablica = new int[this.K + 1];
    }

    public void run() {

    }

}
