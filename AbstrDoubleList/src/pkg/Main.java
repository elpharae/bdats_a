package pkg;

import java.io.FileNotFoundException;
import java.util.Iterator;
import abstrdoublelist.AbstrDoubleList;
import abstrdoublelist.AbstrDoubleListException;
import procesy.Proces;
import procesy.VyrobniProces;
import procesy.VyrobniProcesException;

public class Main {

    public static void main(String[] args) throws AbstrDoubleListException {
        
        VyrobniProces importovane = new VyrobniProces();
        VyrobniProces generovane = new VyrobniProces();
        
        try {

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

        } catch (VyrobniProcesException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor neexistuje");
        }
        


    }

}
