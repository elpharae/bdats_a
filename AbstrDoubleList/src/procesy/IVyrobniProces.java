package procesy;

import java.io.FileNotFoundException;
import java.util.Iterator;
import abstrlifo.IAbstrLifo;
import enums.EPozice;
import enums.EReorg;

public interface IVyrobniProces extends Iterable<Proces> {
    
    int importDat(String soubor) throws VyrobniProcesException, FileNotFoundException;
    void vlozProces(Proces proces, EPozice pozice) throws VyrobniProcesException;
    
    Proces zpristupniProces(EPozice pozice) throws VyrobniProcesException;
    Proces odeberProces(EPozice pozice) throws VyrobniProcesException;

    Iterator<Proces> iterator();
    IAbstrLifo<Proces> vytipujKandidatiReorg(int cas, EReorg reorgan);

    void reorganizace(EReorg reorgan, IAbstrLifo<Proces> zasobnik);
    void zrus();

}
