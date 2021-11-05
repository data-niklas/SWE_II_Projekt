package de.dhbw.bahn.schicht_1_adapter.http.routen.berechnungen;

import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.WegFinder;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.GraphStrecke;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.stream.Collectors;

public class SchnellsteStreckeGet extends StreckenBerechnungRoute {

    public SchnellsteStreckeGet(Serialisierer serialisierer, EntitaetenAufsicht aufsicht, WegFinder wegFinder) {
        super(serialisierer, aufsicht, wegFinder);
    }

    @Override
    protected List<Strecke> berechne(Bahnhof startBahnhof, Bahnhof endBahnhof, Zug zug) {
        List<GraphStrecke> graphStrecken = this.streckenBerechner.berechneKuerzesteZeitStrecke(startBahnhof, endBahnhof, zug);
        return graphStrecken.stream().map(GraphStrecke::holeStrecke).collect(Collectors.toList());
    }
}
