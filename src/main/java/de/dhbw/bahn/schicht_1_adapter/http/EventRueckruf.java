package de.dhbw.bahn.schicht_1_adapter.http;

import java.util.Map;

public interface EventRueckruf {

    EventAntwort bearbeiteAnfrage(Event route, String koerper, Map<String, String> parameters);
}
