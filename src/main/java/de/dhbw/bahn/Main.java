package de.dhbw.bahn;

import de.dhbw.bahn.schicht_0_plugins.algorithmen.Dijkstra;
import de.dhbw.bahn.schicht_0_plugins.http.EinfacherHttpServer;
import de.dhbw.bahn.schicht_0_plugins.persistenz.TemporaereVerwaltung;
import de.dhbw.bahn.schicht_0_plugins.serialisierer.GsonSerialisierer;
import de.dhbw.bahn.schicht_1_adapter.Kontrollierer;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.WegFinder;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> konfiguration = new HashMap<>();
        konfiguration.put("host", "localhost");
        konfiguration.put("port", "8081");

        EinfacherHttpServer server = new EinfacherHttpServer();
        Serialisierer serialisierer = new GsonSerialisierer();
        Verwaltung<Bahnhof> bahnhofVerwaltung = new TemporaereVerwaltung<>();
        Verwaltung<Strecke> streckenVerwaltung = new TemporaereVerwaltung<>();
        Verwaltung<Zug> zugVerwaltung = new TemporaereVerwaltung<>();

        EntitaetenAufsicht aufsicht = new EntitaetenAufsicht(bahnhofVerwaltung, streckenVerwaltung, zugVerwaltung);

        WegFinder wegeFinder = new Dijkstra();

        Kontrollierer kontrollierer = new Kontrollierer(server, serialisierer, aufsicht, wegeFinder);
        server.legeLos(konfiguration);
    }
}
