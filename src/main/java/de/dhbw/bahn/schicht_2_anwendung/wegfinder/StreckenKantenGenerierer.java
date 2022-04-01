package de.dhbw.bahn.schicht_2_anwendung.wegfinder;

import de.dhbw.bahn.schicht_3_domaene.Strecke;

public interface StreckenKantenGenerierer {
    StreckenKante generiereStreckenKante(Strecke strecke, BahnhofsKnoten startBahnhof, BahnhofsKnoten endBahnhof);
}
