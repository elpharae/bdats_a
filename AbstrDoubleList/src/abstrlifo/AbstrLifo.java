package abstrlifo;

import abstrdoublelist.AbstrDoubleList;
import abstrdoublelist.AbstrDoubleListException;

public class AbstrLifo<T> implements IAbstrLifo<T> {

    private AbstrDoubleList<T> struktura;

    public AbstrLifo() {
        struktura = new AbstrDoubleList<T>();
    }

    @Override
    public void zrus() {
        struktura.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return struktura.jePrazdny();
    }

    @Override
    public void vloz(T data) throws AbstrLifoException {
        if (data == null) {
            throw new NullPointerException();
        }

        try {
            struktura.vlozPosledni(data);
            struktura.zpristupniPosledni();
        } catch (AbstrDoubleListException e) {
            // sem by se to v pripade zadnych chyb v kodu nemelo nikdy dostat?
            // metoda zpristupniPosledni vyusti v chybu pouze tehdy, je-li seznam prazdny,
            // coz nikdy nebude v pripade, ze hned pred jejim zavolanim vlozim do seznamu data
            throw new AbstrLifoException("Chyba zasobniku");
        }
    }

    @Override
    public T odeber() throws AbstrLifoException {
        try {
            T data = struktura.odeberPosledni();
            struktura.zpristupniPosledni();
            return data;
        } catch (AbstrDoubleListException e) {
            throw new AbstrLifoException("Zasobnik je prazdny");
        }
    }
    
}
