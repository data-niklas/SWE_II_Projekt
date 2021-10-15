package schicht_3_domaene;

import schicht_4_abstraktion.DuplikatFehler;
import schicht_4_abstraktion.dijkstra.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

public class StreckenNetz implements Graph {

    private List<Bahnhof> bahnhofsListe;
    private List<Strecke> streckenListe;

    public StreckenNetz() {
        this.bahnhofsListe = new ArrayList<>();
        this.streckenListe = new ArrayList<>();
    }

    @Override
    public List<Bahnhof> holeKnoten() {
        return this.bahnhofsListe;
    }

    @Override
    public List<Strecke> holeKanten() {
        return this.streckenListe;
    }

    public Optional<Bahnhof> sucheBahnhof(String name) {
        for (Bahnhof b : this.bahnhofsListe) {
            if (b.holeIdentifizierer().equals(name))
                return Optional.of(b);
        }
        return Optional.empty();
    }

    public Optional<Strecke> sucheStrecke(String bezeichnung) {
        for (Strecke s : this.streckenListe) {
            if (s.holeIdentifizierer().equals(bezeichnung))
                return Optional.of(s);
        }
        return Optional.empty();
    }

    public List<Strecke> sucheStrecke(Bahnhof startBahnhof, Bahnhof endBahnhof) {
        List<Strecke> strecken = new ArrayList<>();
        for (Strecke s : this.streckenListe) {
            boolean hinrichtung = s.holeStartKnoten().equals(startBahnhof) && s.holeEndKnoten().equals(endBahnhof);
            boolean rueckrichtung = s.holeStartKnoten().equals(endBahnhof) && s.holeEndKnoten().equals(startBahnhof);
            if (hinrichtung || rueckrichtung)
                strecken.add(s);
        }
        return strecken;
    }

    public void streckeHinzufuegen(Strecke strecke) {
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
