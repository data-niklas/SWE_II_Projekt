package de.dhbw.bahn.schicht_2_anwendung;

import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.*;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StreckenBerechnerTest {

    @Test
    public void berechneKuerzesteStrecke(){
        Verwaltung<Bahnhof> mockBahnhofsVerwaltung = EasyMock.mock(Verwaltung.class);
        Verwaltung<Strecke> mockStreckenVerwaltung = EasyMock.mock(Verwaltung.class);
        WegFinder mockWegFinder = EasyMock.mock(WegFinder.class);

        Bahnhof startBahnhof = new Bahnhof("Test Startbahnhof");
        Bahnhof endBahnhof = new Bahnhof("Test Endbahnhof");
        BahnhofsKnoten startBahnhofKnoten = new BahnhofsKnoten(startBahnhof);
        BahnhofsKnoten endBahnhofKnoten = new BahnhofsKnoten(endBahnhof);


        int zugNummer = 666;
        double zugHoechstGeschwindigkeit = 142.0;
        double zugVerbrauch = 12.0;

        Zug testZug = new Zug(zugNummer, ZugTyp.IRE, zugHoechstGeschwindigkeit, zugVerbrauch);

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

        List<Strecke> testStreckenEntitaeten = new ArrayList<>();
        testStreckenEntitaeten.add(testStrecke);
        List<Bahnhof> testBahnhofsEntitaeten = new ArrayList<>();
        testBahnhofsEntitaeten.add(startBahnhof);
        testBahnhofsEntitaeten.add(endBahnhof);

        EasyMock.expect(mockStreckenVerwaltung.holeEntitaeten()).andReturn(testStreckenEntitaeten).once();
        EasyMock.expect(mockBahnhofsVerwaltung.holeEntitaeten()).andReturn(testBahnhofsEntitaeten).once();
        mockWegFinder.initialisiereGraphen(
                EasyMock.isA(StreckenNetz.class)
        );
        EasyMock.expectLastCall().once();

        List<StreckenKante> testKantenListe = new ArrayList<>();
        testKantenListe.add(new StreckenKante(testStrecke, startBahnhofKnoten, endBahnhofKnoten) {
            @Override
            public double holeGewichtung() {
                return 0;
            }
        });

        IExpectationSetters<List<? extends Kante>> expect = EasyMock.expect(
                mockWegFinder.berechneWeg(EasyMock.isA(BahnhofsKnoten.class), EasyMock.isA(BahnhofsKnoten.class))
        );
        expect.andReturn(testKantenListe);
        expect.once();

        EasyMock.replay(mockBahnhofsVerwaltung, mockStreckenVerwaltung, mockWegFinder);

        //Arrange
        StreckenBerechner cut = new StreckenBerechner(mockBahnhofsVerwaltung, mockStreckenVerwaltung, mockWegFinder);

        //Act
        List<Strecke> ergebnis = cut.berechneKuerzesteStrecke(startBahnhof, endBahnhof, testZug);

        //Assert
        Assert.assertEquals(1, ergebnis.size());
        Assert.assertEquals(testStrecke, ergebnis.get(0));

        //Verify
        EasyMock.verify(mockBahnhofsVerwaltung, mockStreckenVerwaltung, mockWegFinder);
    }

    @Test
    public void berechneKuerzesteZeitStrecke(){
        Verwaltung<Bahnhof> mockBahnhofsVerwaltung = EasyMock.mock(Verwaltung.class);
        Verwaltung<Strecke> mockStreckenVerwaltung = EasyMock.mock(Verwaltung.class);
        WegFinder mockWegFinder = EasyMock.mock(WegFinder.class);

        Bahnhof startBahnhof = new Bahnhof("Test Startbahnhof");
        Bahnhof endBahnhof = new Bahnhof("Test Endbahnhof");
        BahnhofsKnoten startBahnhofKnoten = new BahnhofsKnoten(startBahnhof);
        BahnhofsKnoten endBahnhofKnoten = new BahnhofsKnoten(endBahnhof);


        int zugNummer = 666;
        double zugHoechstGeschwindigkeit = 142.0;
        double zugVerbrauch = 12.0;

        Zug testZug = new Zug(zugNummer, ZugTyp.IRE, zugHoechstGeschwindigkeit, zugVerbrauch);

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

        List<Strecke> testStreckenEntitaeten = new ArrayList<>();
        testStreckenEntitaeten.add(testStrecke);
        List<Bahnhof> testBahnhofsEntitaeten = new ArrayList<>();
        testBahnhofsEntitaeten.add(startBahnhof);
        testBahnhofsEntitaeten.add(endBahnhof);

        EasyMock.expect(mockStreckenVerwaltung.holeEntitaeten()).andReturn(testStreckenEntitaeten).once();
        EasyMock.expect(mockBahnhofsVerwaltung.holeEntitaeten()).andReturn(testBahnhofsEntitaeten).once();
        mockWegFinder.initialisiereGraphen(
                EasyMock.isA(StreckenNetz.class)
        );
        EasyMock.expectLastCall().once();

        List<StreckenKante> testKantenListe = new ArrayList<>();
        testKantenListe.add(new StreckenKante(testStrecke, startBahnhofKnoten, endBahnhofKnoten) {
            @Override
            public double holeGewichtung() {
                return 0;
            }
        });


        IExpectationSetters<List<? extends Kante>> expect = EasyMock.expect(
                mockWegFinder.berechneWeg(EasyMock.isA(BahnhofsKnoten.class), EasyMock.isA(BahnhofsKnoten.class))
        );
        expect.andReturn(testKantenListe);
        expect.once();

        EasyMock.replay(mockBahnhofsVerwaltung, mockStreckenVerwaltung, mockWegFinder);

        //Arrange
        StreckenBerechner cut = new StreckenBerechner(mockBahnhofsVerwaltung, mockStreckenVerwaltung, mockWegFinder);

        //Act
        List<Strecke> ergebnis = cut.berechneKuerzesteZeitStrecke(startBahnhof, endBahnhof, testZug);

        //Assert
        Assert.assertEquals(1, ergebnis.size());
        Assert.assertEquals(testStrecke, ergebnis.get(0));

        //Verify
        EasyMock.verify(mockBahnhofsVerwaltung, mockStreckenVerwaltung, mockWegFinder);
    }

}
