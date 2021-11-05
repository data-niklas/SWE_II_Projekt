package de.dhbw.bahn.schicht_3_domaene;

import org.junit.Assert;
import org.junit.Test;

public class ZugTest {

    private static Zug initialisiereZug() {
        return new Zug(0, ZugTyp.IC, 0, 0);
    }


    @Test
    public void holeWerteTest() {
        Zug cut = initialisiereZug();

        Assert.assertEquals(0, cut.holeZugNummer());
        Assert.assertEquals(ZugTyp.IC, cut.holeZugTyp());
        Assert.assertEquals(0, cut.holeHoechstGeschwindigkeit(), Double.MIN_VALUE);
        Assert.assertEquals(0, cut.holeVerbrauch(), Double.MIN_VALUE);
    }

    @Test
    public void setzeVerbrauch() {
        Zug cut = initialisiereZug();

        cut.setzeVerbrauch(42);

        Assert.assertEquals(42, cut.holeVerbrauch(), Double.MIN_VALUE);

    }

    @Test
    public void setzeHoechstGeschwindigkeit() {
        Zug cut = initialisiereZug();

        cut.setzeHoechstGeschwindigkeit(42);

        Assert.assertEquals(42, cut.holeHoechstGeschwindigkeit(), Double.MIN_VALUE);
    }

    @Test
    public void setzeVerbrauchFehlerhaft() {
        Zug cut = initialisiereZug();

        try {
            cut.setzeVerbrauch(-1);
            Assert.fail("Verbrauch darf nicht negativ sein!");
        } catch (IllegalArgumentException ignored) {

        }


    }

    @Test
    public void setzeHoechstGeschwindigkeitFehlerhaft() {
        Zug cut = initialisiereZug();

        try {
            cut.setzeHoechstGeschwindigkeit(-1);
            Assert.fail("Hoechstgeschwindigkeit darf nicht negativ sein!");
        } catch (IllegalArgumentException ignored) {

        }
    }

}
