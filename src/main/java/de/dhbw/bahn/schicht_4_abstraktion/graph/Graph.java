package de.dhbw.bahn.schicht_4_abstraktion.graph;

import java.util.List;

public interface Graph {
    List<? extends Knoten> holeKnoten();

    List<? extends Kante> holeKanten();
}
