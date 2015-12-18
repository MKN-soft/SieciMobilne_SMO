package com.siecimobilne.smo.narzedzia;

import java.io.PrintWriter;
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

    private PrintWriter zapis;//do zapisu czasów

    public SMO() {
        Scanner odczyt = new Scanner(System.in);
        System.out.println("Podaj lambda: ");
        this.lambda = Integer.parseInt(odczyt.nextLine());

        System.out.println("Podaj mi: ");
        this.mi = Integer.parseInt(odczyt.nextLine());

        this.L = 10;
        this.T = 10;
        this.K = 1;

        this.t = 0.0;
        this.l = 0;

        this.kolejka = new Zdarzenie[L];
        this.tablica = new Zdarzenie[this.K + 1];

        this.kanaly = new Kanal[K];

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

        // Tworzymy pierwsze "zdarzenie"
        System.out.println("lambda: " + this.lambda);
        Zdarzenie zdarzenie = new Zdarzenie(1, 1.0/this.lambda);
        tablica[0] = zdarzenie;


        // Szukamy minimum w tablicy
        Zdarzenie minimum = min(tablica);
        int licznik = 0;
        while (minimum.getCzas() < T) {
            minimum = min(tablica);
            System.out.println(tablica[0].getCzas() + "\t" + tablica[1].getCzas());
            System.out.println("Przejscie: "+ licznik);
            //TODO symulacja

            // Sprawdza czy zdarzenie jest typu 1
            if (minimum.getTyp() == 1) {
                t = minimum.getCzas();
                zapiszCzas(t,1);
                // Czy kolejka jest pełna
                if (l < L) {
                    // Dodaj zdarzenie do kolejki
                    dodajZdarzenieDoKolejki(minimum.getTyp(), minimum.getCzas());
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
            else if (minimum.getTyp() == 2){
                t = minimum.getCzas();
                zapiszCzas(t,2);
                // Czy kolejka jest pusta
                if (l == 0) {
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
                return;
            }
        }
    }

    //zapisuje czas na osi
    public void zapiszCzas(double czas, int typ){
        this.zapis.println(czas + " " + typ);
    }

    public void ustalenieMomentuPrzyjscia(){
        this.tablica[0] = new Zdarzenie(1,this.tablica[0].getCzas() + (1.0/lambda));
        return;
    }
}
