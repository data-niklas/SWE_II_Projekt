package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.Map;

public class ZugErstellen extends ZugRoute {
    public ZugErstellen(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        super(zugSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameters) {
        Zug zug = this.zugSerialisierer.deserialisieren(koerper, Zug.class);
        this.aufsicht.zugHinzufuegen(zug);
        return new EventAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
