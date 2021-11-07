package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.Map;

public class ZugLesen extends ZugRoute {

    public ZugLesen(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        super(zugSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameter) {
        String antwort;
        if (parameter.containsKey("id")) {
            String zugId = parameter.get("id");
            Zug zug = this.aufsicht.holeZug(zugId);
            if (zug == null) {
                return new EventAntwort(404, "Zug mit dieser ID nicht gefunden", MimeTyp.SCHLICHT);
            }
            antwort = this.zugSerialisierer.serialisieren(zug);
        } else {
            List<Zug> zuege = this.aufsicht.holeZuege();
            antwort = this.zugSerialisierer.serialisieren(zuege);
        }

        return new EventAntwort(200, antwort, MimeTyp.JSON);
    }

}
