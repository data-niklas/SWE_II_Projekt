package de.dhbw.bahn.schicht_0_plugins.serialisierer;

import com.google.gson.Gson;

public class GsonSerialisierer<T> implements Serialisierer<T> {

    private final Gson gson;
    private final Class<T> type;

    public GsonSerialisierer(Class<T> type) {
        this.gson = new Gson();
        this.type = type;
    }

    @Override
    public String zuJson(T objekt) {
        return this.gson.toJson(objekt);
    }

    @Override
    public T vonJson(String json) {
        return this.gson.fromJson(json, this.type);
    }
}
