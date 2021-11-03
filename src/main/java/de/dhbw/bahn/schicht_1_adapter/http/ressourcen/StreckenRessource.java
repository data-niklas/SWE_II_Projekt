package de.dhbw.bahn.schicht_1_adapter.http.ressourcen;

import de.dhbw.bahn.schicht_3_domaene.ZugTyp;

import java.util.Set;

public class StreckenRessource {
    private final String bezeichnung;
    private final String startBahnhof, endBahnhof;
    private final double laenge;

    private final Set<ZugTyp> erlaubteZugTypen;
    private final boolean freigegeben;
    private final double maximalGeschwindigkeit;

    public StreckenRessource(String bezeichnung, String startBahnhof, String endBahnhof, double laenge, Set<ZugTyp> erlaubteZugTypen, boolean freigegeben, double maximalGeschwindigkeit) {
        this.bezeichnung = bezeichnung;
        this.startBahnhof = startBahnhof;
        this.endBahnhof = endBahnhof;
        this.laenge = laenge;
        this.erlaubteZugTypen = erlaubteZugTypen;
        this.freigegeben = freigegeben;
        this.maximalGeschwindigkeit = maximalGeschwindigkeit;
    }

    public String holeBezeichnung() {
        return bezeichnung;
    }

    public String holeStartBahnhof() {
        return startBahnhof;
    }

    public String holeEndBahnhof() {
        return endBahnhof;
    }

    public double holeLaenge() {
        return laenge;
    }

    public Set<ZugTyp> holeErlaubteZugTypen() {
        return erlaubteZugTypen;
    }

    public boolean isFreigegeben() {
        return freigegeben;
    }

    public double holeMaximalGeschwindigkeit() {
        return maximalGeschwindigkeit;
    }
}
