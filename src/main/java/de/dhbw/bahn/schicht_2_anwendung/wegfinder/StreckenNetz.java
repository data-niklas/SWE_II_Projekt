package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_2_anwendung.DuplikatFehler;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

public class StreckenNetz implements Graph {

    private final List<Bahnhof> bahnhofsListe;
    private final List<GraphStrecke> streckenListe;

    public StreckenNetz() {
        this.bahnhofsListe = new ArrayList<>();
        this.streckenListe = new ArrayList<>();
    }

    @Override
    public List<Bahnhof> holeKnoten() {
        return this.bahnhofsListe;
    }

    @Override
    public List<GraphStrecke> holeKanten() {
        return this.streckenListe;
    }

    public Optional<Bahnhof> sucheBahnhof(String name) {
        for (Bahnhof b : this.bahnhofsListe) {
            if (b.holeIdentifizierer().equals(name))
                return Optional.of(b);
        }
        return Optional.empty();
    }

    public Optional<GraphStrecke> sucheStrecke(String bezeichnung) {
        for (GraphStrecke s : this.streckenListe) {
            if (s.holeIdentifizierer().equals(bezeichnung))
                return Optional.of(s);
        }
        return Optional.empty();
    }

    public List<GraphStrecke> sucheStrecke(Bahnhof startBahnhof, Bahnhof endBahnhof) {
        List<GraphStrecke> strecken = new ArrayList<>();
        for (GraphStrecke s : this.streckenListe) {
            boolean hinrichtung = s.holeStartKnoten().equals(startBahnhof) && s.holeEndKnoten().equals(endBahnhof);
            boolean rueckrichtung = s.holeStartKnoten().equals(endBahnhof) && s.holeEndKnoten().equals(startBahnhof);
            if (hinrichtung || rueckrichtung)
                strecken.add(s);
        }
        return strecken;
    }

    public void streckeHinzufuegen(GraphStrecke strecke) {
        if (sucheStrecke(strecke.holeIdentifizierer()).isPresent())
            throw new DuplikatFehler("Eine Strecke mit diesem Identifizierer ist bereits vorhanden.");

        if (!sucheBahnhof(strecke.holeStartKnoten().holeIdentifizierer()).isPresent())
            throw new MissingResourceException("Der Startbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeStartKnoten().holeIdentifizierer());

        if (!sucheBahnhof(strecke.holeEndKnoten().holeIdentifizierer()).isPresent())
            throw new MissingResourceException("Der Endbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeEndKnoten().holeIdentifizierer());

        this.streckenListe.add(strecke);
    }

    public void bahnhofHinzufuegen(Bahnhof bahnhof) {
        if (sucheBahnhof(bahnhof.holeIdentifizierer()).isPresent())
            throw new DuplikatFehler("Ein Bahnhof mit diesem Identifizierer ist bereits vorhanden.");

        this.bahnhofsListe.add(bahnhof);
    }


}
