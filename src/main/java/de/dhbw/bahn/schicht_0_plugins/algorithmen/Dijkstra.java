package de.dhbw.bahn.schicht_0_plugins.algorithmen;

import de.dhbw.bahn.schicht_2_anwendung.wegfinder.WegFinder;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Graph;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Kante;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Knoten;

import java.util.*;

public class Dijkstra implements WegFinder {
    private Graph graph;
    private Knoten aktuellerKnoten;
    private Map<Knoten, DijkstraKnotenDaten> distanzTabelle;

    public Dijkstra() {
    }

    @Override
    public void initialisiereGraphen(Graph graph) {
        this.graph = graph;
    }

    public List<? extends Kante> berechneWeg(Knoten start, Knoten end) {
        if(this.graph == null)
            return Collections.emptyList();

        initialisiereDistanzTabelle();
        waehleKnoten(start);
        distanzTabelle.get(start).setzeDistanz(0);

        while (!aktuellerKnoten.equals(end)) {
            berechneDistanzen();
            Knoten neuerKnoten = findeUnbearbeitetenMinimalenKnoten();
            if (neuerKnoten == null) {
                // Keinen Weg gefunden :(
                return new ArrayList<>();
            }
            waehleKnoten(neuerKnoten);
        }

        return bauWeg(start, end);
    }

    private void initialisiereDistanzTabelle() {
        if (distanzTabelle == null) {
            distanzTabelle = new HashMap<>();
        }
        for (Knoten knoten : graph.holeKnoten()) {
            distanzTabelle.put(knoten, new DijkstraKnotenDaten());
        }
    }

    private void laufeKnotenAb(Knoten knoten, double distanz) {
        DijkstraKnotenDaten daten = distanzTabelle.get(knoten);
        if (distanz < daten.holeDistanz()) {
            daten.setzeDistanz(distanz);
            daten.setzeVon(aktuellerKnoten);
        }
    }

    private Knoten findeUnbearbeitetenMinimalenKnoten() {
        Knoten minimalerKnoten = null;
        DijkstraKnotenDaten minimalerKnotenDaten = null;
        for (Map.Entry<Knoten, DijkstraKnotenDaten> entry : distanzTabelle.entrySet()) {
            Knoten knoten = entry.getKey();
            DijkstraKnotenDaten daten = entry.getValue();
            if (daten.istBearbeitet()) continue;
            if (minimalerKnoten == null
                    || daten.holeDistanz() < minimalerKnotenDaten.holeDistanz()) {
                minimalerKnoten = knoten;
                minimalerKnotenDaten = daten;
            }

        }
        return minimalerKnoten;
    }

    private void berechneDistanzen() {
        for (Kante kante : graph.holeKanten()) {
            Knoten endKnoten = null;
            if (kante.holeStartKnoten().equals(aktuellerKnoten)) {
                endKnoten = kante.holeEndKnoten();
            } else if (kante.holeEndKnoten().equals(aktuellerKnoten)) {
                endKnoten = kante.holeStartKnoten();
            }
            if (endKnoten != null) {
                DijkstraKnotenDaten daten = distanzTabelle.get(endKnoten);
                if (daten.istBearbeitet()) continue;
                double aktuelleGewichtung = distanzTabelle.get(aktuellerKnoten).holeDistanz();
                laufeKnotenAb(endKnoten, aktuelleGewichtung + kante.holeGewichtung());
            }
        }
    }


    private void waehleKnoten(Knoten knoten) {
        distanzTabelle.get(knoten).setzeBearbeitet(true);
        aktuellerKnoten = knoten;
    }



    private Knoten vorgeangerKnoten(Knoten knoten) {
        return distanzTabelle.get(knoten).holeVon();
    }

    private Kante kanteZwischen(Knoten knoten_1, Knoten knoten_2) {
        for (Kante kante : graph.holeKanten()) {
            if (kante.holeEndKnoten().equals(knoten_1) && kante.holeStartKnoten().equals(knoten_2)) {
                return kante;
            } else if (kante.holeEndKnoten().equals(knoten_2) && kante.holeStartKnoten().equals(knoten_1)) {
                return kante;
            }
        }
        return null;
    }

    private List<Kante> bauWeg(Knoten start, Knoten end) {
        List<Kante> weg = new ArrayList<>();
        Knoten zwischenKnoten = end;
        while (!zwischenKnoten.equals(start)) {
            Knoten knoten = vorgeangerKnoten(zwischenKnoten);
            Kante kante = kanteZwischen(knoten, zwischenKnoten);
            weg.add(0, kante);
            zwischenKnoten = knoten;
        }
        return weg;
    }

    private static class DijkstraKnotenDaten {
        private double distanz;
        private boolean bearbeitet;
        private Knoten von;

        public DijkstraKnotenDaten() {
            this.distanz = Integer.MAX_VALUE;
            this.bearbeitet = false;
            this.von = null;
        }

        public Knoten holeVon() {
            return von;
        }

        public void setzeVon(Knoten von) {
            this.von = von;
        }

        public double holeDistanz() {
            return distanz;
        }

        public void setzeDistanz(double distanz) {
            this.distanz = distanz;
        }

        public boolean istBearbeitet() {
            return bearbeitet;
        }

        public void setzeBearbeitet(boolean bearbeitet) {
            this.bearbeitet = bearbeitet;
        }
    }
}
