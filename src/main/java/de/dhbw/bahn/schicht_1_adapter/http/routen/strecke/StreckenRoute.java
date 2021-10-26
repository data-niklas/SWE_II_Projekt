package de.dhbw.bahn.schicht_1_adapter.http.routen.strecke;

import de.dhbw.bahn.schicht_1_adapter.http.HttpRueckruf;
import de.dhbw.bahn.schicht_1_adapter.http.ressourcen.StreckenRessourcenKonvertierer;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;

public abstract class StreckenRoute implements HttpRueckruf {
    protected final Serialisierer streckenSerialisierer;
    protected final EntitaetenAufsicht aufsicht;
    protected final StreckenRessourcenKonvertierer konvertierer;

    protected StreckenRoute(Serialisierer streckenSerialisierer, EntitaetenAufsicht aufsicht) {
        this.streckenSerialisierer = streckenSerialisierer;
        this.aufsicht = aufsicht;
        this.konvertierer = new StreckenRessourcenKonvertierer(aufsicht.holeBahnhofVerwaltung());
    }
}
