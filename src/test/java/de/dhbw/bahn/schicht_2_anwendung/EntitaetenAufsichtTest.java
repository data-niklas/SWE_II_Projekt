package de.dhbw.bahn.schicht_2_anwendung;

import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeSet;

public class EntitaetenAufsichtTest {

    @Test
    public void fuegeNeueStreckeHinzuBahnhoefeExistent() {
        //Arrange
        //Daten Objekte
        Bahnhof startBahnhof = new Bahnhof("Test Startbahnhof");
        Bahnhof endBahnhof = new Bahnhof("Test Endbahnhof");

        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;
        Strecke testStrecke = new Strecke("Test Strecke",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );

        //Mocks
        Verwaltung<Bahnhof> mockVerwaltungBahnhof = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Strecke> mockVerwaltungStrecke = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Zug> mockVerwaltungZug = EasyMock.createMock(Verwaltung.class);

        EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(startBahnhof.holeIdentifizierer())).andReturn(true).once();
        EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(endBahnhof.holeIdentifizierer())).andReturn(true).once();

        mockVerwaltungStrecke.persistiereEntitaet(testStrecke);
        EasyMock.expectLastCall().once();
        EasyMock.expect(mockVerwaltungStrecke.hatEntitaet(testStrecke.holeIdentifizierer())).andReturn(false).once();
        EasyMock.expect(mockVerwaltungStrecke.holeEntitaet(testStrecke.holeIdentifizierer())).andReturn(testStrecke).once();


        EasyMock.replay(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act
        cut.streckeHinzufuegen(testStrecke);

        //Assert
        Assert.assertEquals(
                testStrecke, cut.holeStrecke(testStrecke.holeIdentifizierer())
        );

        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }

    @Test
    public void fuegeNeueStreckeHinzuStartBahnhofNichtExistent() {
        //Arrange
        //Daten Objekte
        Bahnhof startBahnhof = new Bahnhof("Test Startbahnhof");
        Bahnhof endBahnhof = new Bahnhof("Test Endbahnhof");

        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;
        Strecke testStrecke = new Strecke("Test Strecke",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );

        //Mocks
        Verwaltung<Bahnhof> mockVerwaltungBahnhof = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Strecke> mockVerwaltungStrecke = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Zug> mockVerwaltungZug = EasyMock.createMock(Verwaltung.class);

        EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(startBahnhof.holeIdentifizierer())).andReturn(false).once();
        //EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(endBahnhof.holeIdentifizierer())).andReturn(false).once();

        EasyMock.expect(mockVerwaltungStrecke.hatEntitaet(testStrecke.holeIdentifizierer())).andReturn(false).once();

        EasyMock.replay(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act
        try {
            cut.streckeHinzufuegen(testStrecke);

            //Assert
            Assert.fail("Bahnhoefe sollten nicht im System vorhanden sein!");
        }
        catch (MissingResourceException ignored){
            //Error is expected
            //Assert
        }


        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }

    @Test
    public void fuegeNeueStreckeHinzuEndBahnhofNichtExistent() {
        //Arrange
        //Daten Objekte
        Bahnhof startBahnhof = new Bahnhof("Test Startbahnhof");
        Bahnhof endBahnhof = new Bahnhof("Test Endbahnhof");

        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;
        Strecke testStrecke = new Strecke("Test Strecke",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );

        //Mocks
        Verwaltung<Bahnhof> mockVerwaltungBahnhof = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Strecke> mockVerwaltungStrecke = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Zug> mockVerwaltungZug = EasyMock.createMock(Verwaltung.class);

        //EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(startBahnhof.holeIdentifizierer())).andReturn(false).once();
        EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(endBahnhof.holeIdentifizierer())).andReturn(false).once();

        EasyMock.expect(mockVerwaltungStrecke.hatEntitaet(testStrecke.holeIdentifizierer())).andReturn(false).once();

        EasyMock.replay(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act
        try {
            cut.streckeHinzufuegen(testStrecke);

            //Assert
            Assert.fail("Bahnhoefe sollten nicht im System vorhanden sein!");
        }
        catch (MissingResourceException ignored){
            //Error is expected
            //Assert
        }


        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }

    @Test
    public void fuegeVorhandeneStreckeHinzu() {
        //Arrange
        //Daten Objekte
        Bahnhof startBahnhof = new Bahnhof("Test Startbahnhof");
        Bahnhof endBahnhof = new Bahnhof("Test Endbahnhof");

        double maximalGeschwindigkeit = 100;
        double laenge = 3;
        Set<ZugTyp> erlaubteZugTypen = new TreeSet<>();
        erlaubteZugTypen.add(ZugTyp.IRE);
        boolean freigegeben = true;
        Strecke testStrecke = new Strecke("Test Strecke",
                laenge,
                maximalGeschwindigkeit,
                erlaubteZugTypen,
                freigegeben,
                startBahnhof,
                endBahnhof
        );

        //Mocks
        Verwaltung<Bahnhof> mockVerwaltungBahnhof = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Strecke> mockVerwaltungStrecke = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Zug> mockVerwaltungZug = EasyMock.createMock(Verwaltung.class);

        EasyMock.expect(mockVerwaltungStrecke.hatEntitaet(testStrecke.holeIdentifizierer())).andReturn(true).once();

        EasyMock.replay(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act
        try {
            cut.streckeHinzufuegen(testStrecke);

            //Assert
            Assert.fail("Strecke sollte bereits im System vorhanden sein!");
        }
        catch (DuplikatFehler ignored){
            //Error is expected
            //Assert
        }


        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }
}
