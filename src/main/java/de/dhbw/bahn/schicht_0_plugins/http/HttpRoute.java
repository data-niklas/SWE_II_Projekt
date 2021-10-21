package de.dhbw.bahn.schicht_0_plugins.http;

public class HttpRoute {
    private String pfad;
    private HttpAnfragemethode anfragemethode;

    public HttpRoute(String pfad, HttpAnfragemethode anfragemethode) {
        this.pfad = pfad;
        this.anfragemethode = anfragemethode;
    }

    public String holePfad() {
        return pfad;
    }

    public HttpAnfragemethode holeAnfragemethode() {
        return anfragemethode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.holeAnfragemethode().hashCode();
        hash = 31 * hash + (this.holePfad() == null ? 0 : this.holePfad().hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HttpRoute))
            return false;

        HttpRoute route2 = (HttpRoute) obj;
        return route2.holePfad().equals(this.holePfad()) && route2.holeAnfragemethode().equals(this.holeAnfragemethode());
    }
}
