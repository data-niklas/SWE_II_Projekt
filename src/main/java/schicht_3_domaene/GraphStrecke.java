package schicht_3_domaene;

import schicht_4_abstraktion.dijkstra.Kante;
import schicht_4_abstraktion.dijkstra.Knoten;

public abstract class GraphStrecke implements Kante {

    private final Strecke strecke;

    public GraphStrecke(Strecke strecke) {
        this.strecke = strecke;
    }

    public Strecke holeStrecke() {
        return strecke;
    }

    @Override
    public Knoten holeStartKnoten() {
        return this.strecke.holeStartBahnhof();
    }

    @Override
    public Knoten holeEndKnoten() {
        return this.strecke.holeEndBahnhof();
    }

    @Override
    public String holeIdentifizierer() {
        return this.strecke.holeIdentifizierer();
    }
}
