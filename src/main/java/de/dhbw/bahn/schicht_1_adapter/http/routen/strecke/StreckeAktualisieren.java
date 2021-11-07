package de.dhbw.bahn.schicht_1_adapter.http.routen.strecke;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.http.ressourcen.StreckenRessource;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Strecke;

import java.util.Map;

public class StreckeAktualisieren extends StreckeRoute {

    public StreckeAktualisieren(Serialisierer streckenSerialisierer, EntitaetenAufsicht aufsicht) {
        super(streckenSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameter) {
        if (parameter.containsKey("id")) {
            return new EventAntwort(400, "PUT Aktion nicht mit Parameter moeglich.", MimeTyp.SCHLICHT);
        }
        StreckenRessource streckenRessource = this.streckenSerialisierer.deserialisieren(koerper, StreckenRessource.class);
        Strecke strecke = this.konvertierer.konvertiereZu(streckenRessource);
        this.aufsicht.aktualisiereStrecke(strecke);
        return new EventAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
