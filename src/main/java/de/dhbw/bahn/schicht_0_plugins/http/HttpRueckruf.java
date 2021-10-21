package de.dhbw.bahn.schicht_0_plugins.http;

public interface HttpRueckruf {

    HttpAntwort bearbeiteAnfrage(HttpRoute route, String koerper);
}
