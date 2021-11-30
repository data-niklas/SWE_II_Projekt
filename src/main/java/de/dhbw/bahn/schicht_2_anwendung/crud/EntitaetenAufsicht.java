package de.dhbw.bahn.schicht_2_anwendung.crud;

import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_2_anwendung.DuplikatFehler;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.MissingResourceException;

public class EntitaetenAufsicht {

    private final Verwaltung<Bahnhof> bahnhofVerwaltung;
    private final Verwaltung<Strecke> streckenVerwaltung;
    private final Verwaltung<Zug> zugVerwaltung;


    public EntitaetenAufsicht(Verwaltung<Bahnhof> bahnhofsVerwaltung, Verwaltung<Strecke> streckenVerwaltung, Verwaltung<Zug> zugVerwaltung) {
        this.streckenVerwaltung = streckenVerwaltung;
        this.zugVerwaltung = zugVerwaltung;
        this.bahnhofVerwaltung = bahnhofsVerwaltung;
    }

    public List<Zug> holeZuege() {
        return this.zugVerwaltung.holeEntitaeten();
    }

    public List<Strecke> holeStrecken() {
        return this.streckenVerwaltung.holeEntitaeten();
    }

    public List<Bahnhof> holeBahnhoefe() {
        return this.bahnhofVerwaltung.holeEntitaeten();
    }


    public Zug holeZug(String zugNummer) {
        return this.zugVerwaltung.holeEntitaet(zugNummer);
    }

    public Bahnhof holeBahnhof(String name) {
        return this.bahnhofVerwaltung.holeEntitaet(name);
    }

    public Strecke holeStrecke(String bezeichnung) {
        return this.streckenVerwaltung.holeEntitaet(bezeichnung);
    }


    public void streckeHinzufuegen(Strecke strecke) {
        if (streckenVerwaltung.hatEntitaet(strecke.holeIdentifizierer()))
            throw new DuplikatFehler("Eine Strecke mit diesem Identifizierer ist bereits vorhanden.");

        if (!bahnhofVerwaltung.hatEntitaet(strecke.holeStartBahnhof().holeIdentifizierer()))
            throw new MissingResourceException("Der Startbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeStartBahnhof().holeIdentifizierer());

        if (!bahnhofVerwaltung.hatEntitaet(strecke.holeEndBahnhof().holeIdentifizierer()))
            throw new MissingResourceException("Der Endbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeEndBahnhof().holeIdentifizierer());

        this.streckenVerwaltung.persistiereEntitaet(strecke);
    }

    public void bahnhofHinzufuegen(Bahnhof bahnhof) {
        if (bahnhofVerwaltung.hatEntitaet(bahnhof.holeIdentifizierer()))
            throw new DuplikatFehler("Ein Bahnhof mit diesem Identifizierer ist bereits vorhanden.");

        this.bahnhofVerwaltung.persistiereEntitaet(bahnhof);
    }

    public void zugHinzufuegen(Zug zug) {
        if (zugVerwaltung.hatEntitaet(zug.holeIdentifizierer()))
            throw new DuplikatFehler("Ein Zug mit diesem Identifizierer ist bereits vorhanden.");

        this.zugVerwaltung.persistiereEntitaet(zug);
    }


    public void aktualisiereBahnhof(Bahnhof bahnhof) {
        if (!bahnhofVerwaltung.hatEntitaet(bahnhof.holeIdentifizierer()))
            throw new MissingResourceException("Bahnhof mit diesem Namen existiert nicht.",
                    Bahnhof.class.getSimpleName(),
                    bahnhof.holeIdentifizierer());

        bahnhofVerwaltung.aktualisiereEntitaet(bahnhof);
    }

    public void aktualisiereStrecke(Strecke strecke) {
        if (!streckenVerwaltung.hatEntitaet(strecke.holeIdentifizierer()))
            throw new MissingResourceException("Strecke mit dieser Bezeichnung existiert nicht.",
                    Strecke.class.getSimpleName(),
                    strecke.holeIdentifizierer());

        if (!bahnhofVerwaltung.hatEntitaet(strecke.holeStartBahnhof().holeIdentifizierer()))
            throw new MissingResourceException("Der Startbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeStartBahnhof().holeIdentifizierer());

        if (!bahnhofVerwaltung.hatEntitaet(strecke.holeEndBahnhof().holeIdentifizierer()))
            throw new MissingResourceException("Der Endbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeEndBahnhof().holeIdentifizierer());

        streckenVerwaltung.aktualisiereEntitaet(strecke);
    }

    public void aktualisiereZug(Zug zug) {
        if (!this.zugVerwaltung.hatEntitaet(zug.holeIdentifizierer()))
            throw new MissingResourceException("Zug mit dieser Zugnummer existiert nicht.",
                    Strecke.class.getSimpleName(),
                    zug.holeIdentifizierer());

        zugVerwaltung.aktualisiereEntitaet(zug);
    }


    public void bahnhofLoeschen(String name) {
        if (!this.bahnhofVerwaltung.hatEntitaet(name))
            throw new MissingResourceException("Bahnhof mit diesem Namen existiert nicht.",
                    Bahnhof.class.getSimpleName(),
                    name);

        this.bahnhofVerwaltung.loescheEntitaet(name);
    }

    public void streckeLoeschen(String bezeichnung) {
        if (!this.streckenVerwaltung.hatEntitaet(bezeichnung))
            throw new MissingResourceException("Strecke mit dieser Bezeichnung existiert nicht.",
                    Strecke.class.getSimpleName(),
                    bezeichnung);

        this.streckenVerwaltung.loescheEntitaet(bezeichnung);
    }

    public void zugLoeschen(String zugNummer) {
        if (!this.zugVerwaltung.hatEntitaet(zugNummer))
            throw new MissingResourceException("Zug mit dieser Zugnummer existiert nicht.",
                    Strecke.class.getSimpleName(),
                    zugNummer);
        this.zugVerwaltung.loescheEntitaet(zugNummer);
    }

    public Verwaltung<Bahnhof> holeBahnhofVerwaltung() {
        return bahnhofVerwaltung;
    }

    public Verwaltung<Strecke> holeStreckenVerwaltung() {
        return streckenVerwaltung;
    }

    public Verwaltung<Zug> holeZugVerwaltung() {
        return zugVerwaltung;
    }
}
