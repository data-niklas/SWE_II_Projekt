package de.dhbw.bahn.schicht_3_domaene;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class StreckeTest {

    private static Strecke initialisiereStrecke(){
        Set<ZugTyp> zugTypen = new HashSet<>();
        zugTypen.add(ZugTyp.S);
        zugTypen.add(ZugTyp.IC);
        return new Strecke("", 0, 0, zugTypen, false, new Bahnhof("a"), new Bahnhof("b"));
    }

    @Test
    public void holeWerteTest(){
        Strecke cut = initialisiereStrecke();

        Assert.assertEquals("", cut.holeBezeichnung());
        Assert.assertEquals(0, cut.holeLaenge(), Double.MIN_VALUE);
        Assert.assertEquals(0, cut.holeMaximalGeschwindigkeit(), Double.MIN_VALUE);
        Set<ZugTyp> zugTypen = cut.holeErlaubteZugTypen();
        Assert.assertEquals(2, zugTypen.size());
        Assert.assertTrue(zugTypen.contains(ZugTyp.S));
        Assert.assertTrue( zugTypen.contains(ZugTyp.IC));
        Assert.assertTrue(new Bahnhof("a").equals(cut.holeStartBahnhof()));
        Assert.assertTrue(new Bahnhof("b").equals(cut.holeEndBahnhof()));
    }

    @Test
    public void setzeMaximalgeschwindigkeit(){
        Strecke cut = initialisiereStrecke();

        cut.setzeMaximalGeschwindigkeit(42);

        Assert.assertEquals(cut.holeMaximalGeschwindigkeit(), 42, Double.MIN_VALUE);
    }
    @Test
    public void setzeMaximalgeschwindigkeitFehlerhaft(){
        Strecke cut = initialisiereStrecke();
        try {
            cut.setzeMaximalGeschwindigkeit(-1);
            Assert.fail("Maximalgeschwindigkeit darf nicht negativ sein!");
        }
        catch(IllegalArgumentException ignored){

        }
    }

}
