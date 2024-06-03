package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.List;

public class GlavnaParcela {
    private long id;

    private double geografskaSirina;
    private double geografskaDuzina;

    private Korisnik vlasnik;

    private double humus;

    private BiljnaKultura poslednjaBiljnaKultura;

    private JacinaVetra ocekivanaJacinaVetra;

    private List<String> preporuke = new ArrayList<>();
    private List<String> preporukeGrupa = new ArrayList<>();
    private List<String> finalnaPreporuka = new ArrayList<>();
    public List<String> getFinalnaPreporuka() {
        return finalnaPreporuka;
    }   
    
    public List<String> getPreporukeGrupa() {
        return preporukeGrupa;
    }

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

    public List<String> getPreporuke() {
        return preporuke;
    }

    public GlavnaParcela(long id, double geografskaSirina, double geografskaDuzina, Korisnik vlasnik, double humus,
            BiljnaKultura poslednjaBiljnaKultura, JacinaVetra ocekivanaJacinaVetra) {
        this.id = id;
        this.geografskaSirina = geografskaSirina;
        this.geografskaDuzina = geografskaDuzina;
        this.vlasnik = vlasnik;
        this.humus = humus;
        this.poslednjaBiljnaKultura = poslednjaBiljnaKultura;
        this.ocekivanaJacinaVetra = ocekivanaJacinaVetra;
    }
    public GlavnaParcela(){
        
    }
}
