package de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle;

import de.dhbw.bahn.schicht_2_anwendung.EntitaetenVerwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;
import de.dhbw.bahn.schicht_4_abstraktion.DuplikatFehler;

import java.util.MissingResourceException;

public class EntitaetenAufsicht {

    private final EntitaetenVerwaltung<Strecke> streckenVerwaltung;
    private final EntitaetenVerwaltung<Zug> zugVerwaltung;
    private final EntitaetenVerwaltung<Bahnhof> bahnhofVerwaltung;

    public EntitaetenAufsicht(EntitaetenVerwaltung<Strecke> streckenVerwaltung, EntitaetenVerwaltung<Zug> zugVerwaltung, EntitaetenVerwaltung<Bahnhof> bahnhofsVerwaltung) {
        this.streckenVerwaltung = streckenVerwaltung;
        this.zugVerwaltung = zugVerwaltung;
        this.bahnhofVerwaltung = bahnhofsVerwaltung;
    }

    public void streckeHinzufuegen(Strecke strecke) {
        if (streckenVerwaltung.hatEntitaet(strecke))
            throw new DuplikatFehler("Eine Strecke mit diesem Identifizierer ist bereits vorhanden.");

        if (!bahnhofVerwaltung.hatEntitaet(strecke.holeStartBahnhof()))
            throw new MissingResourceException("Der Startbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeStartBahnhof().holeIdentifizierer());

        if (!bahnhofVerwaltung.hatEntitaet(strecke.holeEndBahnhof()))
            throw new MissingResourceException("Der Endbahnhof der Strecke ist nicht vorhanden.",
                    Bahnhof.class.getSimpleName(),
                    strecke.holeEndBahnhof().holeIdentifizierer());

        this.streckenVerwaltung.persistiereEntitaet(strecke);
    }

    public void bahnhofHinzufuegen(Bahnhof bahnhof) {
        if (bahnhofVerwaltung.hatEntitaet(bahnhof))
            throw new DuplikatFehler("Ein Bahnhof mit diesem Identifizierer ist bereits vorhanden.");

        this.bahnhofVerwaltung.persistiereEntitaet(bahnhof);
    }

    public void zugHinzufuegen(Zug zug) {
        if (zugVerwaltung.hatEntitaet(zug))
            throw new DuplikatFehler("Ein Zug mit diesem Identifizierer ist bereits vorhanden.");

        this.zugVerwaltung.persistiereEntitaet(zug);
    }
}
