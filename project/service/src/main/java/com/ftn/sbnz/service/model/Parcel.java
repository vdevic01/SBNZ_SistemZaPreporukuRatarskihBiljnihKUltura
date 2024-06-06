package com.ftn.sbnz.service.model;

import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.models.GrupaZrenja;
import com.ftn.sbnz.model.models.JacinaVetra;
import com.ftn.sbnz.model.models.Proizvodjac;
import com.ftn.sbnz.service.dto.request.ParcelDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private User owner;

    private double humusContent;

    @Enumerated(EnumType.STRING)
    private JacinaVetra expectedWindStrength;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parcel")
    private List<HybridRecommendation> recommendations = new ArrayList<>();

    @ElementCollection(targetClass = Proizvodjac.class)
    @CollectionTable(name = "manufacturer_preferences", joinColumns = @JoinColumn(name = "parcel_id"))
    private List<Proizvodjac> manufacturerPreferences = new ArrayList<>();

    public Parcel(ParcelDto parcelDto, User owner){
        this.latitude = parcelDto.getLatitude();
        this.longitude = parcelDto.getLongitude();
        this.humusContent = parcelDto.getHumusContent();
        this.expectedWindStrength = parcelDto.getExpectedWindStrength();
        this.owner = owner;
        this.manufacturerPreferences = parcelDto.getManufacturerPreferences();
    }
}
