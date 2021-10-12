package schicht_4_abstraktion.dijkstra;

public interface Kante {
    Knoten holeStartKnoten();
    Knoten holeEndKnoten();
    double holeGewichtung();
}
