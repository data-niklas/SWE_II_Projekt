package de.dhbw.bahn.schicht_0_plugins.http;

import de.dhbw.bahn.schicht_1_adapter.http.EventTyp;

import java.util.Map;
import java.util.Optional;

public class HttpMethodeZuEventTypMapping {

    private static Map<String, EventTyp> mapping = Map.ofEntries(
            Map.entry("GET", EventTyp.LESEN),
            Map.entry("PUT", EventTyp.AKTUALISIEREN),
            Map.entry("POST", EventTyp.ERSTELLEN),
            Map.entry("DELETE", EventTyp.LOESCHEN)
    );

    public static Optional<EventTyp> fuer(String httpMethode) {
        return Optional.ofNullable(mapping.get(httpMethode));
    }

}
