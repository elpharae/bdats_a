package ads;

import java.util.Iterator;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private int pocet;
    private ListItem<T> prvni;
    private ListItem<T> aktualni;
    private ListItem<T> posledni;

    private static class ListItem<T> {

        private final T data;
        private ListItem<T> dalsi;

        public ListItem(T data) {
            this.data = data;
            this.dalsi = null;
        }
    }

    public AbstrDoubleList() {
        this.pocet = 0;
        this.prvni = null;
        this.aktualni = null;
        this.posledni = null;
    }

    @Override
    public void zrus() {
        this.pocet = 0;
        this.prvni = null;
        this.aktualni = null;
        this.posledni = null;
    }

    @Override
    public boolean jePrazdny() {
        return this.pocet == 0;
    }

    private void vlozDoPrazdneho(T data) {
        this.prvni = new ListItem<T>(data);
        this.prvni.dalsi = this.prvni;
        this.posledni = this.prvni;

        this.pocet++;
    }

    @Override
    public void vlozPrvni(T data) {
        if (this.pocet == 0) {
            vlozDoPrazdneho(data);
        } else { 
            ListItem<T> pom = new ListItem<T>(data);
            pom.dalsi = this.prvni;
            this.prvni = pom;
            this.pocet++;
        }
    }

    @Override
    public void vlozPosledni(T data) {
        if (this.pocet == 0) {
            vlozDoPrazdneho(data);
        } else {
            ListItem<T> pom = new ListItem<T>(data);
            this.posledni.dalsi = pom;
            pom.dalsi = this.prvni;
            this.posledni = pom;
            this.pocet++;
        }
    }

    @Override
    public void vlozNaslednika(T data) throws AbstrDoubleListException {
        if (this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> pom = this.aktualni.dalsi;
        this.aktualni = new ListItem<T>(data);
        this.aktualni.dalsi = pom;
        this.pocet++;
        
    }

    private ListItem<T> najdiPredchudce() throws AbstrDoubleListException {
        if (this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> pom = this.prvni;
        while (pom.dalsi.dalsi != this.aktualni) {
            pom = pom.dalsi;
        }
        return pom;
    }

    @Override
    public void vlozPredchudce(T data) throws AbstrDoubleListException {
        ListItem<T> predchudce = najdiPredchudce();
        ListItem<T> pom = new ListItem<T>(data);
        pom.dalsi = this.aktualni;
        predchudce.dalsi = pom;
        this.pocet++;
    }

    @Override
    public T zpristupniAktualni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }
        return this.aktualni.data;
    }

    @Override
    public T zpristupniPrvni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }
        return this.prvni.data;
    }

    @Override
    public T zpristupniPosledni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }
        return this.posledni.data;
    }

    @Override
    public T zpristupniNaslednika() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }
        return this.aktualni.dalsi.data;
    }

    @Override
    public T zpristupniPredchudce() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }
        return najdiPredchudce().data;
    }

    @Override
    public T odeberAktualni() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T odeberPrvni() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T odeberPosledni() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T odeberNaslednika() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T odeberPredchudce() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    


}
