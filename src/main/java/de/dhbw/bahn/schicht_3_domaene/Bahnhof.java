package de.dhbw.bahn.schicht_3_domaene;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

public class Bahnhof implements Identifizierbar {

    private final String name;

    public Bahnhof(String name) {
        this.name = name;
    }


    public String holeName() {
        return name;
    }

    @Override
    public String holeIdentifizierer() {
        return this.name;
    }
}
