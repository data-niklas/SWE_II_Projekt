package de.dhbw.bahn.schicht_1_adapter.http;

public class HttpAntwort {
    private final int status;
    private final String koerper;
    private final String koerperTyp;

    public HttpAntwort(int status, String koerper, String koerperTyp) {
        this.status = status;
        this.koerper = koerper;
        this.koerperTyp = koerperTyp;
    }

    public int holeStatus() {
        return status;
    }

    public String holeKoerper() {
        return koerper;
    }

    public String holeKoerperTyp() {
        return koerperTyp;
    }
}
