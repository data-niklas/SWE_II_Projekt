package de.dhbw.bahn.schicht_1_adapter.http.routen.berechnungen;

import de.dhbw.bahn.schicht_1_adapter.http.HttpAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRoute;
import de.dhbw.bahn.schicht_1_adapter.http.HttpRueckruf;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.EntitaetenAufsicht;
import de.dhbw.bahn.schicht_2_anwendung.anwendungsfaelle.StreckenBerechner;
import de.dhbw.bahn.schicht_3_domaene.Bahnhof;
import de.dhbw.bahn.schicht_3_domaene.GraphStrecke;
import de.dhbw.bahn.schicht_3_domaene.Strecke;
import de.dhbw.bahn.schicht_3_domaene.Zug;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KuerzesteStreckeGet implements HttpRueckruf {

    private static final String PARAMETER_START_BAHNHOF = "start";
    private static final String PARAMETER_ZIEL_BAHNHOF = "ziel";
    private static final String PARAMETER_ZUG = "zug";

    private final Serialisierer serialisierer;
    private final EntitaetenAufsicht aufsicht;
    private final StreckenBerechner streckenBerechner;

    public KuerzesteStreckeGet(Serialisierer serialisierer, EntitaetenAufsicht aufsicht) {
        this.serialisierer = serialisierer;
        this.aufsicht = aufsicht;
        this.streckenBerechner = new StreckenBerechner(aufsicht.holeBahnhofVerwaltung(), aufsicht.holeStreckenVerwaltung());
    }

    @Override
    public HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameter) {
        if (!pruefeParameter(parameter))
            return new HttpAntwort(400, "Schlechte Anfrage", MimeTyp.SCHLICHT);

        Bahnhof startBahnhof = this.aufsicht.holeBahnhof(parameter.get(PARAMETER_START_BAHNHOF));
        Bahnhof endBahnhof = this.aufsicht.holeBahnhof(parameter.get(PARAMETER_ZIEL_BAHNHOF));
        Zug zug = this.aufsicht.holeZug(parameter.get(PARAMETER_ZUG));

        if (startBahnhof == null || endBahnhof == null || zug == null)
            return new HttpAntwort(400, "Schlechte Anfrage", MimeTyp.SCHLICHT);

        List<GraphStrecke> graphStrecken = this.streckenBerechner.berechneKuerzesteStrecke(startBahnhof, endBahnhof, zug);
        List<Strecke> strecken = graphStrecken.stream().map(GraphStrecke::holeStrecke).collect(Collectors.toList());

        String antwort = this.serialisierer.serialisieren(strecken);
        return new HttpAntwort(200, antwort, MimeTyp.JSON);
    }

    private boolean pruefeParameter(Map<String, String> parameter) {
        return parameter.containsKey(PARAMETER_START_BAHNHOF)
                && parameter.containsKey(PARAMETER_ZIEL_BAHNHOF)
                && parameter.containsKey(PARAMETER_ZUG);
    }
}
