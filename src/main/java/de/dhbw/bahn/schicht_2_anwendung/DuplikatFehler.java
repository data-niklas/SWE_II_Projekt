package de.dhbw.bahn.schicht_2_anwendung;

public class DuplikatFehler extends RuntimeException {
    public DuplikatFehler(String message) {
        super(message);
    }
}
