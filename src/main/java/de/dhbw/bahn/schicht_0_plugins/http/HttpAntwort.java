package de.dhbw.bahn.schicht_0_plugins.http;

public class HttpAntwort {
    private int status;
    private String koerper;
    private String koerperTyp;

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
