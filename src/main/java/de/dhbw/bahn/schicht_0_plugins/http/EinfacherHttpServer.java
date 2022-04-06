package de.dhbw.bahn.schicht_0_plugins.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.dhbw.bahn.schicht_1_adapter.http.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EinfacherHttpServer implements EventRegistrierer, Startbar, Konfigurierbar, HttpHandler {

    private final Map<Event, EventRueckruf> rueckrufTabelle;
    private int port;
    private String host;
    private boolean laeuft;

    private com.sun.net.httpserver.HttpServer httpServer;
    private Map<String, String> konfiguration;

    public EinfacherHttpServer() {
        laeuft = false;
        rueckrufTabelle = new HashMap<>();
        port = 8080;
        host = "localhost";
    }

    private void registriereKontext() {
        this.rueckrufTabelle.keySet().forEach(route -> this.httpServer.createContext(route.holeName(), this));
    }

    @Override
    public void halteAn() {
        this.laeuft = false;
        this.httpServer.stop(0);
    }

    @Override
    public Map<String, String> holeKonfiguration() {
        return this.konfiguration;
    }

    @Override
    public synchronized boolean holeLaeuft() {
        return this.laeuft;
    }

    @Override
    public void legeLos(Map<String, String> konfiguration) {
        this.konfiguration = konfiguration;
        if (konfiguration.containsKey("host"))
            this.host = konfiguration.get("host");
        if (konfiguration.containsKey("port"))
            this.port = Integer.parseInt(konfiguration.get("port"));
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

    @Override
    public void registriereEventRueckruf(Event route, EventRueckruf rueckruf) {
        this.rueckrufTabelle.put(route, rueckruf);
    }

    @Override
    public void handle(HttpExchange exchange) {
        EventAntwort antwort;
        try {
            antwort = this.verarbeiteAnfrage(exchange);
        } catch (IOException e) {
            antwort = new EventAntwort(500, "Internal server error", MimeTyp.SCHLICHT);
        }
        this.verarbeiteHttpAntwort(exchange, antwort);
    }

    private EventAntwort verarbeiteAnfrage(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestMethod() + " Anfrage an " + exchange.getHttpContext().getPath());

        if (!wirdAnfragemethodeUnterstuetzt(exchange))
            return baueEventAntwortAnfragemethodeNichtUnterstuetzt();

        Event event = baueEventAus(exchange);
        if (!istEventRueckrufVorhandenFuer(event))
            return baueEventAntwortEventRueckrufNichtGefunden();

        EventAntwort antwort = rufeEventRueckrufAufFuer(event, exchange);
        return antwort;
    }

    private boolean wirdAnfragemethodeUnterstuetzt(HttpExchange exchange) {
        Optional<EventTyp> eventTyp = holeEventTypFuer(exchange);
        return eventTyp.isPresent();
    }

    private Optional<EventTyp> holeEventTypFuer(HttpExchange exchange) {
        String httpMethod = exchange.getRequestMethod();
        return HttpMethodeZuEventTypMapping.fuer(httpMethod);
    }

    private EventAntwort baueEventAntwortAnfragemethodeNichtUnterstuetzt() {
        return new EventAntwort(404, "Diese Anfragemethode wird nicht unterstützt", MimeTyp.SCHLICHT);
    }

    private Event baueEventAus(HttpExchange exchange) {
        EventTyp eventTyp = holeEventTypFuer(exchange).get();
        String pfad = exchange.getHttpContext().getPath();
        Event event = new Event(pfad, eventTyp);
        return event;
    }

    private boolean istEventRueckrufVorhandenFuer(Event event) {
        return this.rueckrufTabelle.containsKey(event);
    }

    private EventAntwort baueEventAntwortEventRueckrufNichtGefunden() {
        return new EventAntwort(404, "Not Found", MimeTyp.SCHLICHT);
    }

    private EventAntwort rufeEventRueckrufAufFuer(Event event, HttpExchange exchange) throws IOException {
        EventRueckruf rueckruf = holeEventRueckrufFuer(event);
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> parameter = leseParameter(query);
        String koerper = leseKoerper(exchange.getRequestBody());
        EventAntwort antwort = rueckruf.bearbeiteAnfrage(event, koerper, parameter);
        return antwort;
    }

    private EventRueckruf holeEventRueckrufFuer(Event event) {
        return this.rueckrufTabelle.get(event);
    }

    private Map<String, String> leseParameter(String query) throws UnsupportedEncodingException {
        Map<String, String> parameter = new HashMap<>();
        if (query == null)
            return parameter;
        String[] parameterPaare = query.split("&");
        for (String paar : parameterPaare) {
            String[] paarTeile = paar.split("=");
            String schluessel = URLDecoder.decode(paarTeile[0], "utf-8");
            String wert = URLDecoder.decode(paarTeile[1], "utf-8");
            parameter.put(schluessel, wert);
        }

        return parameter;
    }

    private void verarbeiteHttpAntwort(HttpExchange exchange, EventAntwort antwort) {
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
