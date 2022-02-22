package de.dhbw.bahn.schicht_3_domaene;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

import java.util.Objects;
import java.util.Set;

public class Strecke implements Identifizierbar {

    private final String bezeichnung;
    private final Bahnhof startBahnhof;
    private final Bahnhof endBahnhof;
    private final double laenge;

    private final Set<ZugTyp> erlaubteZugTypen;
    private final boolean freigegeben;
    private double maximalGeschwindigkeit;

    public Strecke(String bezeichnung,
                   double laenge,
                   double maximalGeschwindigkeit,
                   Set<ZugTyp> erlaubteZugTypen,
                   boolean freigegeben,
                   Bahnhof startBahnhof,
                   Bahnhof endBahnhof) {
        this.bezeichnung = bezeichnung;
        this.startBahnhof = startBahnhof;
        this.endBahnhof = endBahnhof;
        this.laenge = laenge;
        this.erlaubteZugTypen = erlaubteZugTypen;
        this.freigegeben = freigegeben;
        this.setzeMaximalGeschwindigkeit(maximalGeschwindigkeit);
    }

    public void setzeMaximalGeschwindigkeit(double maximalGeschwindigkeit) {
        if (maximalGeschwindigkeit < 0)
            throw new IllegalArgumentException("Die Maximalgeschwindigkeit muss positiv sein.");
        this.maximalGeschwindigkeit = maximalGeschwindigkeit;
    }

    public String holeBezeichnung() {
        return bezeichnung;
    }

    public Bahnhof holeStartBahnhof() {
        return startBahnhof;
    }

    public Bahnhof holeEndBahnhof() {
        return endBahnhof;
    }

    public double holeLaenge() {
        return this.laenge;
    }

    @Override
    public String holeIdentifizierer() {
        return this.bezeichnung;
    }


    public Set<ZugTyp> holeErlaubteZugTypen() {
        return erlaubteZugTypen;
    }

    public boolean istFreigegeben() {
        return freigegeben;
    }

    public double holeMaximalGeschwindigkeit() {
        return maximalGeschwindigkeit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Strecke)) return false;
        Strecke strecke = (Strecke) o;
        return Objects.equals(holeIdentifizierer(), strecke.holeIdentifizierer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(holeIdentifizierer());
    }
}
