package schicht_3_domaene;

import schicht_4_abstraktion.dijkstra.Kante;
import schicht_4_abstraktion.dijkstra.Knoten;

import java.util.Set;

public abstract class GraphStrecke extends Strecke implements Kante {

    public GraphStrecke(Strecke strecke) {
        super(
                strecke.holeBezeichnung(),
                strecke.holeLaenge(),
                strecke.holeMaximalGeschwindigkeit(),
                strecke.holeErlaubteZugTypen(),
                strecke.istFreigegeben(),
                strecke.holeStartBahnhof(),
                strecke.holeEndBahnhof()
        );
    }

    public GraphStrecke(String bezeichnung, double laenge, double maximalGeschwindigkeit, Set<ZugTyp> erlaubteZugTypen, boolean freigegeben, Bahnhof startBahnhof, Bahnhof endBahnhof) {
        super(bezeichnung, laenge, maximalGeschwindigkeit, erlaubteZugTypen, freigegeben, startBahnhof, endBahnhof);
    }

    @Override
    public Knoten holeStartKnoten() {
        return this.holeStartBahnhof();
    }

    @Override
    public Knoten holeEndKnoten() {
        return this.holeEndBahnhof();
    }

}
