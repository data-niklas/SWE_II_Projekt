package de.dhbw.bahn.schicht_0_plugins.persistenz;

import de.dhbw.bahn.schicht_2_anwendung.crud.Verwaltung;
import de.dhbw.bahn.schicht_4_abstraktion.Identifizierbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemporaereVerwaltung<T extends Identifizierbar> implements Verwaltung<T> {

    protected Map<String, T> zuweisungsTabelle;

    public TemporaereVerwaltung() {
        this.zuweisungsTabelle = new HashMap<>();
    }

    @Override
    public boolean hatEntitaet(String identifizierer) {
        return zuweisungsTabelle.containsKey(identifizierer);
    }

    @Override
    public T holeEntitaet(String identifizierer) {
        return zuweisungsTabelle.get(identifizierer);
    }

    @Override
    public void persistiereEntitaet(T entitaet) {
        this.zuweisungsTabelle.put(entitaet.holeIdentifizierer(), entitaet);
    }

    @Override
    public void aktualisiereEntitaet(T entitaet) {
        this.zuweisungsTabelle.put(entitaet.holeIdentifizierer(), entitaet);
    }

    @Override
    public void loescheEntitaet(String identifizierer) {
        this.zuweisungsTabelle.remove(identifizierer);
    }

    @Override
    public List<T> holeEntitaeten() {
        return new ArrayList<>(zuweisungsTabelle.values());
    }
}
