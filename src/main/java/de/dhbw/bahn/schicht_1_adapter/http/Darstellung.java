package de.dhbw.bahn.schicht_1_adapter.http;

import java.util.Map;

public interface Darstellung {

    Map<String, String> holeKonfiguration();

    boolean holeLaeuft();

    void legeLos(Map<String, String> konfiguration);

    void halteAn();

    void registriereEventRueckruf(Event route, EventRueckruf rueckruf);
}
