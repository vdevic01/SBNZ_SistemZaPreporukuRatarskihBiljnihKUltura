package com.ftn.sbnz.model.models;

public class Korisnik {
    public long id;

    private String email;
    private String sifra;


    public Korisnik(long id, String email, String sifra) {
        this.id = id;
        this.email = email;
        this.sifra = sifra;
    }
    
    public long getId() {
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
    public void setId(long id) {
        this.id = id;
    }   
}
