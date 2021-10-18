package schicht_4_abstraktion.dijkstra;

import schicht_4_abstraktion.Identifizierbar;

public interface Kante extends Identifizierbar {
    Knoten holeStartKnoten();

    Knoten holeEndKnoten();

    double holeGewichtung();
}
