package de.dhbw.bahn.schicht_0_plugins.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.dhbw.bahn.schicht_1_adapter.http.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class EinfacherHttpServer implements HttpServer, HttpHandler {

    private final Map<HttpRoute, HttpRueckruf> rueckrufTabelle;
    private int port;
    private String host;
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
            this.laeuft = true;
            this.httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registriereKontext() {
        this.rueckrufTabelle.keySet().forEach(route -> this.httpServer.createContext(route.holePfad(), this));
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
        System.out.println(exchange.getRequestMethod() + " Anfrage an " + exchange.getHttpContext().getPath());
        String pfad = exchange.getHttpContext().getPath();
        String query = exchange.getRequestURI().getQuery();

        HttpRoute route = new HttpRoute(pfad, HttpAnfragemethode.valueOf(exchange.getRequestMethod()));
        if (!this.rueckrufTabelle.containsKey(route)) {
            this.verarbeiteHttpAntwort(exchange, new HttpAntwort(404, "Not Found", MimeTyp.SCHLICHT));
            return;
        }

        Map<String, String> parameter = null;
        try {
            parameter = leseParameter(query);
        } catch (UnsupportedEncodingException e) {
            this.verarbeiteHttpAntwort(exchange, new HttpAntwort(500, "Internal server error", MimeTyp.SCHLICHT));
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
        HttpAntwort antwort;
        try {
            antwort = rueckruf.bearbeiteAnfrage(route, koerper, parameter);
        } catch (Exception e) {
            this.verarbeiteHttpAntwort(exchange, new HttpAntwort(500, e.getMessage(), MimeTyp.SCHLICHT));
            return;
        }
        this.verarbeiteHttpAntwort(exchange, antwort);
    }

    private Map<String, String> leseParameter(String query) throws UnsupportedEncodingException {
        Map<String, String> parameter = new HashMap<>();
        if (query == null) return parameter;
        String[] parameterPaare = query.split("&");
        for (String paar : parameterPaare) {
            String[] paarTeile = paar.split("=");
            String schluessel = URLDecoder.decode(paarTeile[0], "utf-8");
            String wert = URLDecoder.decode(paarTeile[1], "utf-8");
            parameter.put(schluessel, wert);
        }

        return parameter;
    }

    private void verarbeiteHttpAntwort(HttpExchange exchange, HttpAntwort antwort) {
        try {
            exchange.getResponseHeaders().add("Content-Type", antwort.holeKoerperTyp().holeWert() + "; charset=utf-8");
            exchange.sendResponseHeaders(antwort.holeStatus(), antwort.holeKoerper().getBytes().length);
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
