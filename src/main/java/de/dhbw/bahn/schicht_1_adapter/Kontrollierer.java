package de.dhbw.bahn.schicht_1_adapter;

import de.dhbw.bahn.schicht_1_adapter.http.EventTyp;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.Darstellung;
import de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof.BahnhofLoeschen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof.BahnhofLesen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof.BahnhofErstellen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.berechnungen.KuerzesteStreckeLesen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.berechnungen.SchnellsteStreckeLesen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.strecke.StreckeLoeschen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.strecke.StreckeLesen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.strecke.StreckeErstellen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugLoeschen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugLesen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugErstellen;
import de.dhbw.bahn.schicht_1_adapter.http.routen.zug.ZugAktualisieren;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.WegFinder;

import java.util.Map;

public class Kontrollierer {

    private final Darstellung server;
    private final Serialisierer serialisierer;
    private final EntitaetenAufsicht aufsicht;
    private final WegFinder wegFinder;

    public Kontrollierer(Darstellung server, Serialisierer serialisierer, EntitaetenAufsicht aufsicht, WegFinder wegFinder) {
        this.server = server;
        this.serialisierer = serialisierer;
        this.aufsicht = aufsicht;
        this.wegFinder = wegFinder;
        this.registriereRouten();
    }

    private void registriereRouten() {
        this.registriereZug();
        this.registriereBahnhof();
        this.registriereStrecken();
        this.registriereBerechnungen();
    }

    private void registriereZug() {
        this.server.registriereEventRueckruf(new Event("/zug", EventTyp.LESEN), new ZugLesen(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/zug", EventTyp.ERSTELLEN), new ZugErstellen(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/zug", EventTyp.AKTUALISIEREN), new ZugAktualisieren(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/zug", EventTyp.LOESCHEN), new ZugLoeschen(this.serialisierer, this.aufsicht));
    }

    private void registriereBahnhof() {
        this.server.registriereEventRueckruf(new Event("/bahnhof", EventTyp.LESEN), new BahnhofLesen(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/bahnhof", EventTyp.ERSTELLEN), new BahnhofErstellen(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/bahnhof", EventTyp.LOESCHEN), new BahnhofLoeschen(this.serialisierer, this.aufsicht));
    }

    private void registriereStrecken() {
        this.server.registriereEventRueckruf(new Event("/strecke", EventTyp.LESEN), new StreckeLesen(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/strecke", EventTyp.ERSTELLEN), new StreckeErstellen(this.serialisierer, this.aufsicht));
        this.server.registriereEventRueckruf(new Event("/strecke", EventTyp.LOESCHEN), new StreckeLoeschen(this.serialisierer, this.aufsicht));
    }

    private void registriereBerechnungen() {
        this.server.registriereEventRueckruf(new Event("/kuerzester-weg", EventTyp.LESEN), new KuerzesteStreckeLesen(this.serialisierer, this.aufsicht, this.wegFinder));
        this.server.registriereEventRueckruf(new Event("/schnellster-weg", EventTyp.LESEN), new SchnellsteStreckeLesen(this.serialisierer, this.aufsicht, this.wegFinder));
    }

    public void legeLos(Map<String, String> konfiguration) {
        this.server.legeLos(konfiguration);
    }

    public void halteAn() {
        this.server.halteAn();
    }

}
