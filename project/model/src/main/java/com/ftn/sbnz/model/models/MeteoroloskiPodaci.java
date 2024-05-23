package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class MeteoroloskiPodaci {
    private long parcelaID;
    private LocalDateTime timestamp;
    private double temperatura;
    public long getParcelaID() {
        return parcelaID;
    }
    public void setParcelaID(long parcelaID) {
        this.parcelaID = parcelaID;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public double getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
    public int getMonth(){
        return this.getTimestamp().getMonthValue();
    }
    public MeteoroloskiPodaci() {
    }
    public MeteoroloskiPodaci(long parcelaID, LocalDateTime timestamp, double temperatura) {
        this.parcelaID = parcelaID;
        this.timestamp = timestamp;
        this.temperatura = temperatura;
    }
    
    
}
