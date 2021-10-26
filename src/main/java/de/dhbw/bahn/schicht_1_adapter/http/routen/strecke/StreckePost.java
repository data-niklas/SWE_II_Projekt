package de.dhbw.bahn.schicht_1_adapter.http.routen.strecke;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.http.ressourcen.StreckenRessource;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_3_domaene.Strecke;

import java.util.Map;

public class StreckePost extends StreckeRoute {
    public StreckePost(Serialisierer streckenSerialisierer, EntitaetenAufsicht aufsicht) {
        super(streckenSerialisierer, aufsicht);
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameters) {
        StreckenRessource streckenRessource = this.streckenSerialisierer.deserialisieren(koerper, StreckenRessource.class);
        Strecke strecke = this.konvertierer.konvertiereZu(streckenRessource);
        this.aufsicht.streckeHinzufuegen(strecke);
        return new HttpAntwort(200, "Erfolg", MimeTyp.SCHLICHT);
    }
}
