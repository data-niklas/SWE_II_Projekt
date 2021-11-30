package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

public abstract class StreckenKante implements Kante {

    private final Strecke strecke;
    private final BahnhofsKnoten startBahnhof;
    private final BahnhofsKnoten endBahnhof;

    public StreckenKante(Strecke strecke, BahnhofsKnoten startBahnhof, BahnhofsKnoten endBahnhof) {
        this.strecke = strecke;
        this.startBahnhof = startBahnhof;
        this.endBahnhof = endBahnhof;
    }

    public Strecke holeStrecke() {
        return strecke;
    }

    @Override
    public BahnhofsKnoten holeStartKnoten() {
        return this.startBahnhof;
    }

    @Override
    public Knoten holeEndKnoten() {
        return this.endBahnhof;
    }

    @Override
    public String holeIdentifizierer() {
        return this.strecke.holeIdentifizierer();
    }
}
