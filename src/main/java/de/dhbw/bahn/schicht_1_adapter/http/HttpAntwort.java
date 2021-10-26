package de.dhbw.bahn.schicht_1_adapter.http;

public class HttpAntwort {
    private final int status;
    private final String koerper;
    private final MimeTyp koerperTyp;

    public HttpAntwort(int status, String koerper, MimeTyp koerperTyp) {
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

    public MimeTyp holeKoerperTyp() {
        return koerperTyp;
    }
}
