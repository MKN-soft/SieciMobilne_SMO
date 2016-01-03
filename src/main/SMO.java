package main;

import main.narzedzia.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by MSI on 2015-12-20.
 */
public class SMO {

    private double lambda;
    private double mi;
    private int L;
    private int T;
    private int K;

    private double t;

    private TablicaZdarzen tablicaZdarzen;
    private Kolejka kolejka;
    private Kanaly kanaly;

    private Zdarzenie minimum;

    private Wykresy wykresy;

    private RozkladPoissona rozkladPoissona;

    public int liczba_zgloszen_przybylych = 0;
    public int liczba_zgloszen_obsluzonych = 0;

    public SMO() {
        Scanner odczyt = new Scanner(System.in);
        System.out.println("Podaj lambda: ");
        this.lambda = odczyt.nextDouble();

        System.out.println("Podaj mi: ");
        this.mi = odczyt.nextDouble();

        this.L = 10;
        this.T = 10;
        this.K = 1;

        this.t = 0.0;

        try {
            this.wykresy = new Wykresy();
            this.wykresy.dodajDoWykresu(this.lambda, this.mi, this.K);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Błąd otwarcia pliku!");
        }

        this.kanaly = new Kanaly(this.K);
        this.kolejka = new Kolejka(this.L);
        this.tablicaZdarzen = new TablicaZdarzen(this.K);
        this.rozkladPoissona = new RozkladPoissona();
    }

    public void simulate() {
        // Tworzymy pierwsze zdarzenie
        this.tablicaZdarzen.dodajDoTypuI(new Zdarzenie(1, round(1.0 / this.lambda, 2)));
        this.wykresy.dodajDoWykresu1(this.tablicaZdarzen.getTypI().getCzas());
        this.liczba_zgloszen_przybylych++;
        this.wykresy.dodajDoWykresu4(1, this.tablicaZdarzen.getTypI().getCzas());

        while (t < T) {
            // Szukamy minimum w tablicy
            this.minimum = this.tablicaZdarzen.minimum();

            // Sprawdza czy zdarzenie jest typu I
            if (this.minimum.getTyp() == 1) {
                this.t = this.minimum.getCzas();

                // Czy kolejka jest pełna
                if (this.kolejka.getCount() < L) {
                    // Dodaj zdarzenie do kolejki
                    this.kolejka.dodaj(this.minimum);

                    // Kanał obsługi jest pusty
                    if(this.kanaly.czyPustyKanal()) {
                        // Przeniesienie zdarzenia z kolejki do kanalu obslugi (FIFO)
                        Zdarzenie temp = this.kolejka.usun();

                        // Okreslenie momentu konca obslugi zdarzenia przez kanal obslugi
                        double koniecObslugi = round(this.t + (1.0 / this.mi), 2);
                        //double deltaMi = rozkladPoissona.mi(this.mi);
                        //double koniecObslugi = round(this.t + deltaMi, 2);

                        // Dodaj do kanału
                        int id = this.kanaly.dodaj(temp, koniecObslugi);

                        // Wstawienie zdarzenia typu II do tablicy zdarzen
                        this.tablicaZdarzen.dodajDoTypuII(id, koniecObslugi);

                        // Ustalenie momentu przyjscia nastepnego zdarzenia
                        ustalenieMomentuPrzyjscia();
                    }
                    else {
                        // Wszystkie kanały są zajęte, zostawiamy zgłoszenie w kolejce

                        // Dodanie do wykresu 6 - Liczba odrzuconych zdarzeń
                        this.wykresy.dodajDoWykresu6(1, this.tablicaZdarzen.getTypI().getCzas());

                        // Ustalenie momentu przyjscia nastepnego zdarzenia
                        ustalenieMomentuPrzyjscia();
                    }
                }
                else {
                    // Kolejka jest pełna

                    // Dodanie do wykresu 6 - Liczba odrzuconych zdarzeń
                    this.wykresy.dodajDoWykresu6(1, this.tablicaZdarzen.getTypI().getCzas());

                    // Ustalenie momentu przyjscia nastepnego zdarzenia
                    ustalenieMomentuPrzyjscia();
                }
            }
            // Zdarzenie jest typu II
            else {
                this.t = this.minimum.getCzas();

                // Zwróc id kanału
                int id = this.kanaly.getIdKanalu(this.minimum);

                // Zwolnij kanał
                this.kanaly.zwolnij(id);

                // Dodanie do wykresu 5 - Zgłoszenia obsłużone
                this.wykresy.dodajDoWykresu5(1, this.tablicaZdarzen.getTypII().getCzas());

                // Czy kolejka jest pusta
                if (this.kolejka.getCount() == 0) {
                    // Wstaw INFINITY do tablicy zdarzeń typu II
                    this.tablicaZdarzen.dodajDoTypuIINieskonczonosc(id);

                    // Ustalenie momentu przyjścia następnego zdarzenia
                    ustalenieMomentuPrzyjscia();
                }
                else {
                    // Przeniesienie zdarzenia z kolejki do kanalu obslugi (FIFO)
                    Zdarzenie temp = this.kolejka.usun();

                    // Określenie momentu końca obsługi zdarzenia przez kanał obsługi
                    double koniecObslugi = round(this.t + (1.0 / this.mi), 2);
                    //double deltaMi = rozkladPoissona.mi(this.mi);
                    //double koniecObslugi = round(this.t + deltaMi, 2);

                    this.kanaly.dodaj(id, temp, koniecObslugi);

                    // Wstawienie zdarzenia typu II do tablicy zdarzeń
                    this.tablicaZdarzen.dodajDoTypuII(id, koniecObslugi);

                    // Ustalenie momentu przyjścia następnego zdarzenia
                    ustalenieMomentuPrzyjscia();
                }
            }
        }

        this.sum();
        this.close();
    }

    private void ustalenieMomentuPrzyjscia() {
        this.tablicaZdarzen.dodajDoTypuI(new Zdarzenie(1, round((this.tablicaZdarzen.getTypI().getCzas() + (1.0) / this.lambda), 2)));
        //double deltaLambda = rozkladPoissona.lambda(this.lambda);
        //this.tablicaZdarzen.dodajDoTypuI(new Zdarzenie(1, round( (this.tablicaZdarzen.getTypI().getCzas() + deltaLambda), 2 ) ));

        this.rysujWykresy();

        this.liczba_zgloszen_przybylych++;
    }

    private void rysujWykresy() {
        // Dodanie do wykresu 1
        this.wykresy.dodajDoWykresu1(this.tablicaZdarzen.getTypI().getCzas());

        // Dodanie do wykresu 2
        this.wykresy.dodajDoWykresu2(this.kolejka.getCount(), this.tablicaZdarzen.getTypI().getCzas());

        // Dodanie do wykresu 3
        this.wykresy.dodajDoWykresu3(this.kanaly.getCount(), this.tablicaZdarzen.getTypI().getCzas());

        // Dodanie do wykresu 4 - zgłoszenia przybyłe
        this.wykresy.dodajDoWykresu4(1, this.tablicaZdarzen.getTypI().getCzas());
    }

    private void close() {
        this.wykresy.close();
    }

    private void sum() {
        this.wykresy.sum();
    }

    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

}