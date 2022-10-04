package ads;

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
    public void vloz(T data) throws AbstrDoubleListException {
        struktura.vlozPosledni(data);
        struktura.zpristupniPosledni();
    }

    @Override
    public T odeber() throws AbstrDoubleListException {
        T data = struktura.odeberPosledni();
        struktura.zpristupniPosledni();
        return data;
    }
    
}
