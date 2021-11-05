package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.Map;

public class ZugGet extends ZugRoute {

    public ZugGet(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        super(zugSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameter) {
        String antwort;
        if (parameter.containsKey("id")) {
            String zugId = parameter.get("id");
            Zug zug = this.aufsicht.holeZug(zugId);
            if (zug == null) {
                return new HttpAntwort(404, "Zug mit dieser ID nicht gefunden", MimeTyp.SCHLICHT);
            }
            antwort = this.zugSerialisierer.serialisieren(zug);
        } else {
            List<Zug> zuege = this.aufsicht.holeZuege();
            antwort = this.zugSerialisierer.serialisieren(zuege);
        }

        return new HttpAntwort(200, antwort, MimeTyp.JSON);
    }

}
