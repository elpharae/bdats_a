package ads;

public interface IAbstrLifo<T> {

    void zrus();
    boolean jePrazdny();

    void vloz(T data) throws AbstrDoubleListException;
    T odeber() throws AbstrDoubleListException;

}
