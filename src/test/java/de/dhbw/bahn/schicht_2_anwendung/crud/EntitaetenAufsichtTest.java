package de.dhbw.bahn.schicht_2_anwendung.crud;

import de.dhbw.bahn.schicht_2_anwendung.DuplikatFehler;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeSet;

@DisplayName("EntitaetenAufsicht Use-Case")
public class EntitaetenAufsichtTest {

    @Test
    @DisplayName("streckeHinzufuegen(). Bahnhoefe existieren bereits.")
    public void fuegeNeueStreckeHinzuBahnhoefeExistent() {
        //Capture
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

        //Arrange
        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act
        cut.streckeHinzufuegen(testStrecke);

        //Assert
        Assertions.assertEquals(
                testStrecke, cut.holeStrecke(testStrecke.holeIdentifizierer())
        );

        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }

    @Test
    @DisplayName("streckeHinzufuegen(). Startbahnhof existiert nicht.")
    public void fuegeNeueStreckeHinzuStartBahnhofNichtExistent() {
        //Capture
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

        //Arrange
        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act, Assert
        Assertions.assertThrows(MissingResourceException.class, ()-> cut.streckeHinzufuegen(testStrecke));

        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }

    @Test
    @DisplayName("streckeHinzufuegen(). Endbahnhof existiert nicht.")
    public void fuegeNeueStreckeHinzuEndBahnhofNichtExistent() {
        //Capture
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

        Verwaltung<Bahnhof> mockVerwaltungBahnhof = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Strecke> mockVerwaltungStrecke = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Zug> mockVerwaltungZug = EasyMock.createMock(Verwaltung.class);

        EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(startBahnhof.holeIdentifizierer())).andReturn(true).once();
        EasyMock.expect(mockVerwaltungBahnhof.hatEntitaet(endBahnhof.holeIdentifizierer())).andReturn(false).once();

        EasyMock.expect(mockVerwaltungStrecke.hatEntitaet(testStrecke.holeIdentifizierer())).andReturn(false).once();

        EasyMock.replay(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Arrange
        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act, Assert
        Assertions.assertThrows(MissingResourceException.class, ()-> cut.streckeHinzufuegen(testStrecke));


        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }

    @Test
    @DisplayName("streckeHinzufuegen(). Strecke existiert bereits.")
    public void fuegeVorhandeneStreckeHinzu() {
        //Capture
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


        Verwaltung<Bahnhof> mockVerwaltungBahnhof = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Strecke> mockVerwaltungStrecke = EasyMock.createMock(Verwaltung.class);
        Verwaltung<Zug> mockVerwaltungZug = EasyMock.createMock(Verwaltung.class);

        EasyMock.expect(mockVerwaltungStrecke.hatEntitaet(testStrecke.holeIdentifizierer())).andReturn(true).once();

        EasyMock.replay(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Arrange
        EntitaetenAufsicht cut = new EntitaetenAufsicht(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);

        //Act, Assert
        Assertions.assertThrows(DuplikatFehler.class, ()-> cut.streckeHinzufuegen(testStrecke));

        //Verify
        EasyMock.verify(mockVerwaltungBahnhof, mockVerwaltungStrecke, mockVerwaltungZug);
    }
}
