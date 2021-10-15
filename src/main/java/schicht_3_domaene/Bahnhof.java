package schicht_3_domaene;

import schicht_4_abstraktion.dijkstra.Knoten;

public class Bahnhof implements Knoten {

    private String name;

    @Override
    public boolean equals(Knoten andererKnoten) {
        return andererKnoten.holeIdentifizierer().equals(this.holeIdentifizierer());
    }

    @Override
    public String holeIdentifizierer() {
        return this.name;
    }
}
