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

    public List<Strecke> berechneStrecke(Bahnhof start, Bahnhof ende, StreckenNetz streckenNetz) {
        this.wegFinder.initialisiereGraphen(streckenNetz);
        BahnhofsKnoten startKnoten = new BahnhofsKnoten(start);
        BahnhofsKnoten endKnoten = new BahnhofsKnoten(ende);
        List<StreckenKante> weg = (List<StreckenKante>) this.wegFinder.berechneWeg(startKnoten, endKnoten);
        return weg.stream().map(StreckenKante::holeStrecke).collect(Collectors.toList());
    }

    public List<Strecke> berechneKuerzesteStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteStreckenNetz(zug);
        return this.berechneStrecke(start, ende, streckenNetz);
    }

    public List<Strecke> berechneKuerzesteZeitStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteZeitNetz(zug);
        return this.berechneStrecke(start, ende, streckenNetz);
    }

    private void fuegeStreckeZuStreckenNetzHinzu(StreckenNetz netz, Strecke strecke, final StreckenKantenGenerierer streckenKantenGenerierer){
        final BahnhofsKnoten start = new BahnhofsKnoten(strecke.holeStartBahnhof());
        final BahnhofsKnoten ende = new BahnhofsKnoten(strecke.holeEndBahnhof());
        StreckenKante streckeGraph = streckenKantenGenerierer.generiereStreckenKante(strecke, start, ende);
        netz.streckeHinzufuegen(streckeGraph);
    }

    private void fuegeKantenZuStreckenNetzHinzu(StreckenNetz netz, final Zug zug, final StreckenKantenGenerierer streckenKantenGenerierer){
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp()) || strecke.holeMaximalGeschwindigkeit() == 0) {
                continue;
            }
            fuegeStreckeZuStreckenNetzHinzu(netz, strecke, streckenKantenGenerierer);
        }
    }

    private void fuegeBahnhoefeZuStreckenNetzHinzu(StreckenNetz netz){
        for (Bahnhof bahnhof : bahnhofsVerwaltung.holeEntitaeten()) {
            netz.bahnhofHinzufuegen(new BahnhofsKnoten(bahnhof));
        }
    }

    private StreckenNetz baueStreckenNetz(final Zug zug, final StreckenKantenGenerierer streckenKantenGenerierer) {
        StreckenNetz netz = new StreckenNetz();
        fuegeBahnhoefeZuStreckenNetzHinzu(netz);
        if (zug.holeHoechstGeschwindigkeit() == 0){
            return netz;
        }
        fuegeKantenZuStreckenNetzHinzu(netz, zug, streckenKantenGenerierer);
        return netz;
    }

    private StreckenNetz baueKuerzesteStreckenNetz(Zug zug) {
        StreckenKantenGenerierer streckenKantenGenerierer = new StreckenKantenGenerierer() {
            @Override
            public StreckenKante generiereStreckenKante(Strecke strecke, BahnhofsKnoten startBahnhof, BahnhofsKnoten endBahnhof) {
                return new StreckenKante(strecke, startBahnhof, endBahnhof) {
                    @Override
                    public double holeGewichtung() {
                        return this.holeStrecke().holeLaenge();
                    }
                };
            }
        };
        return baueStreckenNetz(zug, streckenKantenGenerierer);
    }

    private StreckenNetz baueKuerzesteZeitNetz(final Zug zug) {
        StreckenKantenGenerierer streckenKantenGenerierer = new StreckenKantenGenerierer() {
            @Override
            public StreckenKante generiereStreckenKante(Strecke strecke, BahnhofsKnoten startBahnhof, BahnhofsKnoten endBahnhof) {
                return new StreckenKante(strecke, startBahnhof, endBahnhof) {
                    @Override
                    public double holeGewichtung() {
                        double fahrGeschwindigkeit = Math.min(zug.holeHoechstGeschwindigkeit(), this.holeStrecke().holeMaximalGeschwindigkeit());
                        return this.holeStrecke().holeLaenge() / fahrGeschwindigkeit;                    }
                };
            }
        };
        return baueStreckenNetz(zug, streckenKantenGenerierer);
    }

}
