package de.dhbw.bahn.schicht_3_domaene;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

import java.util.Objects;

public class Zug implements Identifizierbar {

    private final int zugNummer;
    private final ZugTyp zugTyp;
    private double hoechstGeschwindigkeit;
    private double verbrauch; // Verbrauch an Kraftstoff pro 100 km

    public Zug(int zugNummer, ZugTyp zugTyp, double hoechstGeschwindigkeit, double verbrauch) {
        this.zugNummer = zugNummer;
        this.zugTyp = zugTyp;
        this.setzeHoechstGeschwindigkeit(hoechstGeschwindigkeit);
        this.setzeVerbrauch(verbrauch);
    }

    @Override
    public String holeIdentifizierer() {
        return Integer.toString(this.zugNummer);
    }

    public int holeZugNummer() {
        return zugNummer;
    }

    public ZugTyp holeZugTyp() {
        return zugTyp;
    }

    public double holeHoechstGeschwindigkeit() {
        return hoechstGeschwindigkeit;
    }

    public double holeVerbrauch() {
        return verbrauch;
    }

    public void setzeHoechstGeschwindigkeit(double hoechstGeschwindigkeit) {
        if (hoechstGeschwindigkeit < 0)
            throw new IllegalArgumentException("Die Hoechstgeschwindigkeit muss positiv sein.");
        this.hoechstGeschwindigkeit = hoechstGeschwindigkeit;
    }

    public void setzeVerbrauch(double verbrauch) {
        if (verbrauch < 0)
            throw new IllegalArgumentException("Der Verbrauch muss positiv sein.");
        this.verbrauch = verbrauch;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zug)) return false;
        Zug zug = (Zug) o;
        return Objects.equals(holeIdentifizierer(), zug.holeIdentifizierer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(holeIdentifizierer());
    }
}
