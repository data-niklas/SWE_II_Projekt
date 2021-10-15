package schicht_4_abstraktion.dijkstra;

import schicht_4_abstraktion.Identifizierbar;

public interface Knoten extends Identifizierbar {
    boolean equals(Knoten andererKnoten);
}
