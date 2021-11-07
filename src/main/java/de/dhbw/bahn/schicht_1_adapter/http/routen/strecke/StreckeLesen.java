package de.dhbw.bahn.schicht_1_adapter.http.routen.strecke;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.http.ressourcen.StreckenRessource;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Strecke;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreckeLesen extends StreckeRoute {
    public StreckeLesen(Serialisierer streckenSerialisierer, EntitaetenAufsicht aufsicht) {
        super(streckenSerialisierer, aufsicht);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameter) {
        String antwort;
        if (parameter.containsKey("id")) {
            String id = parameter.get("id");
            Strecke strecke = this.aufsicht.holeStrecke(id);
            StreckenRessource streckenRessource = this.konvertierer.konvertiereVon(strecke);
            antwort = this.streckenSerialisierer.serialisieren(streckenRessource);
        } else {
            List<Strecke> strecken = this.aufsicht.holeStrecken();
            List<StreckenRessource> streckenRessourcen = strecken.stream().map(konvertierer::konvertiereVon).collect(Collectors.toList());
            antwort = this.streckenSerialisierer.serialisieren(streckenRessourcen);
        }

        return new EventAntwort(200, antwort, MimeTyp.JSON);
    }
}
