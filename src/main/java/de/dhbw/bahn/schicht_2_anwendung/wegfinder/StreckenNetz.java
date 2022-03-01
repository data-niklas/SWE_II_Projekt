package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_2_anwendung.DuplikatFehler;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_4_abstraktion.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

public class StreckenNetz implements Graph {

    private final List<BahnhofsKnoten> bahnhofsListe;
    private final List<StreckenKante> streckenListe;

    public StreckenNetz() {
        this.bahnhofsListe = new ArrayList<>();
        this.streckenListe = new ArrayList<>();
    }

    @Override
    public List<BahnhofsKnoten> holeKnoten() {
        return this.bahnhofsListe;
    }

    @Override
    public List<StreckenKante> holeKanten() {
        return this.streckenListe;
    }

    public Optional<BahnhofsKnoten> sucheBahnhof(String name) {
        for (BahnhofsKnoten b : this.bahnhofsListe) {
            if (b.holeIdentifizierer().equals(name))
                return Optional.of(b);
        }
        return Optional.empty();
    }

    public Optional<StreckenKante> sucheStrecke(String bezeichnung) {
        for (StreckenKante s : this.streckenListe) {
            if (s.holeIdentifizierer().equals(bezeichnung))
                return Optional.of(s);
        }
        return Optional.empty();
    }

    public void streckeHinzufuegen(StreckenKante strecke) {
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

    public void bahnhofHinzufuegen(BahnhofsKnoten bahnhof) {
        if (sucheBahnhof(bahnhof.holeIdentifizierer()).isPresent())
            throw new DuplikatFehler("Ein Bahnhof mit diesem Identifizierer ist bereits vorhanden.");

        this.bahnhofsListe.add(bahnhof);
    }


}
