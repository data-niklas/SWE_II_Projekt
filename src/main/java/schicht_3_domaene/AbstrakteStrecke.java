package schicht_3_domaene;

import schicht_4_abstraktion.Identifizierbar;
import schicht_4_abstraktion.dijkstra.Kante;


public abstract class AbstrakteStrecke implements Kante, Identifizierbar {
    private String bezeichnung;
    private double gewichtung;

    private Bahnhof startBahnhof;
    private Bahnhof endBahnhof;

    public AbstrakteStrecke(String bezeichnung,
                   double gewichtung,
                   Bahnhof startBahnhof,
                   Bahnhof endBahnhof) {
        this.bezeichnung = bezeichnung;
        this.startBahnhof = startBahnhof;
        this.endBahnhof = endBahnhof;
        this.setzeGewichtung(gewichtung);
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
        return this.gewichtung;
    }

    @Override
    public String holeIdentifizierer() {
        return this.bezeichnung;
    }

    public String holeBezeichnung() {
        return this.bezeichnung;
    }

    public void setzeGewichtung(double gewichtung) {
        if (gewichtung < 0)
            throw new IllegalArgumentException("Die Laenge muss positiv sein.");
        this.gewichtung = gewichtung;
    }
}
