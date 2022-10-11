package pkg;

import java.util.Random;

import abstrdoublelist.AbstrDoubleList;
import procesy.Proces;
import procesy.ProcesManualni;
import procesy.ProcesRoboticky;

public class DataGenerator {
    
    private static Random RND = new Random();

    // ciselne hranice jsem urcil podle vzoroveho souboru import.csv
    public static final int MIN_POCET_OSOB = 2;
    public static final int MAX_POCET_OSOB = 8;
    public static final int MIN_CAS_PROCESU = 2;
    public static final int MAX_CAS_PROCESU = 30;

    public static AbstrDoubleList<Proces> nahodneGenerovanaData(int pocet) {
        if (pocet <= 0 || pocet > 50) {
            throw new IllegalArgumentException();
        }

        AbstrDoubleList<Proces> data = new AbstrDoubleList<Proces>();

        Proces proces;
        String id;
        int pocetOsob;
        int casProcesu;

        int pocetManual = 0;
        int pocetRobot = 0;

        for (int i = 0; i < pocet; i++) {
            pocetOsob = RND.nextInt(MAX_POCET_OSOB - MIN_POCET_OSOB) + MIN_POCET_OSOB;
            casProcesu = RND.nextInt(MAX_CAS_PROCESU - MIN_CAS_PROCESU) + MIN_CAS_PROCESU;

            if (RND.nextInt(2) >= 1) {
                pocetManual++;

                id = "O1" + "0".repeat(2 - Integer.toString(pocetManual).length()) + pocetManual;
                proces = new ProcesManualni(id, pocetOsob, casProcesu);
                data.vlozPosledni(proces);
            } else {
                pocetRobot++;
            
                id = "R1" + "0".repeat(2 - Integer.toString(pocetRobot).length()) + pocetRobot;
                proces = new ProcesRoboticky(id, casProcesu);
                data.vlozPosledni(proces);
            }
        }

        return data;
    }

}
