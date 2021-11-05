package de.dhbw.bahn.schicht_3_domaene;

import org.junit.Assert;
import org.junit.Test;

public class BahnhofTest {

    private static Bahnhof initialisiereBahnhof() {
        return new Bahnhof("");
    }

    @Test
    public void holeWerteTest() {
        Bahnhof cut = initialisiereBahnhof();

        Assert.assertEquals("", cut.holeName());
        Assert.assertEquals("", cut.holeIdentifizierer());
    }

    @Test
    public void bahnhofVergleich() {
        Bahnhof cut = initialisiereBahnhof();

        Assert.assertTrue(cut.equals(initialisiereBahnhof()));
        Assert.assertFalse(cut.equals(new Bahnhof("asoödijascoijasd")));
    }

}
