package de.dhbw.bahn.schicht_0_plugins;

import de.dhbw.bahn.schicht_0_plugins.persistierung.TemporaereVerwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Zug;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import org.junit.Assert;
import org.junit.Test;

public class TemporaereVerwaltungTest {

    @Test
    public void entitaetPersistieren(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        TemporaereVerwaltung<Bahnhof> cut = new TemporaereVerwaltung<>();

        //Act
        boolean hatEntitaetBevorPersistenz = cut.hatEntitaet(testBahnhof.holeIdentifizierer());
        cut.persistiereEntitaet(testBahnhof);
        boolean hatEntitaetNachPersistenz = cut.hatEntitaet(testBahnhof.holeIdentifizierer());

        //Assert
        Assert.assertEquals(false, hatEntitaetBevorPersistenz);
        Assert.assertEquals(true, hatEntitaetNachPersistenz);
    }

    @Test
    public void entitaetLoeschen(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        TemporaereVerwaltung<Bahnhof> cut = new TemporaereVerwaltung<>();
        cut.persistiereEntitaet(testBahnhof);

        //Act
        boolean hatEntitaetBevorPersistenz = cut.hatEntitaet(testBahnhof.holeIdentifizierer());
        cut.loescheEntitaet(testBahnhof.holeIdentifizierer());
        boolean hatEntitaetNachPersistenz = cut.hatEntitaet(testBahnhof.holeIdentifizierer());

        //Assert
        Assert.assertEquals(true, hatEntitaetBevorPersistenz);
        Assert.assertEquals(false, hatEntitaetNachPersistenz);
    }

    @Test
    public void entitaetAktualisieren(){
        //Arrange
        int zugNummer = 666;
        double zugHoechstGeschwindigkeit = 142.0;
        double zugVerbrauch = 12.0;

        Zug testZug = new Zug(zugNummer, ZugTyp.IRE, zugHoechstGeschwindigkeit, zugVerbrauch);
        TemporaereVerwaltung<Zug> cut = new TemporaereVerwaltung<>();
        cut.persistiereEntitaet(testZug);

        double zugNeuerVerbrauch = 42.0;

        //Act
        double vorherigerVerbrauch = cut.holeEntitaet(testZug.holeIdentifizierer()).holeVerbrauch();
        testZug.setzeVerbrauch(zugNeuerVerbrauch);
        cut.aktualisiereEntitaet(testZug);
        double nachfolgenderVerbrauch = cut.holeEntitaet(testZug.holeIdentifizierer()).holeVerbrauch();

        //Assert
        Assert.assertEquals(zugVerbrauch, vorherigerVerbrauch, Double.MIN_VALUE);
        Assert.assertEquals(zugNeuerVerbrauch, nachfolgenderVerbrauch, Double.MIN_VALUE);
    }
}
