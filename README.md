# SWE_II_Projekt

## HTTP Anfragen

## HTTP - REST - API
| Pfad                      | Methode | Beschreibung                                              |
|---------------------------|---------|-----------------------------------------------------------|
| /bahnhof                  | GET     | Anfrage nach einer Liste aller Bahnhoefe                  |
| /bahnhof?id=name          | GET     | Anfrage nach Details zu einem Bahnhof mit dem Namen       |
| /bahnhof                  | POST    | Hinzufuegen eines neuen Bahnhofs                          |
| /bahnhof                  | PUT     | Aktualisieren der Details eines Bahnhofs                  |
| /bahnhof?id=name          | DELETE  | Loeschen eines Banhofs mit dem Namen                      |
|                           |         |                                                           |
| /strecke                  | GET     | Anfrage nach einer Liste aller Strecken                   |
| /strecke?id=bezeichnung   | GET     | Anfrage nach Details zu einer Strecke mit der Bezeichnung |
| /strecke                  | POST    | Hinzufuegen einer neuen Strecke                           |
| /strecke                  | PUT     | Aktualisieren der Details einer Strecke                   |
| /strecke?id=bezeichnung   | DELETE  | Loeschen der Strecke mit der Bezeichnung                  |
|                           |         |                                                           |
| /zug                      | GET     | Anfrage nach einer Liste aller Zuege                      |
| /zug?id=zugnummer         | GET     | Anfrage nach Details zu einem Zug mit der Zugnummer       |
| /zug                      | POST    | Hinzufuegen eines neuen Zuges                             |
| /zug                      | PUT     | Aktualisieren der Details eines Zuges                     |
| /zug?id=zugnummer         | DELETE  | Loeschen des Zuges mit der Zugnummer                      |
|                           |         |                                                           |
| /kuerzester-weg?start=startbahnhof&ziel=zielbahnhof&zug=zugnummer | GET      | Berechne den kuerzesten Weg zwischen dem Startbahnhof und dem Zielbahnhof mit dem Zug mit der Zugnummer |
| /schnellster-weg?start=startbahnhof&ziel=zielbahnhof&zug=zugnummer | GET      | Berechne den schnellsten Weg zwischen dem Startbahnhof und dem Zielbahnhof mit dem Zug mit der Zugnummer |
|                           |         |                                                           |

## Voraussetzungen

- Es gibt keine H??he
- Keine Umstiege

### Entit??ten

- Zug
    - Attribute
        - H??chstgeschwindigkeit
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
    - Zul??ssige H??chstgeschwindigkeit
    - Erlaubte Zugtypen
    - Ist die Strecke blockiert? (Bauarbeiten etc.)
    - Distanz
    - Startbahnhof und Endbahnhof

### Use-Cases

#### Bearbeiten von Entit??ten

- Bearbeiten von Z??gen
- Bearbeiten von Bahnh??fen
- Bearbeiten von Strecken
- Bearbeiten = CRUD

#### Routenplanung von Bahnhof A nach B

- K??rzester zul??ssiger Weg auf dem Streckengraphen suchen
- Schnellste zul??ssige Strecke