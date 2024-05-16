package com.ftn.sbnz.model.models;

import java.util.UUID;

public class SporednaParcela {
    private UUID id;
    private Korisnik vlasnik;
    private GrupaZrenja planiranaGrupaZrenja;


    public SporednaParcela(Korisnik vlasnik, GrupaZrenja planiranaGrupaZrenja) {
        this.vlasnik = vlasnik;
        this.planiranaGrupaZrenja = planiranaGrupaZrenja;
    }
    public UUID getId() {
        return id;
    }
    public Korisnik getVlasnik() {
        return vlasnik;
    }
    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }
    public GrupaZrenja getPlaniranaGrupaZrenja() {
        return planiranaGrupaZrenja;
    }
    public void setPlaniranaGrupaZrenja(GrupaZrenja planiranaGrupaZrenja) {
        this.planiranaGrupaZrenja = planiranaGrupaZrenja;
    }
    
}
