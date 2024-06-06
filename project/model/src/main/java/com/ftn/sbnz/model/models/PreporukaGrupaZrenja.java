package com.ftn.sbnz.model.models;

public class PreporukaGrupaZrenja {
    private GrupaZrenja grupa;
    private GlavnaParcela glavnaParcela;
    public GrupaZrenja getGrupa() {
        return grupa;
    }
    public void setGrupa(GrupaZrenja grupa) {
        this.grupa = grupa;
    }
    public GlavnaParcela getGlavnaParcela() {
        return glavnaParcela;
    }
    public void setGlavnaParcela(GlavnaParcela glavnaParcela) {
        this.glavnaParcela = glavnaParcela;
    }
    public PreporukaGrupaZrenja() {
    }
    public PreporukaGrupaZrenja(GrupaZrenja grupa, GlavnaParcela glavnaParcela) {
        this.grupa = grupa;
        this.glavnaParcela = glavnaParcela;
    }
}
