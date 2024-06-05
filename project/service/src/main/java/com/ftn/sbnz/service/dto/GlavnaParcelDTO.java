package com.ftn.sbnz.service.dto;

import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.model.models.JacinaVetra;
import com.ftn.sbnz.model.models.Korisnik;

public class GlavnaParcelDTO {
    private long id;

    private double geografskaSirina;
    private double geografskaDuzina;

    private Korisnik vlasnik;

    private double humus;

    private BiljnaKultura poslednjaBiljnaKultura;

    private JacinaVetra ocekivanaJacinaVetra;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getGeografskaSirina() {
        return geografskaSirina;
    }

    public void setGeografskaSirina(double geografskaSirina) {
        this.geografskaSirina = geografskaSirina;
    }

    public double getGeografskaDuzina() {
        return geografskaDuzina;
    }

    public void setGeografskaDuzina(double geografskaDuzina) {
        this.geografskaDuzina = geografskaDuzina;
    }

    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    public double getHumus() {
        return humus;
    }

    public void setHumus(double humus) {
        this.humus = humus;
    }

    public BiljnaKultura getPoslednjaBiljnaKultura() {
        return poslednjaBiljnaKultura;
    }

    public void setPoslednjaBiljnaKultura(BiljnaKultura poslednjaBiljnaKultura) {
        this.poslednjaBiljnaKultura = poslednjaBiljnaKultura;
    }

    public JacinaVetra getOcekivanaJacinaVetra() {
        return ocekivanaJacinaVetra;
    }

    public void setOcekivanaJacinaVetra(JacinaVetra ocekivanaJacinaVetra) {
        this.ocekivanaJacinaVetra = ocekivanaJacinaVetra;
    }

    public GlavnaParcelDTO(long id, double geografskaSirina, double geografskaDuzina, Korisnik vlasnik, double humus,
            BiljnaKultura poslednjaBiljnaKultura, JacinaVetra ocekivanaJacinaVetra) {
        this.id = id;
        this.geografskaSirina = geografskaSirina;
        this.geografskaDuzina = geografskaDuzina;
        this.vlasnik = vlasnik;
        this.humus = humus;
        this.poslednjaBiljnaKultura = poslednjaBiljnaKultura;
        this.ocekivanaJacinaVetra = ocekivanaJacinaVetra;
    }

    public GlavnaParcelDTO() {
    }

    

    
}
