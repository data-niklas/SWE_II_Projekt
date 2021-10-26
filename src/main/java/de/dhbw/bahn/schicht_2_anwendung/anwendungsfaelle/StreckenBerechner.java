package de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle;

import de.dhbw.bahn.schicht_0_plugins.persistierung.TemporaereVerwaltung;
import de.dhbw.bahn.schicht_3_domaene.*;
import de.dhbw.bahn.schicht_4_abstraktion.dijkstra.Dijkstra;

import java.util.List;

public class StreckenBerechner {

    private final TemporaereVerwaltung<Bahnhof> bahnhofsVerwaltung;
    private final TemporaereVerwaltung<Strecke> streckenVerwaltung;

    public StreckenBerechner(TemporaereVerwaltung<Bahnhof> bahnhofsVerwaltung, TemporaereVerwaltung<Strecke> streckenVerwaltung) {
        this.bahnhofsVerwaltung = bahnhofsVerwaltung;
        this.streckenVerwaltung = streckenVerwaltung;
    }


    public List<GraphStrecke> berechneKuerzesteStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteStreckeNetz(zug);
        Dijkstra dijkstra = new Dijkstra(streckenNetz);
        List<GraphStrecke> weg = (List<GraphStrecke>) dijkstra.kuerzesterWeg(start, ende);
        return weg;
    }

    public List<GraphStrecke> berechneKuerzesteZeitStrecke(Bahnhof start, Bahnhof ende, Zug zug) {
        StreckenNetz streckenNetz = baueKuerzesteZeitNetz(zug);
        Dijkstra dijkstra = new Dijkstra(streckenNetz);
        List<GraphStrecke> weg = (List<GraphStrecke>) dijkstra.kuerzesterWeg(start, ende);
        return weg;
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
