package com.ftn.sbnz.service.model;

import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.model.models.Hibrid;
import com.ftn.sbnz.model.models.Proizvodjac;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hybrid_recommendations")
public class HybridRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BiljnaKultura plant;
    private Proizvodjac manufacturer;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;

    public HybridRecommendation(BiljnaKultura plant, Proizvodjac manufacturer, String name, Parcel parcel){
        this.plant = plant;
        this.manufacturer = manufacturer;
        this.name = name;
        this.parcel = parcel;
    }
}
