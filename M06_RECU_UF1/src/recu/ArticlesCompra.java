package recu;

import java.io.Serializable;

public class ArticlesCompra implements Serializable{
    private String descripcio;
    private double quantitat;
    private String unitat;
    private String seccio;
    private double preu;

    // Consrtuctors
    public ArticlesCompra() {
    }

    public ArticlesCompra(String descripcio, double quantitat, String unitat, String seccio, double preu) {
        this.descripcio = descripcio;
        this.quantitat = quantitat;
        this.unitat = unitat;
        this.seccio = seccio;
        this.preu = preu;
    }

    // Getters i setters
    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(double quantitat) {
        this.quantitat = quantitat;
    }

    public String getUnitat() {
        return unitat;
    }

    public void setUnitat(String unitat) {
        this.unitat = unitat;
    }

    public String getSeccio() {
        return seccio;
    }

    public void setSeccio(String seccio) {
        this.seccio = seccio;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public double getPreu() {
        return preu;
    }
}
