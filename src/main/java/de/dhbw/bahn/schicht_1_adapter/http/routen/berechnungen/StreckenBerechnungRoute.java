package de.dhbw.bahn.schicht_1_adapter.http.routen.berechnungen;

import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.EventRueckruf;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.crud.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.WegFinder;
import de.dhbw.bahn.schicht_2_anwendung.wegfinder.StreckenBerechner;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.Map;

public abstract class StreckenBerechnungRoute implements EventRueckruf {

    protected static final String PARAMETER_START_BAHNHOF = "start";
    protected static final String PARAMETER_ZIEL_BAHNHOF = "ziel";
    protected static final String PARAMETER_ZUG = "zug";

    protected final Serialisierer serialisierer;
    protected final EntitaetenAufsicht aufsicht;
    protected final StreckenBerechner streckenBerechner;

    public StreckenBerechnungRoute(Serialisierer serialisierer, EntitaetenAufsicht aufsicht, WegFinder wegFinder) {
        this.serialisierer = serialisierer;
        this.aufsicht = aufsicht;
        this.streckenBerechner = new StreckenBerechner(aufsicht.holeBahnhofVerwaltung(), aufsicht.holeStreckenVerwaltung(), wegFinder);
    }

    @Override
    public EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameter) {
        if (!pruefeParameter(parameter))
            return new EventAntwort(400, "Schlechte Anfrage", MimeTyp.SCHLICHT);

        Bahnhof startBahnhof = this.aufsicht.holeBahnhof(parameter.get(PARAMETER_START_BAHNHOF));
        Bahnhof endBahnhof = this.aufsicht.holeBahnhof(parameter.get(PARAMETER_ZIEL_BAHNHOF));
        Zug zug = this.aufsicht.holeZug(parameter.get(PARAMETER_ZUG));

        if (startBahnhof == null || endBahnhof == null || zug == null)
            return new EventAntwort(400, "Schlechte Anfrage", MimeTyp.SCHLICHT);

        List<Strecke> strecken = this.berechne(startBahnhof, endBahnhof, zug);

        String antwort = this.serialisierer.serialisieren(strecken);
        return new EventAntwort(200, antwort, MimeTyp.JSON);
    }

    protected boolean pruefeParameter(Map<String, String> parameter) {
        return parameter.containsKey(PARAMETER_START_BAHNHOF)
                && parameter.containsKey(PARAMETER_ZIEL_BAHNHOF)
                && parameter.containsKey(PARAMETER_ZUG);
    }

    protected abstract List<Strecke> berechne(Bahnhof startBahnhof, Bahnhof endBahnhof, Zug zug);

}
