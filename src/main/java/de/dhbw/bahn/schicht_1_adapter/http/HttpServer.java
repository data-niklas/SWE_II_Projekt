package de.dhbw.bahn.schicht_1_adapter.http;

public interface HttpServer {
    int holePort();

    String holeHost();

    boolean holeLaeuft();

    void legeLos(String host, int port);

    void halteAn();


    void registriereHttpRueckruf(HttpRoute route, HttpRueckruf rueckruf);
}
