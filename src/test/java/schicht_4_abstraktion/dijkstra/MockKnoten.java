package schicht_4_abstraktion.dijkstra;

public class MockKnoten implements Knoten{

    private final String identifizierer;

    public MockKnoten(String identifizierer){
        this.identifizierer = identifizierer;
    }

    @Override
    public String holeIdentifizierer() {
        return identifizierer;
    }

    @Override
    public boolean equals(Knoten andererKnoten) {
        return andererKnoten.holeIdentifizierer().equals(identifizierer);
    }
}
