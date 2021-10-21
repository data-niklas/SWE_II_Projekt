package de.dhbw.bahn;

import de.dhbw.bahn.schicht_0_plugins.http.*;

public class Main {

    public static void main(String[] args) {
        HttpServer server = new EinfacherHttpServer();
        server.registriereHttpRueckruf(new HttpRoute("/hello", HttpAnfragemethode.GET), (route, koerper) -> {
            System.out.println("Bearbeitet Anfrage");
            return new HttpAntwort(200, "HALLO", "text");
        });

        server.registriereHttpRueckruf(new HttpRoute("/echo", HttpAnfragemethode.POST), (route, koerper) -> {
            System.out.println("Bearbeitet POST Anfrage");
            return new HttpAntwort(201, koerper, "text");
        });

        server.legeLos("localhost", 8080);

    }
}
