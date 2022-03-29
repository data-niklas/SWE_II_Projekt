package de.dhbw.bahn.schicht_0_plugins.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.dhbw.bahn.schicht_1_adapter.http.Darstellung;
import de.dhbw.bahn.schicht_1_adapter.http.Event;
import de.dhbw.bahn.schicht_1_adapter.http.EventAntwort;
import de.dhbw.bahn.schicht_1_adapter.http.EventRueckruf;
import de.dhbw.bahn.schicht_1_adapter.http.MimeTyp;
import de.dhbw.bahn.schicht_1_adapter.http.EventTyp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class EinfacherHttpServer implements Darstellung, HttpHandler {

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

    private EventTyp httpMethodeZuEventTyp(String httpMethode) {
        switch (httpMethode) {
        case "GET":
            return EventTyp.LESEN;
        case "PUT":
            return EventTyp.AKTUALISIEREN;
        case "POST":
            return EventTyp.ERSTELLEN;
        case "DELETE":
            return EventTyp.LOESCHEN;
        default:
            return null;
        }
    }

    private EventAntwort verarbeiteAnfrage(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestMethod() + " Anfrage an " + exchange.getHttpContext().getPath());

        String pfad = exchange.getHttpContext().getPath();

        EventTyp eventTyp = httpMethodeZuEventTyp(exchange.getRequestMethod());
        if (eventTyp == null)
            return new EventAntwort(404, "Diese Anfragemethode wird nicht unterstützt", MimeTyp.SCHLICHT);
        Event route = new Event(pfad, eventTyp);
        if (!this.rueckrufTabelle.containsKey(route)) {
            return new EventAntwort(404, "Not Found", MimeTyp.SCHLICHT);
        }
        EventRueckruf rueckruf = this.rueckrufTabelle.get(route);

        String query = exchange.getRequestURI().getQuery();
        Map<String, String> parameter = leseParameter(query);
        String koerper = leseKoerper(exchange.getRequestBody());

        return rueckruf.bearbeiteAnfrage(route, koerper, parameter);
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
        for (int ch; (ch = reader.read()) != -1;) {
            stringBuilder.append((char) ch);
        }
        return stringBuilder.toString();
    }
}
