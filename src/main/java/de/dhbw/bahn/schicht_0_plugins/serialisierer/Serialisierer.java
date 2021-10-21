package de.dhbw.bahn.schicht_0_plugins.serialisierer;

public interface Serialisierer<T> {
    String zuJson(T objekt);

    T vonJson(String json);
}
