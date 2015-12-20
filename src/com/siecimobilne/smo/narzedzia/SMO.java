package com.siecimobilne.smo.narzedzia;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

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

    private PrintWriter zapis;//do zapisu czasów

    private Zdarzenie minimum;//najmniejsze zdarzenie w tablicy zdarzen

    private Wykresy wykresy; // Pobiera dane do rysowania wykresów

    private int liczba_zgloszen_przybylych = 0;
    private int liczba_zgloszen_obsluzonych = 0;

    private Random generator;

    public SMO() {
        Scanner odczyt = new Scanner(System.in);
        System.out.println("Podaj lambda: ");
        this.lambda = Integer.parseInt(odczyt.nextLine());

        System.out.println("Podaj mi: ");
        this.mi = Integer.parseInt(odczyt.nextLine());

        this.L = 10;
        this.T = 10;
        this.K = 2;

        this.t = 0.0;
        this.l = 0;

        this.kolejka = new Zdarzenie[L];
        this.tablica = new Zdarzenie[this.K + 1];

        this.kanaly = new Kanal[K];

        try {
            this.wykresy = new Wykresy();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Błąd otwarcia pliku!");
        }

        try{
            this.zapis = new PrintWriter("out.txt");
        }
        catch(Exception e){
            System.out.println("Błąd otwarcia pliku!");
        }


        //tworzenie obiektow symulujacych kanaly
        for (int i = 0; i < K; i++)
            this.kanaly[i] = new Kanal(i+1);

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
    public void dodajZdarzenieDoKolejki(int typ, double czas) {
        //TODO rzucic wyjatkiem albo po prostu upewnic sie ze typ jest 1 lub 2 i zaden inny.
        for (int i = 0; i < L; i++) {
            if (kolejka[i] == null) {
                kolejka[i] = new Zdarzenie(typ, czas);
                // poczebny break, inaczej zapełni kolejkę klonami zdarzenia...
                break;
            }
        }
    }

    public Zdarzenie usunZdarzenieZKolejki() {
        for (int i = 0; i < L; i++) {
            if (kolejka[i] != null) {
                Zdarzenie temp = new Zdarzenie(kolejka[i].getTyp(),kolejka[i].getCzas());
                kolejka[i] = null;
                //przzesuniecie wszystkich zdarzen o jeden w lewo
                for (int j=0; j<L-1;j++){
                    kolejka[j]=kolejka[j+1];
                }
                return temp;
            }
        }
        return null;
    }

    /**
     * Petla WHILE  sumulujaca SMO.
     */
    public void simulate() {
        //generator do generowania liczb losowych
        this.generator = new Random();
        // Tworzymy pierwsze "zdarzenie"
        Zdarzenie zdarzenie = new Zdarzenie(1, 1.0/this.lambda);
        this.liczba_zgloszen_przybylych++;
        tablica[0] = zdarzenie;
        // Dodanie do wykresu 1
        wykresy.dodajDoWykresu1(1.0/this.lambda);

        // Szukamy minimum w tablicy
        this.minimum = min(tablica);
        int licznik = 0;

        while (t < T) {
            this.minimum = min(tablica);
            System.out.println(tablica[0].getCzas() + "\t" + tablica[1].getCzas());
            //System.out.println("Przejscie: "+ licznik);
            //TODO symulacja

            // Sprawdza czy zdarzenie jest typu 1
            if (this.minimum.getTyp() == 1) {
                t = this.minimum.getCzas();
                zapiszCzas(t,1);
                // Czy kolejka jest pełna
                if (l < L) {
                    // Dodaj zdarzenie do kolejki
                    dodajZdarzenieDoKolejki(this.minimum.getTyp(), this.minimum.getCzas());
                    l++;
                    // Kanał obsługi jest pusty
                    if (czyPustyKanal()) {
                        // Któryś kanał jest pusty

                        // Przeniesienie zdarzenia z kolejki do kanalu obslugi (FIFO)
                        Zdarzenie temp = usunZdarzenieZKolejki();
                        dodajZdarzenieDoKanalu(temp.getTyp(), temp.getCzas());
                        l--;

                        // Okreslenie momentu konca obslugi zdarzenia przez kanal obslugi

                        // Wstawienie zdarzenia typu II do tablicy zdarzen
                        this.tablica[1] = new Zdarzenie(2,temp.getCzas() + 1.0/this.mi);

                        // TODO Ustalenie momentu przyjscia nastepnego zdarzenia
                        ustalenieMomentuPrzyjscia();
                    }
                    else {
                        // Wszystkie są zajęte, olewamy zgłoszenie

                        // TODO Ustalenie momentu przyjscia nastepnego zdarzenia
                        ustalenieMomentuPrzyjscia();
                    }
                }
                else {
                    // Kolejka jest pełna

                    // TODO Ustalenie momentu przyjscia nastepnego zdarzenia
                    ustalenieMomentuPrzyjscia();
                }
            }
            // Sprawdza czy zdarzenie jest typu 2
            else{
                t = this.minimum.getCzas();
                zapiszCzas(t,2);
                // Czy kolejka jest pusta
                if (l == 0 && czyPustyKanal()) {
                    // Kolejka jest pusta

                    // Wstaw "nieskończoność" do tablicy zdarzen typu II
                    tablica[1] = new Zdarzenie(2, Double.POSITIVE_INFINITY);
                }
                else {
                    // Kolejka NIE jest pusta

                    // Przeniesienie zdarzenia z kolejki do kanalu obslugi (FIFO)
                    Zdarzenie temp = usunZdarzenieZKolejki();
                    dodajZdarzenieDoKanalu(temp.getTyp(), temp.getCzas());

                    l--;

                    // Okreslenie momentu konca obslugi zdarzenia przez kanal obslugi

                    // Wstawienie zdarzenia typu II do tablicy zdarzen
                    tablica[1] = new Zdarzenie(2,tablica[0].getCzas() + (1.0/mi));
                    // TODO Ustalenie momentu przyjscia nastepnego zdarzenia
                    ustalenieMomentuPrzyjscia();
                }
            }
            licznik++;
        }
        this.zapis.close();
        this.wykresy.close();
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

    private boolean czyPustyKanal() {
        for (int i = 0; i < kanaly.length; i++) {
            if (kanaly[i].isWolny()) {
                return true;
            }
        }
        return false;
    }

    public void dodajZdarzenieDoKanalu(int typ, double czas) {
        //TODO rzucic wyjatkiem albo po prostu upewnic sie ze typ jest 1 lub 2 i zaden inny.
        //jeżeli t jest wieksze od czasu w kanale to go oprozniamy zeby bylo miejsce na inne zdarzenia
        for (int i = 0; i < kanaly.length; i++) {
            Zdarzenie tempZdarz = min(this.tablica);
            double tempMin = tempZdarz.getCzas();
            if (!kanaly[i].isWolny() && (kanaly[i].getZdarzenie().getCzas() < tempMin)) {
                kanaly[i].usunZdarzenie();
            }
        }

        for (int i = 0; i < kanaly.length; i++) {
            if (kanaly[i].isWolny()) {
                kanaly[i].dodajZdarzenie(typ,czas);
                this.liczba_zgloszen_obsluzonych++;
                return;
            }
        }
    }

    //zapisuje czas na osi
    public void zapiszCzas(double czas, int typ){
        this.zapis.println(czas + " " + typ);
    }

    public void ustalenieMomentuPrzyjscia(){
        //Losowanie lambda i mi zgodnie z rozkladem Poissona
        double losowaLiczba = (this.generator.nextInt(99)+1)/100.0;//losowa liczba z przedziału (0,1)
        double deltaLambda = (-1.0/this.lambda * Math.log(losowaLiczba));
        losowaLiczba = (this.generator.nextInt(100)+1)/100.0;
        double deltaMi = (-1.0/this.mi * Math.log(losowaLiczba));

        //Wstawienie zcasu przybycia kolejnego zdarzenia do tablicy
        this.tablica[0] = new Zdarzenie(1,this.tablica[0].getCzas() + deltaLambda);
        //System.out.println("losowe lambda: " + deltaLambda);
        //System.out.println("losowe mi: " + deltaMi);
        // Dodanie do wykresu 1
        wykresy.dodajDoWykresu1(this.t);
        // Dodanie do wykresu 2
        wykresy.dodajDoWykresu2(this.l, this.t);
        // Dodanie do wykresu 3
        wykresy.dodajDoWykresu3(this.kanaly, this.t);
        // Dodanie do wykresu 4 - zgłoszenia przybyłe
        wykresy.dodajDoWykresu4(this.liczba_zgloszen_przybylych, Math.round(this.t*100.0)/100.0);
        // Dodanie do wykresu 5 - Zgłoszenia obsłużone
        wykresy.dodajDoWykresu5(this.liczba_zgloszen_obsluzonych, Math.round((this.t+deltaMi)*100.0)/100.0);

        this.liczba_zgloszen_przybylych++;
        return;
    }
}
