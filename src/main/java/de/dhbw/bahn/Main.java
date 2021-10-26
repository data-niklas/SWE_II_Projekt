package de.dhbw.bahn;

import de.dhbw.bahn.schicht_0_plugins.http.EinfacherHttpServer;
import de.dhbw.bahn.schicht_0_plugins.persistierung.TemporaereVerwaltung;
import de.dhbw.bahn.schicht_0_plugins.serialisierer.GsonSerialisierer;
import de.dhbw.bahn.schicht_1_adapter.Kontrollierer;
import de.dhbw.bahn.schicht_1_adapter.http.HttpServer;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.Verwaltung;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

public class Main {

    public static void main(String[] args) {
        HttpServer server = new EinfacherHttpServer();
        Serialisierer serialisierer = new GsonSerialisierer();
        Verwaltung<Bahnhof> bahnhofVerwaltung = new TemporaereVerwaltung<>();
        Verwaltung<Strecke> streckenVerwaltung = new TemporaereVerwaltung<>();
        Verwaltung<Zug> zugVerwaltung = new TemporaereVerwaltung<>();

        EntitaetenAufsicht aufsicht = new EntitaetenAufsicht(bahnhofVerwaltung, streckenVerwaltung, zugVerwaltung);

        Kontrollierer kontrollierer = new Kontrollierer(server, serialisierer, aufsicht);
        kontrollierer.legeLos("localhost", 8080);

    }
}
