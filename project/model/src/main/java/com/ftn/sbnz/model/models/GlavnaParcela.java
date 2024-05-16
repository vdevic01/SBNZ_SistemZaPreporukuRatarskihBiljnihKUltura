package com.ftn.sbnz.model.models;

import java.util.UUID;
import jakarta.persistence.*;

@Entity()
@Table(name = "Parcele")
public class GlavnaParcela {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false, nullable = false)
    private UUID id;

    private double geografskaSirina;
    private double geografskaDuzina;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "vlasnik_id")
    private Korisnik vlasnik;

    private double humus;

    @Enumerated(EnumType.STRING)
    private BiljnaKultura poslednjaBiljnaKultura;

    @Enumerated(EnumType.STRING)
    private JacinaVetra ocekivanaJacinaVetra;
}
