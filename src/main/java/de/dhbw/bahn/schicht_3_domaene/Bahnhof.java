package de.dhbw.bahn.schicht_3_domaene;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bahnhof)) return false;
        Bahnhof bahnhof = (Bahnhof) o;
        return Objects.equals(holeIdentifizierer(), bahnhof.holeIdentifizierer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(holeIdentifizierer());
    }
}
