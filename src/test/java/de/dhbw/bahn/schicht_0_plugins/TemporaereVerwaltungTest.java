package de.dhbw.bahn.schicht_0_plugins;

import de.dhbw.bahn.schicht_0_plugins.persistierung.TemporaereVerwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Zug;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TemporaereVerwaltung Plugin")
public class TemporaereVerwaltungTest {

    @Test
    @DisplayName("Entitaet soll persistiert werden. Nur unechte Persistenz wird gefordert.")
    public void entitaetPersistieren(){
        //Arrange
        Bahnhof testBahnhof = new Bahnhof("Test Bahnhof");
        TemporaereVerwaltung<Bahnhof> cut = new TemporaereVerwaltung<>();

        //Act
        boolean hatEntitaetBevorPersistenz = cut.hatEntitaet(testBahnhof.holeIdentifizierer());
        cut.persistiereEntitaet(testBahnhof);
        boolean hatEntitaetNachPersistenz = cut.hatEntitaet(testBahnhof.holeIdentifizierer());

        //Assert
        Assertions.assertEquals(false, hatEntitaetBevorPersistenz);
        Assertions.assertEquals(true, hatEntitaetNachPersistenz);
    }

    @Test
    @DisplayName("Entitaet soll nach loeschen nicht mehr existent sein.")
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
        Assertions.assertEquals(true, hatEntitaetBevorPersistenz);
        Assertions.assertEquals(false, hatEntitaetNachPersistenz);
    }

    @Test
    @DisplayName("Kann eine Entitaet aktualisiert werden.")
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
        Assertions.assertEquals(zugVerbrauch, vorherigerVerbrauch, Double.MIN_VALUE);
        Assertions.assertEquals(zugNeuerVerbrauch, nachfolgenderVerbrauch, Double.MIN_VALUE);
    }
}
