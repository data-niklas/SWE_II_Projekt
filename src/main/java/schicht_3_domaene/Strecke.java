package schicht_3_domaene;

import java.util.Set;

public class Strecke extends AbstrakteStrecke {

    private Set<ZugTyp> erlaubteZugTypen;
    private boolean freigegeben;
    private double maximalGeschwindigkeit;

    public Strecke(String bezeichnung,
                   double laenge,
                   double maximalGeschwindigkeit,
                   Set<ZugTyp> erlaubteZugTypen,
                   boolean freigegeben,
                   Bahnhof startBahnhof,
                   Bahnhof endBahnhof) {
        super(bezeichnung, laenge, startBahnhof, endBahnhof);
        this.erlaubteZugTypen = erlaubteZugTypen;
        this.freigegeben = freigegeben;
        this.setzeMaximalGeschwindigkeit(maximalGeschwindigkeit);
    }

    private void setzeMaximalGeschwindigkeit(double maximalGeschwindigkeit) {
        if (maximalGeschwindigkeit < 0)
            throw new IllegalArgumentException("Die Maximalgeschwindigkeit muss positiv sein.");
        this.maximalGeschwindigkeit = maximalGeschwindigkeit;
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
}
