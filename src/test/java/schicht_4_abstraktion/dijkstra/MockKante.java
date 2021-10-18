package schicht_4_abstraktion.dijkstra;

public class MockKante implements Kante {

    private final Knoten startKnoten, endKnoten;
    private final double gewichtung;
    private final String identifizierer;

    public MockKante(Knoten startKnoten, Knoten endKnoten, double gewichtung, String identifizierer) {
        this.startKnoten = startKnoten;
        this.endKnoten = endKnoten;
        this.gewichtung = gewichtung;
        this.identifizierer = identifizierer;
    }

    @Override
    public Knoten holeStartKnoten() {
        return startKnoten;
    }

    @Override
    public Knoten holeEndKnoten() {
        return endKnoten;
    }

    @Override
    public double holeGewichtung() {
        return gewichtung;
    }

    @Override
    public String holeIdentifizierer() {
        return this.identifizierer;
    }
}
