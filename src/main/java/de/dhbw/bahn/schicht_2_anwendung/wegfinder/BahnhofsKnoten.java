package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

import java.util.Objects;

public class BahnhofsKnoten implements Knoten {

    private final Bahnhof bahnhof;

    public BahnhofsKnoten(Bahnhof bahnhof) {
        this.bahnhof = bahnhof;
    }

    public Bahnhof holeBahnhof() {
        return bahnhof;
    }

    @Override
    public boolean equals(Knoten andererKnoten) {
        return holeIdentifizierer().equals(andererKnoten.holeIdentifizierer());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BahnhofsKnoten that = (BahnhofsKnoten) o;
        return Objects.equals(bahnhof, that.bahnhof);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bahnhof);
    }

    @Override
    public String holeIdentifizierer() {
        return this.bahnhof.holeIdentifizierer();
    }
}
