package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MSI on 2016-01-03.
 */
public class Klaster {

    private List<SMO> list;
    private int count = 4;


    public Klaster() {

        list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            list.add(new SMO(i));
            Main.clear();
        }
    }

    public void simulate() {
        for (int i = 0; i < count; i++) {
            list.get(i).simulate();
        }
    }
}
