package de.dhbw.bahn.schicht_1_adapter.http;

public enum MimeTyp {
    SCHLICHT("text/plain"),
    JSON("application/json");

    private final String wert;

    MimeTyp(String wert) {
        this.wert = wert;
    }

    public String holeWert() {
        return this.wert;
    }
}
