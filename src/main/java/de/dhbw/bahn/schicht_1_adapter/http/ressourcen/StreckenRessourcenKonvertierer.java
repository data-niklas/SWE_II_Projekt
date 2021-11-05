package de.dhbw.bahn.schicht_1_adapter.http.ressourcen;

import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;

import java.util.MissingResourceException;

public class StreckenRessourcenKonvertierer implements RessourcenKonvertierer<Strecke, StreckenRessource> {

    private final Verwaltung<Bahnhof> bahnhofVerwaltung;

    public StreckenRessourcenKonvertierer(Verwaltung<Bahnhof> bahnhofVerwaltung) {
        this.bahnhofVerwaltung = bahnhofVerwaltung;
    }

    @Override
    public Strecke konvertiereZu(StreckenRessource streckenRessource) {
        if (!bahnhofVerwaltung.hatEntitaet(streckenRessource.holeStartBahnhof())) {
            throw new MissingResourceException("Startbahnhof konnte nicht gefunden werden.", Bahnhof.class.getSimpleName(), streckenRessource.holeStartBahnhof());
        }
        if (!bahnhofVerwaltung.hatEntitaet(streckenRessource.holeEndBahnhof())) {
            throw new MissingResourceException("Endbahnhof konnte nicht gefunden werden.", Bahnhof.class.getSimpleName(), streckenRessource.holeEndBahnhof());
        }
        return new Strecke(
                streckenRessource.holeBezeichnung(),
                streckenRessource.holeLaenge(),
                streckenRessource.holeMaximalGeschwindigkeit(),
                streckenRessource.holeErlaubteZugTypen(),
                streckenRessource.isFreigegeben(),
                this.bahnhofVerwaltung.holeEntitaet(streckenRessource.holeStartBahnhof()),
                this.bahnhofVerwaltung.holeEntitaet(streckenRessource.holeEndBahnhof())
        );
    }

    @Override
    public StreckenRessource konvertiereVon(Strecke strecke) {
        return new StreckenRessource(
                strecke.holeBezeichnung(),
                strecke.holeStartBahnhof().holeIdentifizierer(),
                strecke.holeEndBahnhof().holeIdentifizierer(),
                strecke.holeLaenge(),
                strecke.holeErlaubteZugTypen(),
                strecke.istFreigegeben(),
                strecke.holeMaximalGeschwindigkeit()
        );
    }
}
