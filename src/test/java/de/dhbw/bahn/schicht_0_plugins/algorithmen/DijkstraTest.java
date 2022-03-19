package de.dhbw.bahn.schicht_0_plugins.algorithmen;

import de.dhbw.bahn.schicht_0_plugins.algorithmen.Dijkstra;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.BahnhofsKnoten;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenKante;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenNetz;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@DisplayName("Dijkstra Wegsuche Plugin")
public class DijkstraTest {

    @Test
    @DisplayName("Wegsuche von A nach A. Umwege sollen ignoriert werden.")
    public void reflexivitaetTest(){
        //Arrange
        StreckenNetz graph = new StreckenNetz();
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        BahnhofsKnoten testKnoten = new BahnhofsKnoten(testBahnhof);
        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;

        Bahnhof startBahnhof = testBahnhof;
        Bahnhof endBahnhof = testBahnhof;
        Strecke umweg = new Strecke("Umweg von Test Bahnhof zu Test Bahnhof",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );
        graph.bahnhofHinzufuegen(testKnoten);
        graph.streckeHinzufuegen(new StreckenKante(umweg, testKnoten, testKnoten) {
            @Override
            public double holeGewichtung() {
                return this.holeStrecke().holeLaenge();
            }
        });

        Dijkstra cut = new Dijkstra();
        cut.initialisiereGraphen(graph);

        //Act
        List<? extends Kante> wege = cut.berechneWeg(testKnoten, testKnoten);

        //Assert
        //Kein Weg wird erwartet, da kein Weg zu sich selbst benötigt wird
        Assertions.assertEquals(0, wege.size());
    }

    @Test
    @DisplayName("Wegsuche in einem Dreieck.")
    public void dreieckTest(){
        //Arrange
        StreckenNetz graph = new StreckenNetz();
        Bahnhof testBahnhof1 = new Bahnhof("Test Bahnhof1");
        Bahnhof testBahnhof2 = new Bahnhof("Test Bahnhof2");
        Bahnhof testBahnhof3 = new Bahnhof("Test Bahnhof3");
        BahnhofsKnoten testKnoten1 = new BahnhofsKnoten(testBahnhof1);
        BahnhofsKnoten testKnoten2 = new BahnhofsKnoten(testBahnhof2);
        BahnhofsKnoten testKnoten3 = new BahnhofsKnoten(testBahnhof3);
        graph.bahnhofHinzufuegen(testKnoten1);
        graph.bahnhofHinzufuegen(testKnoten2);
        graph.bahnhofHinzufuegen(testKnoten3);


        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;

        Bahnhof startBahnhof = testBahnhof1;
        Bahnhof endBahnhof = testBahnhof2;
        Strecke strecke12 = new Strecke("Strecke von 1 zu 2",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );


        graph.streckeHinzufuegen(new StreckenKante(strecke12, testKnoten1, testKnoten2) {
            @Override
            public double holeGewichtung() {
                return this.holeStrecke().holeLaenge();
            }
        });

        laenge = 10;
        startBahnhof = testBahnhof2;
        endBahnhof = testBahnhof3;
        Strecke strecke23 = new Strecke("Strecke von 2 zu 3",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );
        graph.streckeHinzufuegen(new StreckenKante(strecke23, testKnoten2, testKnoten3) {
            @Override
            public double holeGewichtung() {
                return this.holeStrecke().holeLaenge();
            }
        });

        laenge = 6;
        startBahnhof = testBahnhof3;
        endBahnhof = testBahnhof1;
        Strecke strecke31 = new Strecke("Strecke von 3 zu 1",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );
        graph.streckeHinzufuegen(new StreckenKante(strecke31, testKnoten3, testKnoten1) {
            @Override
            public double holeGewichtung() {
                return this.holeStrecke().holeLaenge();
            }
        });

        Dijkstra cut = new Dijkstra();
        cut.initialisiereGraphen(graph);

        //Act
        List<? extends Kante> wege = cut.berechneWeg(testKnoten2, testKnoten3);

        //Assert
        //Der Weg von 2->1->3 wird erwartet
        Assertions.assertEquals(2, wege.size());
    }

}
