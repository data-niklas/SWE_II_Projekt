package schicht_4_abstraktion.dijkstra;

public class MockKante implements Kante{

    private final Knoten startKnoten, endKnoten;
    private final double gewichtung;

    public MockKante(Knoten startKnoten, Knoten endKnoten, double gewichtung){
        this.startKnoten = startKnoten;
        this.endKnoten = endKnoten;
        this.gewichtung = gewichtung;
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
}
