package de.dhbw.bahn.schicht_1_adapter.http.routen.strecke;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;

import java.util.Map;

public class StreckeDelete extends StreckeRoute {

    public StreckeDelete(Serialisierer streckenSerialisierer, EntitaetenAufsicht aufsicht) {
        super(streckenSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameters) {
        if (!parameters.containsKey("id")) {
            return new HttpAntwort(500, "Es muss eine ID übergeben werden", MimeTyp.SCHLICHT);
        }
        String id = parameters.get("id");
        this.aufsicht.streckeLoeschen(id);

        return new HttpAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
