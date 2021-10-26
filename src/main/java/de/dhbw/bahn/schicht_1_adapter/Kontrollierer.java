package de.dhbw.bahn.schicht_1_adapter;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAnfragemethode;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.HttpServer;
import de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof.BahnhofDelete;
import de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof.BahnhofGet;
import de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof.BahnhofPost;
import de.dhbw.bahn.schicht_1_adapter.http.routen.strecke.StreckenDelete;
import de.dhbw.bahn.schicht_1_adapter.http.routen.strecke.StreckenGet;
import de.dhbw.bahn.schicht_1_adapter.http.routen.strecke.StreckenPost;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugDelete;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugGet;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugPost;
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
        this.registriereBahnhof();
        this.registriereStrecken();
    }

    private void registriereZug() {
        this.server.registriereHttpRueckruf(new HttpRoute("/zug", HttpAnfragemethode.GET), new ZugGet(this.serialisierer, this.aufsicht));
        this.server.registriereHttpRueckruf(new HttpRoute("/zug", HttpAnfragemethode.POST), new ZugPost(this.serialisierer, this.aufsicht));
        this.server.registriereHttpRueckruf(new HttpRoute("/zug", HttpAnfragemethode.DELETE), new ZugDelete(this.serialisierer, this.aufsicht));
    }

    private void registriereBahnhof() {
        this.server.registriereHttpRueckruf(new HttpRoute("/bahnhof", HttpAnfragemethode.GET), new BahnhofGet(this.serialisierer, this.aufsicht));
        this.server.registriereHttpRueckruf(new HttpRoute("/bahnhof", HttpAnfragemethode.POST), new BahnhofPost(this.serialisierer, this.aufsicht));
        this.server.registriereHttpRueckruf(new HttpRoute("/bahnhof", HttpAnfragemethode.DELETE), new BahnhofDelete(this.serialisierer, this.aufsicht));
    }

    private void registriereStrecken() {
        this.server.registriereHttpRueckruf(new HttpRoute("/strecke", HttpAnfragemethode.GET), new StreckenGet(this.serialisierer, this.aufsicht));
        this.server.registriereHttpRueckruf(new HttpRoute("/strecke", HttpAnfragemethode.POST), new StreckenPost(this.serialisierer, this.aufsicht));
        this.server.registriereHttpRueckruf(new HttpRoute("/strecke", HttpAnfragemethode.DELETE), new StreckenDelete(this.serialisierer, this.aufsicht));
    }

    public void legeLos(String host, int port) {
        this.server.legeLos(host, port);
    }

    public void halteAn() {
        this.server.halteAn();
    }

}
