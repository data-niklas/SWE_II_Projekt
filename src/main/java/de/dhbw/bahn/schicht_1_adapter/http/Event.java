package de.dhbw.bahn.schicht_1_adapter.http;

public class Event {
    private final String name;
    private final EventTyp typ;

    public Event(String name, EventTyp typ) {
        this.name = name;
        this.typ = typ;
    }

    public String holeName() {
        return name;
    }

    public EventTyp holeTyp() {
        return typ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.holeTyp().hashCode();
        hash = 31 * hash + (this.holeName() == null ? 0 : this.holeName().hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event))
            return false;

        Event route2 = (Event) obj;
        return route2.holeName().equals(this.holeName()) && route2.holeTyp().equals(this.holeTyp());
    }
}
