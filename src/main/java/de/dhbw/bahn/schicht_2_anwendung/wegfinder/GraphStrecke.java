package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

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