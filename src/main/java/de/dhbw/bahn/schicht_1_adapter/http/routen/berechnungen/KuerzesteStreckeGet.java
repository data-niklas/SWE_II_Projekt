package de.dhbw.bahn.schicht_1_adapter.http.routen.berechnungen;

import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.KuerzesterWegeFinder;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.GraphStrecke;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.stream.Collectors;

public class KuerzesteStreckeGet extends StreckenBerechnungRoute {

    public KuerzesteStreckeGet(Serialisierer serialisierer, EntitaetenAufsicht aufsicht, KuerzesterWegeFinder kuerzesterWegeFinder) {
        super(serialisierer, aufsicht, kuerzesterWegeFinder);
    }

    @Override
    protected List<Strecke> berechne(Bahnhof startBahnhof, Bahnhof endBahnhof, Zug zug) {
        List<GraphStrecke> graphStrecken = this.streckenBerechner.berechneKuerzesteStrecke(startBahnhof, endBahnhof, zug);
        return graphStrecken.stream().map(GraphStrecke::holeStrecke).collect(Collectors.toList());
    }

}
