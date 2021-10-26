package de.dhbw.bahn.schicht_0_plugins.persistierung;

import de.dhbw.bahn.schicht_2_anwendung.Verwaltung;
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
    public boolean hatEntitaet(T entitaet) {
        return zuweisungsTabelle.containsKey(entitaet.holeIdentifizierer());
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
    public void loescheEntitaet(T entitaet) {
        this.zuweisungsTabelle.remove(entitaet.holeIdentifizierer());
    }

    @Override
    public List<T> holeEntitaeten() {
        return new ArrayList<>(zuweisungsTabelle.values());
    }
}
