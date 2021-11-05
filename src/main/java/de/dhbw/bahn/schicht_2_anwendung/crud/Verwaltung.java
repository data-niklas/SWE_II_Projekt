package de.dhbw.bahn.schicht_2_anwendung.crud;

import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

import java.util.List;

public interface Verwaltung<T extends Identifizierbar> {
    boolean hatEntitaet(String identifizierer);

    T holeEntitaet(String identifizierer);

    void persistiereEntitaet(T entitaet);

    void aktualisiereEntitaet(T entitaet);

    void loescheEntitaet(String identifizierer);

    List<T> holeEntitaeten();
}
