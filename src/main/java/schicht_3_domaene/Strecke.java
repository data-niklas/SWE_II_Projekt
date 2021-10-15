package schicht_3_domaene;

import schicht_4_abstraktion.Identifizierbar;
import schicht_4_abstraktion.dijkstra.Kante;

import java.util.Set;

public class Strecke implements Kante, Identifizierbar {

    private String bezeichnung;
    private double laenge;
    private Set<ZugTyp> erlaubteZugTypen;
    private boolean freigegeben;

    private Bahnhof startBahnhof;
    private Bahnhof endBahnhof;

    public Strecke(String bezeichnung,
                   double laenge,
                   Set<ZugTyp> erlaubteZugTypen,
                   boolean freigegeben,
                   Bahnhof startBahnhof,
                   Bahnhof endBahnhof) {
        this.bezeichnung = bezeichnung;
        this.erlaubteZugTypen = erlaubteZugTypen;
        this.freigegeben = freigegeben;
        this.startBahnhof = startBahnhof;
        this.endBahnhof = endBahnhof;
        this.setzeLaenge(laenge);
    }

    @Override
    public Bahnhof holeStartKnoten() {
        return this.startBahnhof;
    }

    @Override
    public Bahnhof holeEndKnoten() {
        return this.endBahnhof;
    }

    @Override
    public double holeGewichtung() {
        return this.laenge;
    }

    @Override
    public String holeIdentifizierer() {
        return this.bezeichnung;
    }

    public String holeBezeichnung() {
        return this.bezeichnung;
    }

    public void setzeLaenge(double laenge) {
        if (laenge < 0)
            throw new IllegalArgumentException("Die Laenge muss positiv sein.");
        this.laenge = laenge;
    }
}
