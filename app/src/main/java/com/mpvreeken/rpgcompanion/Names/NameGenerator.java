package com.mpvreeken.rpgcompanion.Names;

import java.util.List;
import java.util.Random;

/**
 * Created by Sven on 12/18/2017.
 */

public class NameGenerator {

    protected Random random = new Random();

    protected String cap(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    protected String r(List<String> l) {
        return l.get(random.nextInt(l.size()));
    }

    public String generate(String s) {
        return "";
    }
}
