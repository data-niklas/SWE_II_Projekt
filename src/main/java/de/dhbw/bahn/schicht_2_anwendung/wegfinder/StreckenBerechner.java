package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;

public class StreckenBerechner {

    private final Verwaltung<Bahnhof> bahnhofsVerwaltung;
    private final Verwaltung<Strecke> streckenVerwaltung;
    private final WegFinder wegFinder;

    public StreckenBerechner(Verwaltung<Bahnhof> bahnhofsVerwaltung, Verwaltung<Strecke> streckenVerwaltung, WegFinder wegFinder) {
        this.bahnhofsVerwaltung = bahnhofsVerwaltung;
        this.streckenVerwaltung = streckenVerwaltung;
        this.wegFinder = wegFinder;
    }

    public List<GraphStrecke> berechneKuerzesteStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteStreckeNetz(zug);
        this.wegFinder.initialisiereGraphen(streckenNetz);
        return (List<GraphStrecke>) this.wegFinder.berechneWeg(start, ende);
    }

    public List<GraphStrecke> berechneKuerzesteZeitStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteZeitNetz(zug);
        this.wegFinder.initialisiereGraphen(streckenNetz);
        return (List<GraphStrecke>) this.wegFinder.berechneWeg(start, ende);
    }

    private StreckenNetz baueKuerzesteStreckeNetz(Zug zug) {
        StreckenNetz netz = new StreckenNetz();
        for (Bahnhof bahnhof : bahnhofsVerwaltung.holeEntitaeten()) {
            netz.bahnhofHinzufuegen(bahnhof);
        }
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp())) {
                continue;
            }

            GraphStrecke streckeGraph = new GraphStrecke(strecke) {
                @Override
                public double holeGewichtung() {
                    return this.holeStrecke().holeLaenge();
                }
            };
            netz.streckeHinzufuegen(streckeGraph);
        }
        return netz;
    }

    private StreckenNetz baueKuerzesteZeitNetz(final Zug zug) {
        StreckenNetz netz = new StreckenNetz();
        for (Bahnhof bahnhof : bahnhofsVerwaltung.holeEntitaeten()) {
            netz.bahnhofHinzufuegen(bahnhof);
        }
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp())) {
                continue;
            }

            GraphStrecke streckeGraph = new GraphStrecke(strecke) {
                @Override
                public double holeGewichtung() {
                    double fahrGeschwindigkeit = Math.min(zug.holeHoechstGeschwindigkeit(), this.holeStrecke().holeMaximalGeschwindigkeit());
                    double zeitGewichtung = this.holeStrecke().holeLaenge() / fahrGeschwindigkeit;
                    return zeitGewichtung;
                }
            };
            netz.streckeHinzufuegen(streckeGraph);
        }
        return netz;
    }

}