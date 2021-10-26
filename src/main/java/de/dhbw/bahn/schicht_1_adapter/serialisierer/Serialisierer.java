package de.dhbw.bahn.schicht_1_adapter.serialisierer;

public interface Serialisierer {
    <T> String zuJson(T objekt);

    <T> T vonJson(String json, Class<T> klasse);
}
