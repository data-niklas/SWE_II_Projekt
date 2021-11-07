package de.dhbw.bahn.schicht_1_adapter.http.routen.bahnhof;

import de.dhbw.bahn.schicht_1_adapter.http.EventRueckruf;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;

public abstract class BahnhofRoute implements EventRueckruf {
    protected final Serialisierer bahnhofSerialisierer;
    protected final EntitaetenAufsicht aufsicht;

    protected BahnhofRoute(Serialisierer bahnhofSerialisierer, EntitaetenAufsicht aufsicht) {
        this.bahnhofSerialisierer = bahnhofSerialisierer;
        this.aufsicht = aufsicht;
    }
}
