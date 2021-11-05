package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;

import java.util.List;
import java.util.Map;

public class BahnhofGet extends BahnhofRoute {
    public BahnhofGet(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        super(bahnhofSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameter) {
        String antwort;
        if (parameter.containsKey("id")) {
            String bahnhofName = parameter.get("id");
            Bahnhof bahnhof = this.aufsicht.holeBahnhof(bahnhofName);
            antwort = this.bahnhofSerialisierer.serialisieren(bahnhof);
        } else {
            List<Bahnhof> bahnhoefe = this.aufsicht.holeBahnhoefe();
            antwort = this.bahnhofSerialisierer.serialisieren(bahnhoefe);
        }

        return new HttpAntwort(200, antwort, MimeTyp.JSON);
    }

}
