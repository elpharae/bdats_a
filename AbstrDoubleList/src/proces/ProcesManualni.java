package proces;

public class ProcesManualni extends Proces {
    
    private int pocetOsob;

    public ProcesManualni(String id, int pocetOsob, int casProcesu) {
        super(id, casProcesu);
        this.pocetOsob = pocetOsob;
    }

    public int getPocetOsob() {
        return pocetOsob;
    }

    public void setPocetOsob(int pocetOsob) {
        this.pocetOsob = pocetOsob;
    }
    
}
