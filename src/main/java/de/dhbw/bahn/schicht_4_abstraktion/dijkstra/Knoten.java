package de.dhbw.bahn.schicht_4_abstraktion.dijkstra;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

public interface Knoten extends Identifizierbar {
    boolean equals(Knoten andererKnoten);
}
