package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.List;

public class GlavnaParcela {
    private long id;

    private double geografskaSirina;
    private double geografskaDuzina;

    private double humus;

    private JacinaVetra ocekivanaJacinaVetra;

    private List<Hibrid> preporuke = new ArrayList<>();

    public void obrisiPreporuke(){
        this.preporuke.clear();
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

    public double getHumus() {
        return humus;
    }

    public void setHumus(double humus) {
        this.humus = humus;
    }

    public JacinaVetra getOcekivanaJacinaVetra() {
        return ocekivanaJacinaVetra;
    }

    public void setOcekivanaJacinaVetra(JacinaVetra ocekivanaJacinaVetra) {
        this.ocekivanaJacinaVetra = ocekivanaJacinaVetra;
    }

    public List<Hibrid> getPreporuke() {
        return preporuke;
    }

    public GlavnaParcela(long id, double geografskaSirina, double geografskaDuzina, double humus, JacinaVetra ocekivanaJacinaVetra) {
        this.id = id;
        this.geografskaSirina = geografskaSirina;
        this.geografskaDuzina = geografskaDuzina;
        this.humus = humus;
        this.ocekivanaJacinaVetra = ocekivanaJacinaVetra;
    }
    public GlavnaParcela(){
        
    }
}
