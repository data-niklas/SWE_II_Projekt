package de.dhbw.bahn.schicht_0_plugins.http;

public interface HttpServer {
    int holePort();

    String holeHost();

    boolean holeLaeuft();

    void legeLos(String host, int port);

    void halteAn();

    void registriereHttpRueckruf(HttpRoute route, HttpRueckruf rueckruf);
}
