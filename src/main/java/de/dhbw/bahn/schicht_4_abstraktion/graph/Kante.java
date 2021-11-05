package de.dhbw.bahn.schicht_4_abstraktion.graph;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

public interface Kante extends Identifizierbar {
    Knoten holeStartKnoten();

    Knoten holeEndKnoten();

    double holeGewichtung();
}
