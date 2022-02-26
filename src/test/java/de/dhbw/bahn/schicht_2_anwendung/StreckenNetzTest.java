package de.dhbw.bahn.schicht_2_anwendung;

import de.dhbw.bahn.schicht_2_anwendung.wegfinder.BahnhofsKnoten;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenKante;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenNetz;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@DisplayName("StreckenNetz")
public class StreckenNetzTest {

    @Test
    @DisplayName("Suche nach nicht vorhandenem Bahnhof.")
    public void sucheBahnhofLeer(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        StreckenNetz cut = new StreckenNetz();

        //Act
        Optional<BahnhofsKnoten> ergebnis = cut.sucheBahnhof(testBahnhof.holeIdentifizierer());

        //Assert
        Assertions.assertFalse(ergebnis.isPresent());
    }

    @Test
    @DisplayName("Suche nach vorhandenem Bahnhof.")
    public void sucheBahnhofVorhanden(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        StreckenNetz cut = new StreckenNetz();
        cut.bahnhofHinzufuegen(new BahnhofsKnoten(testBahnhof));

        //Act
        Optional<BahnhofsKnoten> ergebnis = cut.sucheBahnhof(testBahnhof.holeIdentifizierer());

        //Assert
        Assertions.assertTrue(ergebnis.isPresent());
        Assertions.assertEquals(testBahnhof.holeIdentifizierer(), ergebnis.get().holeIdentifizierer());
    }

    @Test
    @DisplayName("Suche nach nicht vorhandener Strecke.")
    public void sucheStreckeLeer(){
        //Arrange
        StreckenNetz cut = new StreckenNetz();
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        BahnhofsKnoten testKnoten = new BahnhofsKnoten(testBahnhof);
        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;

        Bahnhof startBahnhof = testBahnhof;
        Bahnhof endBahnhof = testBahnhof;
        Strecke testStrecke = new Strecke("Umweg von Test Bahnhof zu Test Bahnhof",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );
        StreckenKante testStreckenKante = new StreckenKante(testStrecke, testKnoten, testKnoten) {
            @Override
            public double holeGewichtung() {
                return this.holeStrecke().holeLaenge();
            }
        };

        cut.bahnhofHinzufuegen(testKnoten);
        cut.streckeHinzufuegen(testStreckenKante);

        //Act
        Optional<StreckenKante> ergebnis = cut.sucheStrecke(testStreckenKante.holeIdentifizierer());

        //Assert
        Assertions.assertTrue(ergebnis.isPresent());
        Assertions.assertEquals(testStrecke.holeIdentifizierer(), ergebnis.get().holeIdentifizierer());
    }

    @Test
    @DisplayName("Suche nach vorhandener Strecke.")
    public void sucheStreckeVorhanden(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        BahnhofsKnoten testKnoten = new BahnhofsKnoten(testBahnhof);
        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;

        Bahnhof startBahnhof = testBahnhof;
        Bahnhof endBahnhof = testBahnhof;
        Strecke testStrecke = new Strecke("Umweg von Test Bahnhof zu Test Bahnhof",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );
        StreckenKante testStreckenKante = new StreckenKante(testStrecke, testKnoten, testKnoten) {
            @Override
            public double holeGewichtung() {
                return this.holeStrecke().holeLaenge();
            }
        };

        StreckenNetz cut = new StreckenNetz();

        //Act
        Optional<StreckenKante> ergebnis = cut.sucheStrecke(testStreckenKante.holeIdentifizierer());

        //Assert
        Assertions.assertFalse(ergebnis.isPresent());
    }
}
