package de.dhbw.bahn.schicht_0_plugins;

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
    public void reflexivitätTest(){
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

}
