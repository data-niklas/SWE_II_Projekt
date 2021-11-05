package de.dhbw.bahn.schicht_2_anwendung;

import de.dhbw.bahn.schicht_2_anwendung.wegfinder.GraphStrecke;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenNetz;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.ZugTyp;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class StreckenNetzTest {

    private static StreckenNetz initialisiereStreckenNetz() {
        return new StreckenNetz();
    }

    private static Strecke initialisiereStrecke() {
        Set<ZugTyp> zugTypen = new HashSet<>();
        zugTypen.add(ZugTyp.S);
        zugTypen.add(ZugTyp.IC);
        return new Strecke("", 0, 0, zugTypen, false, new Bahnhof("a"), new Bahnhof("b"));
    }

    private static GraphStrecke initialisiereGraphStrecke() {
        return new GraphStrecke(initialisiereStrecke()) {
            @Override
            public double holeGewichtung() {
                return 42;
            }
        };
    }

    private static void fuegeKnotenHinzu(StreckenNetz netz) {
        netz.bahnhofHinzufuegen(new Bahnhof("a"));
        netz.bahnhofHinzufuegen(new Bahnhof("b"));
    }

    @Test
    public void holeWerteTest() {
        StreckenNetz netz = initialisiereStreckenNetz();

        Assert.assertEquals(0, netz.holeKanten().size());
        Assert.assertEquals(0, netz.holeKnoten().size());
    }

    @Test
    public void neuerBahnhof() {
        StreckenNetz netz = initialisiereStreckenNetz();

        try {
            netz.bahnhofHinzufuegen(new Bahnhof("a"));
        } catch (Error e) {
            Assert.fail();
        }
        Assert.assertTrue(netz.sucheBahnhof("a").isPresent());
    }

    @Test
    public void neuerBahnhofDuplikat() {
        StreckenNetz netz = initialisiereStreckenNetz();

        try {
            netz.bahnhofHinzufuegen(new Bahnhof("a"));
        } catch (Error e) {
            Assert.fail();
        }
        try {
            netz.bahnhofHinzufuegen(new Bahnhof("a"));
            Assert.fail();
        } catch (DuplikatFehler ignored) {
        }
    }

    @Test
    public void neueStrecke() {
        StreckenNetz netz = initialisiereStreckenNetz();

        fuegeKnotenHinzu(netz);

        try {
            netz.streckeHinzufuegen(initialisiereGraphStrecke());
        } catch (Error e) {
            Assert.fail();
        }
    }

}
