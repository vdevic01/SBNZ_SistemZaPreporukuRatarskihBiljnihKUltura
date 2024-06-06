package com.ftn.sbnz.model.models;

public class Hibrid {
    private Proizvodjac proizvodjac;
    private String naziv;
    private BiljnaKultura biljnaKultura;
    public BiljnaKultura getBiljnaKultura() {
        return biljnaKultura;
    }
    public void setBiljnaKultura(BiljnaKultura biljnaKultura) {
        this.biljnaKultura = biljnaKultura;
    }
    public Proizvodjac getProizvodjac() {
        return proizvodjac;
    }
    public void setProizvodjac(Proizvodjac proizvodjac) {
        this.proizvodjac = proizvodjac;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Hibrid(BiljnaKultura biljnaKultura, Proizvodjac proizvodjac, String naziv) {
        this.biljnaKultura = biljnaKultura;
        this.proizvodjac = proizvodjac;
        this.naziv = naziv;
    }
}
