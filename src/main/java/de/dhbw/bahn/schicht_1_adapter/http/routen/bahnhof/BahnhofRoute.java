package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.HttpRueckruf;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;

public abstract class BahnhofRoute implements HttpRueckruf {
    protected final Serialisierer bahnhofSerialisierer;
    protected final EntitaetenAufsicht aufsicht;

    protected BahnhofRoute(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        this.bahnhofSerialisierer = bahnhofSerialisierer;
        this.aufsicht = aufsicht;
    }
}
