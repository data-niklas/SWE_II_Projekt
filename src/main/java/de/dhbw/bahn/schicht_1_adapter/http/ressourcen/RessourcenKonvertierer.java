package de.dhbw.bahn.schicht_1_adapter.http.ressourcen;

public interface RessourcenKonvertierer<S, T> {
    S konvertiereZu(T t);

    T konvertiereVon(S s);


}
