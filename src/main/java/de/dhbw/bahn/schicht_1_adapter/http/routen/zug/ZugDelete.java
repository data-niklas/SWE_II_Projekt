package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;

import java.util.Map;

public class ZugDelete extends ZugRoute {
    public ZugDelete(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        super(zugSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameters) {
        if (!parameters.containsKey("id")) {
            return new HttpAntwort(500, "Es muss eine ID übergeben werden", MimeTyp.SCHLICHT);
        }
        String zugNummer = parameters.get("id");
        this.aufsicht.zugLoeschen(zugNummer);

        return new HttpAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
