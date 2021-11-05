package de.dhbw.bahn.schicht_4_abstraktion.graph;

import java.util.ArrayList;
import java.util.List;

public class MockGraph implements Graph {

    private final List<Knoten> knoten;
    private final List<Kante> kanten;

    public MockGraph() {
        knoten = new ArrayList<>();
        kanten = new ArrayList<>();
    }

    public Knoten sucheKnoten(String identifizierer) {
        for (Knoten knoten : this.knoten) {
            if (knoten.holeIdentifizierer().equals(identifizierer)) return knoten;
        }
        return null;
    }

    public void neueKante(String von, String zu, double gewichtung, String identifizierer) {
        Knoten vonKnoten = sucheKnoten(von);
        Knoten zuKnoten = sucheKnoten(zu);
        kanten.add(new MockKante(vonKnoten, zuKnoten, gewichtung, identifizierer));
    }

    public void neuerKnoten(Knoten knoten) {
        this.knoten.add(knoten);
    }


    @Override
    public List<Knoten> holeKnoten() {
        return knoten;
    }

    @Override
    public List<Kante> holeKanten() {
        return kanten;
    }
}
