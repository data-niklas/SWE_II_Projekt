package de.dhbw.bahn.schicht_2_anwendung;

import de.dhbw.bahn.schicht_2_anwendung.wegfinder.BahnhofsKnoten;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenNetz;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class StreckenNetzTest {

    @Test
    public void sucheBahnhofLeer(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        StreckenNetz cut = new StreckenNetz();

        //Act
        Optional<BahnhofsKnoten> ergebnis = cut.sucheBahnhof(testBahnhof.holeIdentifizierer());

        //Assert
        Assert.assertFalse(ergebnis.isPresent());
    }

    @Test
    public void sucheBahnhofVorhanden(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        StreckenNetz cut = new StreckenNetz();
        cut.bahnhofHinzufuegen(new BahnhofsKnoten(testBahnhof));

        //Act
        Optional<BahnhofsKnoten> ergebnis = cut.sucheBahnhof(testBahnhof.holeIdentifizierer());

        //Assert
        Assert.assertTrue(ergebnis.isPresent());
        Assert.assertEquals(testBahnhof.holeIdentifizierer(), ergebnis.get().holeIdentifizierer());
    }
}
