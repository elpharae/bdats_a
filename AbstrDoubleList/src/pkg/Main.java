package pkg;

import java.io.FileNotFoundException;
import java.util.Iterator;
import abstrdoublelist.AbstrDoubleListException;
import procesy.Proces;
import procesy.VyrobniProces;
import procesy.VyrobniProcesException;

public class Main {

    public static void main(String[] args) throws AbstrDoubleListException {
        VyrobniProces proces = new VyrobniProces();

        try {
            
            System.out.println(proces.importDat("./import.csv"));
            Iterator<Proces> it = proces.iterator();

            while (it.hasNext()) {
                System.out.println(it.next());
            }

        } catch (VyrobniProcesException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor neexistuje");
        }
        


    }

}
