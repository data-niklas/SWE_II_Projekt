package schicht_4_abstraktion.dijkstra;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DijkstraTest {

    //CUT
    private Dijkstra dijkstra;

    //Maximal 24
    private static void initialisiereKnoten(MockGraph graph, int anzahl) {
        for (int i = 0; i < anzahl; i++) {
            String identifizierer = Character.toString((char) ('A' + i));
            graph.neuerKnoten(new MockKnoten(identifizierer));
        }
    }

    /* A        B
     *
     *      E
     *
     * C        D
     * Alle Knoten sind mit ihren direkt nächsten Knoten mit Gewichtung 1 verbunden
     */
    private static MockGraph mockGraph1() {
        MockGraph graph = new MockGraph();
        //A-E
        initialisiereKnoten(graph, 5);
        graph.neueKante("A", "B", 1, "AB_1");
        graph.neueKante("A", "E", 1, "AE_1");
        graph.neueKante("A", "C", 1, "AC_1");
        graph.neueKante("D", "C", 1, "DC_1");
        graph.neueKante("D", "E", 1, "DE_1");
        graph.neueKante("D", "B", 1, "DB_1");
        graph.neueKante("C", "E", 1, "CE_1");
        graph.neueKante("B", "E", 1, "BE_1");
        return graph;
    }

    /* A        B
     *
     *      E
     *
     * C        D
     * Alle Knoten sind mit ihren direkt nächsten Knoten
     */
    private static MockGraph mockGraph2() {
        MockGraph graph = new MockGraph();
        //A-E
        initialisiereKnoten(graph, 5);
        graph.neueKante("A", "B", 2, "AB_1");//!
        graph.neueKante("A", "E", 1, "AE_1");
        graph.neueKante("A", "C", 1, "AC_1");
        graph.neueKante("D", "C", 1, "DC_1");
        graph.neueKante("D", "E", 1, "DE_1");
        graph.neueKante("D", "B", 1, "DB_1");
        graph.neueKante("C", "E", 3, "CE_1");//!
        graph.neueKante("B", "E", 1, "BE_1");
        return graph;
    }

    /* A        B
     *
     *      C
     */
    private static MockGraph mockGraph3() {
        MockGraph graph = new MockGraph();
        //A-E
        initialisiereKnoten(graph, 3);
        graph.neueKante("A", "B", 3, "AB_1");//!
        graph.neueKante("A", "C", 1, "AC_1");
        graph.neueKante("C", "B", 1, "CB_1");
        return graph;
    }

    //Reihenfolge wird ignoriert
    private static void assertKanteGleich(Kante kante, String identifizierer1, String identifizierer2) {
        String startIdentifizierer = kante.holeStartKnoten().holeIdentifizierer();
        String endIdentifizierer = kante.holeEndKnoten().holeIdentifizierer();
        boolean reihenfolge1 = startIdentifizierer.equals(identifizierer1) && endIdentifizierer.equals(identifizierer2);
        boolean reihenfolge2 = startIdentifizierer.equals(identifizierer2) && endIdentifizierer.equals(identifizierer1);
        Assert.assertTrue(reihenfolge1 || reihenfolge2);
    }


    @Test
    public void einfacherGewichtungsTest() {
        //Init
        MockGraph graph = mockGraph3();
        dijkstra = new Dijkstra(graph);
        //
        List<Kante> weg = (List<Kante>) dijkstra.kuerzesterWeg(graph.sucheKnoten("A"), graph.sucheKnoten("B"));
        //
        Assert.assertEquals(2, weg.size());
        assertKanteGleich(weg.get(0), "A", "C");
        assertKanteGleich(weg.get(1), "C", "B");
    }


}
