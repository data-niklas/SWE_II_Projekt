package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;

import java.util.Map;

public class BahnhofLoeschen extends BahnhofRoute {
    public BahnhofLoeschen(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        super(bahnhofSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameters) {
        if (!parameters.containsKey("id")) {
            return new EventAntwort(500, "Es muss eine ID übergeben werden", MimeTyp.SCHLICHT);
        }
        String bahnhofBezeichnung = parameters.get("id");
        this.aufsicht.bahnhofLoeschen(bahnhofBezeichnung);

        return new EventAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
