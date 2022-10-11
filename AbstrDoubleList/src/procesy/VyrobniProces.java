package procesy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import abstrdoublelist.AbstrDoubleList;
import abstrdoublelist.AbstrDoubleListException;
import abstrlifo.IAbstrLifo;
import enums.EPozice;
import enums.EReorg;

public class VyrobniProces implements IVyrobniProces {

    private AbstrDoubleList<Proces> data;

    public VyrobniProces() {
        data = new AbstrDoubleList<Proces>();
    }

    @Override
    public int importDat(String soubor) throws VyrobniProcesException, FileNotFoundException {
        if (soubor.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!Files.exists(Paths.get(soubor))) {
            throw new FileNotFoundException();
        }

        try (
                BufferedReader reader = new BufferedReader(new FileReader(soubor));
            ) {
            reader.readLine(); //skip hlavicky csv souboru
            String radek;
            int pocet = 0;

            while ((radek = reader.readLine()) != null) {
                String[] radekData = radek.split(";");
                Proces proces;

                if (radekData[0].charAt(0) == 'R') {
                    proces = new ProcesRoboticky(radekData[0], Integer.parseInt(radekData[2]));
                } else {
                    proces = new ProcesManualni(radekData[0], Integer.parseInt(radekData[1]), Integer.parseInt(radekData[2]));
                }
                
                data.vlozPosledni(proces);
                pocet++;
            }
            return pocet;
        } catch (IOException | NumberFormatException e) {
            data.zrus();
            throw new VyrobniProcesException("Importovana data ve spatnem formatu");
        }

    }

    @Override
    public void vlozProces(Proces proces, EPozice pozice) throws VyrobniProcesException {
        if (pozice == EPozice.AKTUALNI) {
            throw new IllegalArgumentException();
        }

        if (pozice == null) {
            throw new NullPointerException();
        }

        try {
            switch (pozice) {
                case NASLEDNIK -> {
                    data.vlozNaslednika(proces);
                }
                case POSLEDNI -> {
                    data.vlozPosledni(proces);
                }
                case PREDCHUDCE -> {
                    data.vlozPredchudce(proces);
                }
                case PRVNI -> {
                    data.vlozPosledni(proces);
                }
                case AKTUALNI -> {
                    // sem se to nikdy nedostane diky podmince na zacatku metody, 
                    // ale IDE se nelibi ze switch nema pri vyhodnocovani enumu vsechny pripady k dispozici
                }
            }
        } catch (AbstrDoubleListException e) {
            throw new VyrobniProcesException("Nelze vlozit naslednika/predchudce, protoze nebyl nastaven aktualni, nebo je seznam prazdny");
        }
        
    }

    @Override
    public Proces zpristupniProces(EPozice pozice) throws VyrobniProcesException {
        if (pozice == null) {
            throw new NullPointerException();
        }

        try {
            Proces proces = null;

            switch (pozice) {
                case NASLEDNIK -> {
                    proces = data.zpristupniNaslednika();
                }
                case POSLEDNI -> {
                    proces = data.zpristupniPosledni();
                }
                case PREDCHUDCE -> {
                    proces = data.zpristupniPredchudce();
                }
                case PRVNI -> {
                    proces = data.zpristupniPrvni();
                }
                case AKTUALNI -> {
                    proces = data.zpristupniAktualni();
                }
            }

            return proces;
        } catch (AbstrDoubleListException e) {
            throw new VyrobniProcesException("Nelze zpristupnit aktualni/naslednika/predchudce, protoze nebyl nastaven aktualni, nebo je seznam prazdny");
        }
    }

    @Override
    public Proces odeberProces(EPozice pozice) throws VyrobniProcesException {
        if (pozice == null) {
            throw new NullPointerException();
        }

        try {
            Proces proces = null;

            switch (pozice) {
                case NASLEDNIK -> {
                    proces = data.odeberNaslednika();
                }
                case POSLEDNI -> {
                    proces = data.odeberPosledni();
                }
                case PREDCHUDCE -> {
                    proces = data.odeberPredchudce();
                }
                case PRVNI -> {
                    proces = data.odeberPrvni();
                }
                case AKTUALNI -> {
                    proces = data.odeberAktualni();
                }
            }

            return proces;
        } catch (AbstrDoubleListException e) {
            throw new VyrobniProcesException("Nelze odebrat aktualni/naslednika/predchudce, protoze nebyl nastaven aktualni, nebo je seznam prazdny");
        }
    }

    @Override
    public Iterator<Proces> iterator() {
        return data.iterator();
    }

    @Override
    public IAbstrLifo<Proces> vytipujKandidatiReorg(int cas, EReorg reorgan) {
        /*
         * 
         * 
         */

        return null;
    }

    @Override
    public void reorganizace(EReorg reorgan, IAbstrLifo<Proces> zasobnik) {
        /*
         * 
         * 
         */

    }

    @Override
    public void zrus() {
        data.zrus();
    }
    
}
