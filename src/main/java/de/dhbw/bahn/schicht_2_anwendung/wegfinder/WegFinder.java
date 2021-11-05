package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_4_abstraktion.graph.Graph;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

import java.util.List;

public interface WegFinder {

    void initialisiereGraphen(Graph graph);

    List<? extends Kante> berechneWeg(Knoten start, Knoten end);
}
