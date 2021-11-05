package de.dhbw.bahn.schicht_3_domaene;

import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

public class Bahnhof implements Knoten {

    private final String name;

    public Bahnhof(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Knoten andererKnoten) {
        return andererKnoten.holeIdentifizierer().equals(this.holeIdentifizierer());
    }

    public String holeName() {
        return name;
    }

    @Override
    public String holeIdentifizierer() {
        return this.name;
    }
}
