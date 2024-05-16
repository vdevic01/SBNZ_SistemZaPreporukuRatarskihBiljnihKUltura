package com.ftn.sbnz.model.models;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity()
@Table(name = "Korisnici")
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, nullable = false)
    private UUID id;

    private String email;
    private String sifra;

    public Korisnik(String email, String sifra) {
        this.email = email;
        this.sifra = sifra;
    }
    public UUID getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSifra() {
        return sifra;
    }
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }   
}
