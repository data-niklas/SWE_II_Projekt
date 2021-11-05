package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;

import java.util.Map;

public class BahnhofPut extends BahnhofRoute {

    public BahnhofPut(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        super(bahnhofSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameters) {
        Bahnhof bahnhof = this.bahnhofSerialisierer.deserialisieren(koerper, Bahnhof.class);
        this.aufsicht.aktualisiereBahnhof(bahnhof);
        return new HttpAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
