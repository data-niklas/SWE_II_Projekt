package de.dhbw.bahn.schicht_0_plugins.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class EinfacherHttpServer implements HttpServer, HttpHandler {

    private int port;
    private String host;
    private Map<HttpRoute, HttpRueckruf> rueckrufTabelle;
    private boolean laeuft;

    private com.sun.net.httpserver.HttpServer httpServer;

    public EinfacherHttpServer() {
        laeuft = false;
        rueckrufTabelle = new HashMap<>();
        port = 8080;
        host = "localhost";
    }

    @Override
    public int holePort() {
        return this.port;
    }

    @Override
    public String holeHost() {
        return this.host;
    }

    @Override
    public void legeLos(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            this.httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(this.host, this.port), 0);
            this.registriereKontext();
            this.httpServer.setExecutor(null);
            this.httpServer.start();
            this.laeuft = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registriereKontext() {
        this.rueckrufTabelle.keySet().forEach(route -> {
            this.httpServer.createContext(route.holePfad(), this);
        });
    }

    @Override
    public void halteAn() {
        this.laeuft = false;
        this.httpServer.stop(0);
    }

    @Override
    public synchronized boolean holeLaeuft() {
        return this.laeuft;
    }

    @Override
    public void registriereHttpRueckruf(HttpRoute route, HttpRueckruf rueckruf) {
        this.rueckrufTabelle.put(route, rueckruf);
    }

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println("Exchange: " + exchange.getHttpContext().getPath());
        HttpRoute route = new HttpRoute(exchange.getHttpContext().getPath(), HttpAnfragemethode.valueOf(exchange.getRequestMethod()));
        if (!this.rueckrufTabelle.containsKey(route)) {
            this.verarbeiteHttpAntwort(exchange, new HttpAntwort(404, "Not Found", "text/plain"));
            return;
        }

        HttpRueckruf rueckruf = this.rueckrufTabelle.get(route);
        String koerper;
        try {
            koerper = leseKoerper(exchange.getRequestBody());
        } catch (IOException e) {
            e.printStackTrace();
            // todo: exception
            return;
        }
        HttpAntwort antwort = rueckruf.bearbeiteAnfrage(route, koerper);
        this.verarbeiteHttpAntwort(exchange, antwort);
    }

    private void verarbeiteHttpAntwort(HttpExchange exchange, HttpAntwort antwort) {
        try {
            exchange.sendResponseHeaders(antwort.holeStatus(), antwort.holeKoerper().length());
            this.schreibeKoerper(exchange.getResponseBody(), antwort.holeKoerper());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void schreibeKoerper(OutputStream output, String koerper) throws IOException {
        output.write(koerper.getBytes());
        output.close();
    }

    private String leseKoerper(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder stringBuilder = new StringBuilder();
        for (int ch; (ch = reader.read()) != -1; ) {
            stringBuilder.append((char) ch);
        }
        return stringBuilder.toString();
    }
}
