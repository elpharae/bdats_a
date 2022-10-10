package proces;

import java.io.Serializable;

public abstract class Proces implements Serializable {
    
    private String id;
    private int casProcesu;

    public Proces(String id, int casProcesu) {
        this.id = id;
        this.casProcesu = casProcesu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCasProcesu() {
        return casProcesu;
    }

    public void setCasProcesu(int casProcesu) {
        this.casProcesu = casProcesu;
    }
    
}
