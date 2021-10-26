package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;

import java.util.Map;

public class BahnhofDelete extends BahnhofRoute {
    public BahnhofDelete(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        super(bahnhofSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameters) {
        if (!parameters.containsKey("id")) {
            return new HttpAntwort(500, "Es muss eine ID übergeben werden", MimeTyp.SCHLICHT);
        }
        String bahnhofBezeichnung = parameters.get("id");
        this.aufsicht.bahnhofLoeschen(bahnhofBezeichnung);

        return new HttpAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
