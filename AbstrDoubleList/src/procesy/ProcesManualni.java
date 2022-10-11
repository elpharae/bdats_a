package procesy;

public class ProcesManualni extends Proces {
    
    private int pocetOsob;

    public ProcesManualni(String id, int pocetOsob, int casProcesu) {
        super(id, casProcesu);

        if (pocetOsob < 0) {
            throw new IllegalArgumentException();
        }

        this.pocetOsob = pocetOsob;
    }

    public int getPocetOsob() {
        return pocetOsob;
    }

    public void setPocetOsob(int pocetOsob) {
        this.pocetOsob = pocetOsob;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("; Pocet osob: %d", this.pocetOsob);
    }
    
}
