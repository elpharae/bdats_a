package proces;

import java.util.Iterator;

import ads.AbstrDoubleListException;
import ads.IAbstrLifo;
import enums.EPozice;
import enums.EReorg;

public interface IVyrobniProces {
    
    int importDat(String soubor);
    void vlozProces(Proces proces, EPozice pozice) throws VyrobniProcesException, AbstrDoubleListException;
    
    Proces zpristupniProces(EPozice pozice) throws AbstrDoubleListException, VyrobniProcesException;
    Proces odeberProces(EPozice pozice) throws VyrobniProcesException, AbstrDoubleListException;

    Iterator iterator();
    IAbstrLifo vytipujKandidatiReorg(int cas, EReorg reorgan);

    void reorganizace(EReorg reorgan, IAbstrLifo zasobnik);
    void zrus();

}
