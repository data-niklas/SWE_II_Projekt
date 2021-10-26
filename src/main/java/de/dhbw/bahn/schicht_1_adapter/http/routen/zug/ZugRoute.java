package de.dhbw.bahn.schicht_1_adapter.http.routen.zug;

import de.dhbw.bahn.schicht_1_adapter.http.HttpRueckruf;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;

public abstract class ZugRoute implements HttpRueckruf {
    protected final Serialisierer zugSerialisierer;
    protected final EntitaetenAufsicht aufsicht;

    protected ZugRoute(Serialisierer zugSerialisierer, EntitaetenAufsicht aufsicht) {
        this.zugSerialisierer = zugSerialisierer;
        this.aufsicht = aufsicht;
    }
}
