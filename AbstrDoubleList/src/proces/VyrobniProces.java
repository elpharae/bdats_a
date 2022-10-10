package proces;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import ads.AbstrDoubleList;
import ads.AbstrDoubleListException;
import ads.IAbstrLifo;
import enums.EPozice;
import enums.EReorg;

public class VyrobniProces implements IVyrobniProces {

    private AbstrDoubleList<Proces> data;

    public VyrobniProces() {
        data = new AbstrDoubleList<Proces>();
    }

    @Override
    public int importDat(String soubor) {
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
                    proces = new ProcesRoboticky(radekData[0], Integer.parseInt(radekData[1]));
                } else {
                    proces = new ProcesManualni(radekData[0], Integer.parseInt(radekData[1]), Integer.parseInt(radekData[2]));
                }
                
                data.vlozPosledni(proces);
                pocet++;
            }
            return pocet;
        } catch (IOException e) {
            return 0;
        }

    }

    @Override
    public void vlozProces(Proces proces, EPozice pozice) throws VyrobniProcesException, AbstrDoubleListException {
        if (pozice == null) {
            throw new VyrobniProcesException();
        }

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
                throw new VyrobniProcesException();
            }
        }
    }

    @Override
    public Proces zpristupniProces(EPozice pozice) throws AbstrDoubleListException, VyrobniProcesException {
        if (pozice == null) {
            throw new VyrobniProcesException();
        }

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
    }

    @Override
    public Proces odeberProces(EPozice pozice) throws VyrobniProcesException, AbstrDoubleListException {
        if (pozice == null) {
            throw new VyrobniProcesException();
        }

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
    }

    @Override
    public Iterator iterator() {
        return data.iterator();
    }

    @Override
    public IAbstrLifo vytipujKandidatiReorg(int cas, EReorg reorgan) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reorganizace(EReorg reorgan, IAbstrLifo zasobnik) {
        
    }

    @Override
    public void zrus() {
        data.zrus();
    }
    
}
