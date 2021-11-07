package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;

import java.util.Map;

public class BahnhofErstellen extends BahnhofRoute {
    public BahnhofErstellen(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        super(bahnhofSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameters) {
        Bahnhof bahnhof = this.bahnhofSerialisierer.deserialisieren(koerper, Bahnhof.class);
        this.aufsicht.bahnhofHinzufuegen(bahnhof);
        return new EventAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
