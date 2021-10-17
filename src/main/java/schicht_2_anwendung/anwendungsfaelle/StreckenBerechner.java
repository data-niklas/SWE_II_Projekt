package schicht_2_anwendung.anwendungsfaelle;


import schicht_2_anwendung.EntitaetenVerwaltung;
import schicht_3_domaene.*;
import schicht_4_abstraktion.dijkstra.Dijkstra;
import schicht_4_abstraktion.dijkstra.Kante;

import java.util.ArrayList;
import java.util.List;

public class StreckenBerechner {

    private final EntitaetenVerwaltung<Bahnhof> bahnhofsVerwaltung;
    private final EntitaetenVerwaltung<Strecke> streckenVerwaltung;

    public StreckenBerechner(EntitaetenVerwaltung<Bahnhof> bahnhofsVerwaltung, EntitaetenVerwaltung<Strecke> streckenVerwaltung){
        this.bahnhofsVerwaltung = bahnhofsVerwaltung;
        this.streckenVerwaltung = streckenVerwaltung;
    }


    public List<Strecke> berechneKuerzesteStrecke(Bahnhof start, Bahnhof ende, Zug zug){
        StreckenNetz streckenNetz = baueKuerzesteStreckeNetz(zug);
        Dijkstra dijkstra = new Dijkstra(streckenNetz);
        List<Kante> weg = dijkstra.kuerzesterWeg(start, ende);
        return transformiereStrecke(weg);
    }

    public List<Strecke> berechneKuerzesteZeitStrecke(Bahnhof start, Bahnhof ende, Zug zug){
        StreckenNetz streckenNetz = baueKuerzesteZeitNetz(zug);
        Dijkstra dijkstra = new Dijkstra(streckenNetz);
        List<Kante> weg = dijkstra.kuerzesterWeg(start, ende);
        return transformiereStrecke(weg);
    }

    private List<Strecke> transformiereStrecke(List<Kante> weg){
        List<Strecke> ergebnis = new ArrayList<>();
        for (Kante kante : weg) {
            String identifizierer = ((SimpleStrecke) kante).holeIdentifizierer();
            ergebnis.add(streckenVerwaltung.holeEntitaet(identifizierer));
        }
        return ergebnis;
    }

    private StreckenNetz baueKuerzesteStreckeNetz(Zug zug) {
        StreckenNetz netz = new StreckenNetz();
        for (Bahnhof bahnhof : bahnhofsVerwaltung.holeEntitaeten()) {
            netz.bahnhofHinzufuegen(bahnhof);
        }
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp())){
                continue;
            }
            netz.streckeHinzufuegen(new SimpleStrecke(strecke.holeBezeichnung(), strecke.holeGewichtung(), strecke.holeStartKnoten(), strecke.holeEndKnoten()));
        }
        return netz;
    }

    private StreckenNetz baueKuerzesteZeitNetz(Zug zug) {
        StreckenNetz netz = new StreckenNetz();
        for (Bahnhof bahnhof : bahnhofsVerwaltung.holeEntitaeten()) {
            netz.bahnhofHinzufuegen(bahnhof);
        }
        for (Strecke strecke : streckenVerwaltung.holeEntitaeten()) {
            if (!strecke.istFreigegeben() || !strecke.holeErlaubteZugTypen().contains(zug.holeZugTyp())){
                continue;
            }
            double fahrGeschwindigkeit = Math.min(zug.holeHoechstGeschwindigkeit(), strecke.holeMaximalGeschwindigkeit());
            double zeitGewichtung = strecke.holeGewichtung() / fahrGeschwindigkeit;
            netz.streckeHinzufuegen(new SimpleStrecke(strecke.holeBezeichnung(), zeitGewichtung, strecke.holeStartKnoten(), strecke.holeEndKnoten()));
        }
        return netz;
    }

}
