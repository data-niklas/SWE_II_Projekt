package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.stream.Collectors;

public class StreckenBerechner {

    private final Verwaltung<Bahnhof> bahnhofsVerwaltung;
    private final Verwaltung<Strecke> streckenVerwaltung;
    private final WegFinder wegFinder;

    public StreckenBerechner(Verwaltung<Bahnhof> bahnhofsVerwaltung, Verwaltung<Strecke> streckenVerwaltung, WegFinder wegFinder) {
        this.bahnhofsVerwaltung = bahnhofsVerwaltung;
        this.streckenVerwaltung = streckenVerwaltung;
        this.wegFinder = wegFinder;
    }

    public List<Strecke> berechneKuerzesteStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteStreckeNetz(zug);
        this.wegFinder.initialisiereGraphen(streckenNetz);
        BahnhofsKnoten startKnoten = new BahnhofsKnoten(start);
        BahnhofsKnoten endKnoten = new BahnhofsKnoten(ende);
        List<StreckenKante> weg = (List<StreckenKante>) this.wegFinder.berechneWeg(startKnoten, endKnoten);
        return weg.stream().map(StreckenKante::holeStrecke).collect(Collectors.toList());
    }

    public List<Strecke> berechneKuerzesteZeitStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteZeitNetz(zug);
        this.wegFinder.initialisiereGraphen(streckenNetz);
        BahnhofsKnoten startKnoten = new BahnhofsKnoten(start);
        BahnhofsKnoten endKnoten = new BahnhofsKnoten(ende);
        List<StreckenKante> weg = (List<StreckenKante>) this.wegFinder.berechneWeg(startKnoten, endKnoten);
        return weg.stream().map(StreckenKante::holeStrecke).collect(Collectors.toList());
    }

    private StreckenNetz baueKuerzesteStreckeNetz(Zug zug) {
        StreckenNetz netz = new StreckenNetz();
        for (Bahnhof bahnhof : bahnhofsVerwaltung.holeEntitaeten()) {
            netz.bahnhofHinzufuegen(new BahnhofsKnoten(bahnhof));
        }
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp())) {
                continue;
            }
            BahnhofsKnoten start = new BahnhofsKnoten(strecke.holeStartBahnhof());
            BahnhofsKnoten ende = new BahnhofsKnoten(strecke.holeEndBahnhof());
            StreckenKante streckeGraph = new StreckenKante(strecke, start, ende) {
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
            netz.bahnhofHinzufuegen(new BahnhofsKnoten(bahnhof));
        }
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp())) {
                continue;
            }
            BahnhofsKnoten start = new BahnhofsKnoten(strecke.holeStartBahnhof());
            BahnhofsKnoten ende = new BahnhofsKnoten(strecke.holeEndBahnhof());
            StreckenKante streckeGraph = new StreckenKante(strecke, start, ende) {
                @Override
                public double holeGewichtung() {
                    double fahrGeschwindigkeit = Math.min(zug.holeHoechstGeschwindigkeit(), this.holeStrecke().holeMaximalGeschwindigkeit());
                    if (fahrGeschwindigkeit == 0)
                        return Double.MAX_VALUE;
                    return this.holeStrecke().holeLaenge() / fahrGeschwindigkeit;
                }
            };
            netz.streckeHinzufuegen(streckeGraph);
        }
        return netz;
    }

}
