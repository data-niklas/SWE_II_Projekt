# SWE_II_Projekt
## HTTP - REST - API
- Zugsystem

## Voraussetzungen
- Es gibt keine Höhe
- Keine Umstiege

### Entitäten
- Zug
    - Attribute
        - Höchstgeschwindigkeit
        - Zugtyp
        - 
    - ElektroZug
    - DampfZug
    - SpritZug
- ZugTyp (Enum)
    - ICE
    - IC
    - IRE
    - IR
- Bahnhof
    - Ist an Koordinaten
    - Hat Strecken
- Strecke
    - Zulässige Höchstgeschwindigkeit
    - Erlaubte Zugtypen
    - Ist die Strecke blockiert? (Bauarbeiten etc.)
    - Distanz
    - Startbahnhof und Endbahnhof

### Use-Cases
#### Bearbeiten von Entitäten
- Bearbeiten von Zügen
- Bearbeiten von Bahnhöfen
- Bearbeiten von Strecken
- Bearbeiten = CRUD

#### Routenplanung von Bahnhof A nach B
- Kürzester zulässiger Weg auf dem Streckengraphen suchen
- Schnellste zulässige Strecke