package de.dhbw.bahn.schicht_1_adapter.http;

import java.util.Map;

public interface Startbar {

    boolean holeLaeuft();

    void legeLos(Map<String, String> konfiguration);

    void halteAn();

}
