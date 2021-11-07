package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.Map;

public class ZugAktualisieren extends ZugRoute {

    public ZugAktualisieren(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        super(zugSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameter) {
        if (parameter.containsKey("id")) {
            return new EventAntwort(400, "PUT Aktion nicht mit Parameter moeglich.", MimeTyp.SCHLICHT);
        }
        Zug zug = this.zugSerialisierer.deserialisieren(koerper, Zug.class);
        this.aufsicht.aktualisiereZug(zug);
        return new EventAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
