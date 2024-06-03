package com.ftn.sbnz.model.models;

import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class PosadjenaKultura {
    private GlavnaParcela parcela;
    private Date timestamp;
    private BiljnaKultura kultura;
    private GrupaZrenja grupaZrenja;

    public GlavnaParcela getParcela() {
        return parcela;
    }
    public void setParcela(GlavnaParcela parcela) {
        this.parcela = parcela;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public BiljnaKultura getKultura() {
        return kultura;
    }
    public void setKultura(BiljnaKultura kultura) {
        this.kultura = kultura;
    }
    
    public GrupaZrenja getGrupaZrenja() {
        return grupaZrenja;
    }
    public void setGrupaZrenja(GrupaZrenja grupaZrenja) {
        this.grupaZrenja = grupaZrenja;
    }

    public PosadjenaKultura(GlavnaParcela parcela, Date timestamp, BiljnaKultura kultura, GrupaZrenja grupaZrenja) {
        this.parcela = parcela;
        this.timestamp = timestamp;
        this.kultura = kultura;
        this.grupaZrenja = grupaZrenja;
    }
    public PosadjenaKultura() {
    }
    
    
}
