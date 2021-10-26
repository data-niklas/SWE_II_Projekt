package de.dhbw.bahn.schicht_1_adapter.http;

import java.util.Map;

public interface HttpRueckruf {

    HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper, Map<String, String> parameters);
}
