package de.dhbw.bahn.schicht_3_domaene;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class StreckeTest {

    private static Bahnhof testStartBahnhof;
    private static Bahnhof testEndBahnhof;

    static {

        testStartBahnhof = new Bahnhof("a");
        testEndBahnhof = new Bahnhof("b");
    }

    public static Strecke testStrecke(){
        Set<ZugTyp> zugTypen = new HashSet<>();
        zugTypen.add(ZugTyp.S);
        zugTypen.add(ZugTyp.IC);
        return new Strecke("", 0, 0, zugTypen, false, testStartBahnhof, testEndBahnhof);
    }



    @Test
    public void holeWerteTest() {
        Strecke cut = testStrecke();

        Assert.assertEquals("", cut.holeBezeichnung());
        Assert.assertEquals(0, cut.holeLaenge(), Double.MIN_VALUE);
        Assert.assertEquals(0, cut.holeMaximalGeschwindigkeit(), Double.MIN_VALUE);
        Set<ZugTyp> zugTypen = cut.holeErlaubteZugTypen();
        Assert.assertEquals(2, zugTypen.size());
        Assert.assertTrue(zugTypen.contains(ZugTyp.S));
        Assert.assertTrue(zugTypen.contains(ZugTyp.IC));
        Assert.assertTrue(testStartBahnhof.equals(cut.holeStartBahnhof()));
        Assert.assertTrue(testEndBahnhof.equals(cut.holeEndBahnhof()));
    }

    @Test
    public void setzeMaximalgeschwindigkeit() {
        Strecke cut = testStrecke();

        cut.setzeMaximalGeschwindigkeit(42);

        Assert.assertEquals(cut.holeMaximalGeschwindigkeit(), 42, Double.MIN_VALUE);
    }

    @Test
    public void setzeMaximalgeschwindigkeitFehlerhaft() {
        Strecke cut = testStrecke();
        try {
            cut.setzeMaximalGeschwindigkeit(-1);
            Assert.fail("Maximalgeschwindigkeit darf nicht negativ sein!");
        } catch (IllegalArgumentException ignored) {

        }
    }

}
