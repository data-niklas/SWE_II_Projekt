package de.dhbw.bahn.schicht_2_anwendung;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

import java.util.List;

public interface Verwaltung<T extends Identifizierbar> {
    boolean hatEntitaet(T entitaet);

    T holeEntitaet(String identifizierer);

    void persistiereEntitaet(T entitaet);

    void loescheEntitaet(T entitaet);

    List<T> holeEntitaeten();
}
