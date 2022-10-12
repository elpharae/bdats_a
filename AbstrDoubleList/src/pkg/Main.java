package pkg;

import java.io.FileNotFoundException;
import java.util.Iterator;

import abstrlifo.AbstrLifo;
import enums.EReorg;
import procesy.Proces;
import procesy.ProcesManualni;
import procesy.VyrobniProces;
import procesy.VyrobniProcesException;

public class Main {

    public static void main(String[] args) {
        
        VyrobniProces importovane = new VyrobniProces();
        VyrobniProces generovane = new VyrobniProces();
        
        try {
            /* 
            generovane.generatorDat(21);
            Iterator<Proces> genIt = generovane.iterator();
            System.out.println("Generovane:");
            while (genIt.hasNext()) {
                System.out.println(genIt.next());
            }

            importovane.importDat("./import.csv");
            Iterator<Proces> impIt = importovane.iterator();
            System.out.println("Importovane:");
            while (impIt.hasNext()) {
                System.out.println(impIt.next());
            }
            */

            importovane.importDat("./import.csv");
            Iterator<Proces> impIt = importovane.iterator();
            System.out.println("Pred:");
            while (impIt.hasNext()) {
                if (impIt.next() instanceof ProcesManualni proces) System.out.println(proces);
            }

            AbstrLifo<Proces> zasobnik = (AbstrLifo<Proces>) importovane.vytipujKandidatiReorg(15, EReorg.DEKOMPOZICE);
            Iterator<Proces> zasobnikIt = zasobnik.iterator();
            System.out.println("Kandidati:");
            while (zasobnikIt.hasNext()) {
                System.out.println(zasobnikIt.next());
            }
            importovane.reorganizace(EReorg.DEKOMPOZICE, zasobnik);

            impIt = importovane.iterator();
            System.out.println("Po:");
            while (impIt.hasNext()) {
                if (impIt.next() instanceof ProcesManualni proces) System.out.println(proces);
            }

        } catch (VyrobniProcesException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor neexistuje");
        }
        
    }

}
