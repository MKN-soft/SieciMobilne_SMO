package main;

/**
 * Created by MSI on 2015-12-20.
 */
public class Main {

    public static void main(String [] args) {
        Klaster klaster = new Klaster();
        klaster.simulate();
    }

    public static void clear() {
        for(int clear = 0; clear < 1000; clear++) {
            System.out.println("\b") ;
        }
    }

}
