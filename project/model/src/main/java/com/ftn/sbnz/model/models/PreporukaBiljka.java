package com.ftn.sbnz.model.models;

public class PreporukaBiljka{
    private BiljnaKultura kultura;
    private GlavnaParcela glavnaParcela;
    public BiljnaKultura getKultura() {
        return kultura;
    }
    public void setKultura(BiljnaKultura kultura) {
        this.kultura = kultura;
    }
    public GlavnaParcela getGlavnaParcela() {
        return glavnaParcela;
    }
    public void setGlavnaParcela(GlavnaParcela glavnaParcela) {
        this.glavnaParcela = glavnaParcela;
    }
    public PreporukaBiljka(BiljnaKultura kultura, GlavnaParcela glavnaParcela) {
        this.kultura = kultura;
        this.glavnaParcela = glavnaParcela;
    }        
        
}