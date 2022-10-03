package ads;

import java.util.Iterator;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    /*
     * Otázky na konzultaci:
     * 
     * 1. V rozhraní není definována metoda na nastavení aktuálního prvku. Jak se vybírá aktuální prvek?
     * 2. Metody vlozX - Inicializují aktuální prvek?
     * 
     * Metoda odeberPrvni
     *  3. Po odebrání prvního prvku se druhý prvek v pořadí stane prvním?
     *  4. Pokud první je zároveň aktuální, co se stane s aktuálním?
     * 
     * Metoda odeberPosledni 
     *  5. Po odebrání posledního prvku se předposlední prvek v pořadí stane posledním?
     *  6. Pokud poslední je zároveň aktuální, co se stane s aktuálním?
     * 
     * Metoda odeberNaslednika a odeberPredchudce:
     *  7. Pokud je v seznamu pouze 1 prvek, co se má stát?
     * 
     * 8. Jednosměrný iterátor pro obousměrný list?
     * 9. List je cyklicky zřetězený, nebude metoda hasNext vždy vracet true?
     */

    private int pocet;
    private ListItem<T> prvni;
    private ListItem<T> aktualni;
    private ListItem<T> posledni;

    private static class ListItem<T> {

        private final T data;
        private ListItem<T> dalsi;
        private ListItem<T> predchozi;

        public ListItem(T data) {
            this.data = data;
            this.predchozi = null;
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
        this.pocet++;

        this.prvni = new ListItem<T>(data);
        this.prvni.predchozi = this.prvni;
        this.prvni.dalsi = this.prvni;
        this.posledni = this.prvni;
    }

    @Override
    public void vlozPrvni(T data) {
        if (this.pocet == 0) {
            vlozDoPrazdneho(data);
        } else {
            this.pocet++;
            ListItem<T> vkladany = new ListItem<T>(data);
            ListItem<T> pred = this.posledni;
            ListItem<T> po = this.prvni;

            vkladany.dalsi = po;
            po.predchozi = vkladany;
            vkladany.predchozi = pred;
            pred.dalsi = vkladany;

            this.prvni = vkladany;
        }
    }

    @Override
    public void vlozPosledni(T data) {
        if (this.pocet == 0) {
            vlozDoPrazdneho(data);
        } else {
            this.pocet++;
            ListItem<T> vkladany = new ListItem<T>(data);
            ListItem<T> pred = this.posledni;
            ListItem<T> po = this.prvni;

            vkladany.dalsi = po;
            po.predchozi = vkladany;
            vkladany.predchozi = pred;
            pred.dalsi = vkladany;

            this.posledni = vkladany;
        }
    }

    @Override
    public void vlozNaslednika(T data) throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        if (this.aktualni == this.posledni) {
            vlozPosledni(data);
        } else {
            this.pocet++;
            ListItem<T> vkladany = new ListItem<T>(data);
            ListItem<T> pred = this.aktualni;
            ListItem<T> po = this.aktualni.dalsi;

            vkladany.dalsi = po;
            po.predchozi = vkladany;
            vkladany.predchozi = pred;
            pred.dalsi = vkladany;
        }
    }

    @Override
    public void vlozPredchudce(T data) throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        if (this.aktualni == this.prvni) {
            vlozPrvni(data);
        } else {
            this.pocet++;
            ListItem<T> vkladany = new ListItem<T>(data);
            ListItem<T> pred = this.aktualni.predchozi;
            ListItem<T> po = this.aktualni;

            vkladany.dalsi = po;
            po.predchozi = vkladany;
            vkladany.predchozi = pred;
            pred.dalsi = vkladany;
        }
    }

    @Override
    public T zpristupniAktualni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> vybrany = this.aktualni;
        return vybrany.data;
    }

    @Override
    public T zpristupniPrvni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.prvni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> vybrany = this.prvni;
        return vybrany.data;
    }

    @Override
    public T zpristupniPosledni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.posledni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> vybrany = this.posledni;
        return vybrany.data;
    }

    @Override
    public T zpristupniNaslednika() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> vybrany = this.aktualni.dalsi;
        return vybrany.data;
    }

    @Override
    public T zpristupniPredchudce() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        ListItem<T> vybrany = this.aktualni.dalsi;
        return vybrany.data;
    }

    @Override
    public T odeberAktualni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        this.pocet--;

        if (this.aktualni == this.prvni) {
            T odebirany = odeberPrvni();
            this.aktualni = this.prvni;
            return odebirany;
        }

        if (this.aktualni == this.posledni) {
            T odebirany = odeberPosledni();
            this.aktualni = this.prvni;
            return odebirany;
        }

        ListItem<T> odebirany = this.aktualni;
        ListItem<T> pred = this.aktualni.predchozi;
        ListItem<T> po = this.aktualni.dalsi;

        pred.dalsi = po;
        po.predchozi = pred;

        this.aktualni = this.prvni;
        return odebirany.data;
    }

    @Override
    public T odeberPrvni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.prvni == null) {
            throw new AbstrDoubleListException();
        }

        if (this.prvni == this.aktualni)
            this.aktualni = null;

        this.pocet--;
        ListItem<T> odebirany = this.prvni;
        ListItem<T> pred = this.prvni.predchozi;
        ListItem<T> po = this.prvni.dalsi;

        pred.dalsi = po;
        po.predchozi = pred;

        this.prvni = po; // v případě, že po odebrání prvního se druhý stane prvním, viz otázka

        return odebirany.data;
    }

    @Override
    public T odeberPosledni() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.posledni == null) {
            throw new AbstrDoubleListException();
        }

        this.pocet--;
        ListItem<T> odebirany = this.posledni;
        ListItem<T> pred = this.posledni.predchozi;
        ListItem<T> po = this.posledni.dalsi;

        pred.dalsi = po;
        po.predchozi = pred;

        this.posledni = pred; // v případě, že po odebrání posledního se předposlední stane posledním, viz
                              // otázka

        return odebirany.data;

    }

    @Override
    public T odeberNaslednika() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        if (this.aktualni.dalsi == this.posledni) {
            return odeberPosledni();
        }

        if (this.aktualni.dalsi == this.prvni) {
            return odeberPrvni();
        }

        this.pocet--;
        ListItem<T> odebirany = this.aktualni.dalsi;
        ListItem<T> pred = this.aktualni;
        ListItem<T> po = this.aktualni.dalsi.dalsi;

        pred.dalsi = po;
        po.predchozi = pred;

        return odebirany.data;
    }

    @Override
    public T odeberPredchudce() throws AbstrDoubleListException {
        if (this.pocet == 0 || this.aktualni == null) {
            throw new AbstrDoubleListException();
        }

        if (this.aktualni.predchozi == this.posledni) {
            return odeberPosledni();
        }

        if (this.aktualni.predchozi == this.prvni) {
            return odeberPrvni();
        }

        this.pocet--;
        ListItem<T> odebirany = this.aktualni.predchozi;
        ListItem<T> pred = this.aktualni.predchozi.predchozi;
        ListItem<T> po = this.aktualni;

        pred.dalsi = po;
        po.predchozi = pred;

        return odebirany.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {


            @Override
            public boolean hasNext() {

            }

            @Override
            public T next() {
                
            }

        };
    }

}
