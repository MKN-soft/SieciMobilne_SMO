package com.siecimobilne.smo.narzedzia;

/**
 * Klasa symulujaca kanal
 * Created by marcin on 16.12.15.
 */
public class Kanal {
    private boolean wolny = true;
    private int id;
    private Zdarzenie zdarzenie;

    public Kanal(int id) {
        this.id = id;
        zdarzenie  = null;
    }

    public void dodajZdarzenie(int typ, double czas){
        this.zdarzenie = new Zdarzenie(typ,czas);
    }

    public void usunZdarzenie(){
        this.zdarzenie = null;
    }
    public boolean isWolny() {
        if (this.zdarzenie == null)
            return wolny;
        else
            return false;
    }

    public Zdarzenie getZdarzenie() {
        return zdarzenie;
    }
}
