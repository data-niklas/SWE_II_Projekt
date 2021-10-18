package schicht_2_anwendung;

import schicht_4_abstraktion.Identifizierbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitaetenVerwaltung<T extends Identifizierbar> {

    protected Map<String, T> zuweisungsTabelle;

    public EntitaetenVerwaltung() {
        this.zuweisungsTabelle = new HashMap<>();
    }

    public boolean hatEntitaet(T entitaet) {
        return zuweisungsTabelle.containsKey(entitaet.holeIdentifizierer());
    }

    public T holeEntitaet(String identifizierer) {
        return zuweisungsTabelle.get(identifizierer);
    }

    public void persistiereEntitaet(T entitaet) {
        this.zuweisungsTabelle.put(entitaet.holeIdentifizierer(), entitaet);
    }

    public void loescheEntitaet(T entitaet) {
        this.zuweisungsTabelle.remove(entitaet.holeIdentifizierer());
    }

    public List<T> holeEntitaeten() {
        return new ArrayList<>(zuweisungsTabelle.values());
    }
}
