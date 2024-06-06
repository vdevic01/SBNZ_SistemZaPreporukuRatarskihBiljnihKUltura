package com.ftn.sbnz.model.models;

public class PreferencaProizvodjaca {
    private long parcelaId;
    private Proizvodjac proizvodjac;
    public long getParcelaId() {
        return parcelaId;
    }
    public void setParcelaId(long parcelaId) {
        this.parcelaId = parcelaId;
    }
    public Proizvodjac getProizvodjac() {
        return proizvodjac;
    }
    public void setProizvodjac(Proizvodjac proizvodjac) {
        this.proizvodjac = proizvodjac;
    }
    public PreferencaProizvodjaca() {
    }
    public PreferencaProizvodjaca(long parcelaId, Proizvodjac proizvodjac) {
        this.parcelaId = parcelaId;
        this.proizvodjac = proizvodjac;
    }
}
