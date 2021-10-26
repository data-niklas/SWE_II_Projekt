package de.dhbw.bahn.schicht_1_adapter;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAnfragemethode;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.HttpServer;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugGet;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;

public class Kontrollierer {

    private final HttpServer server;
    private final Serialisierer serialisierer;
    private final EntitaetenAufsicht aufsicht;

    public Kontrollierer(HttpServer server, Serialisierer serialisierer, EntitaetenAufsicht aufsicht) {
        this.server = server;
        this.serialisierer = serialisierer;
        this.aufsicht = aufsicht;
        this.registriereRouten();
    }

    private void registriereRouten() {
        this.registriereZug();
    }

    private void registriereZug() {
        this.server.registriereHttpRueckruf(new HttpRoute("/zug", HttpAnfragemethode.GET), new ZugGet(this.serialisierer, this.aufsicht));
    }

    public void legeLos(String host, int port) {
        this.server.legeLos(host, port);
    }

    public void halteAn() {
        this.server.halteAn();
    }

}
