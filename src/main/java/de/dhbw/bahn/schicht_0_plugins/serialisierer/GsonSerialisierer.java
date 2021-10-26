package de.dhbw.bahn.schicht_0_plugins.serialisierer;

import com.google.gson.Gson;
import de.dhbw.bahn.schicht_1_adapter.serialisierer.Serialisierer;

public class GsonSerialisierer implements Serialisierer {

    private final Gson gson;

    public GsonSerialisierer() {
        this.gson = new Gson();
    }

    @Override
    public <T> String serialisieren(T objekt) {
        return this.gson.toJson(objekt);
    }

    @Override
    public <T> T deserialisieren(String json, Class<T> klasse) {
        return this.gson.fromJson(json, klasse);
    }
}
