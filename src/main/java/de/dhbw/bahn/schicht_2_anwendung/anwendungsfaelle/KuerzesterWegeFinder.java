package de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle;

import de.dhbw.bahn.schicht_4_abstraktion.graph.Graph;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

import java.util.List;

public interface KuerzesterWegeFinder {

    void initialisiereGraphen(Graph graph);

    List<? extends Kante> kuerzesterWeg(Knoten start, Knoten end);
}
