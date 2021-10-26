package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.Map;

public class ZugPut extends ZugRoute {

    public ZugPut(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        super(zugSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameter) {
        if (parameter.containsKey("id")) {
            return new HttpAntwort(400, "PUT Aktion nicht mit Parameter moeglich.", MimeTyp.SCHLICHT);
        }
        Zug zug = this.zugSerialisierer.deserialisieren(koerper, Zug.class);
        this.aufsicht.aktualisiereZug(zug);
        return new HttpAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
