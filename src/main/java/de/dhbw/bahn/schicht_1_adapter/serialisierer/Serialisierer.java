package de.dhbw.bahn.schicht_1_adapter.serialisierer;

public interface Serialisierer {
    <T> String serialisieren(T objekt);

    <T> T deserialisieren(String etwas, Class<T> klasse);
}
