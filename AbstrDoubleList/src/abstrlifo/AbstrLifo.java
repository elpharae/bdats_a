package abstrlifo;

import java.util.Iterator;
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
    public void vloz(T data) {
        if (data == null) {
            throw new NullPointerException();
        }

        try {
            struktura.vlozPosledni(data);
            struktura.zpristupniPosledni();
        } catch (AbstrDoubleListException e) {
            // sem se to nikdy nedostane
            // metoda zpristupniPosledni vyusti v chybu pouze tehdy, je-li seznam prazdny,
            // coz nikdy nebude v pripade, ze hned pred jejim zavolanim vlozim do seznamu data
        }
    }

    @Override
    public T odeber() throws AbstrLifoException {
        try {
            return struktura.odeberPosledni();
        } catch (AbstrDoubleListException e) {
            throw new AbstrLifoException("Zasobnik je prazdny");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return struktura.iterator();
        /*
        return new Iterator<T>() {

            private AbstrDoubleList<T> iteratorData = struktura;


            @Override
            public boolean hasNext() {
                return !iteratorData.jePrazdny();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    return null;
                }

                try {
                    return iteratorData.odeberPosledni();
                } catch (AbstrDoubleListException e) {
                    // sem se to nikdy nedostane
                    return null;
                }
            }
        };
         */
    }
    
}
