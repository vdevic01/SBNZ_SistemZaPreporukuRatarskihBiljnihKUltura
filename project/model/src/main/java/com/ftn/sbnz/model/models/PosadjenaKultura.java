package com.ftn.sbnz.model.models;

import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class PosadjenaKultura {
    private long parcelaId;
    private Date timestamp;
    private BiljnaKultura kultura;

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

    public PosadjenaKultura(long parcelaId, Date timestamp, BiljnaKultura kultura) {
        this.parcelaId = parcelaId;
        this.timestamp = timestamp;
        this.kultura = kultura;
    }
    public PosadjenaKultura() {
    }
    public long getParcelaId() {
        return parcelaId;
    }
    public void setParcelaId(long parcelaId) {
        this.parcelaId = parcelaId;
    }
    
    
}
