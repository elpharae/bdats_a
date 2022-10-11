package abstrlifo;

public interface IAbstrLifo<T> {

    void zrus();
    boolean jePrazdny();

    void vloz(T data) throws AbstrLifoException;
    T odeber() throws AbstrLifoException;

}
