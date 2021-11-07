package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;

import java.util.List;
import java.util.Map;

public class BahnhofLesen extends BahnhofRoute {
    public BahnhofLesen(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        super(bahnhofSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameter) {
        String antwort;
        if (parameter.containsKey("id")) {
            String bahnhofName = parameter.get("id");
            Bahnhof bahnhof = this.aufsicht.holeBahnhof(bahnhofName);
            antwort = this.bahnhofSerialisierer.serialisieren(bahnhof);
        } else {
            List<Bahnhof> bahnhoefe = this.aufsicht.holeBahnhoefe();
            antwort = this.bahnhofSerialisierer.serialisieren(bahnhoefe);
        }

        return new EventAntwort(200, antwort, MimeTyp.JSON);
    }

}
