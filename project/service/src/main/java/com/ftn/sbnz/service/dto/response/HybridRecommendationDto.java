package com.ftn.sbnz.service.dto.response;

import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.model.models.Proizvodjac;
import com.ftn.sbnz.service.model.HybridRecommendation;

import lombok.Data;

@Data
public class HybridRecommendationDto {
    private BiljnaKultura plant;
    private String name;
    private Proizvodjac manufacturer;

    public HybridRecommendationDto(HybridRecommendation recommendation){
        this.plant = recommendation.getPlant();
        this.name = recommendation.getName();
        this.manufacturer = recommendation.getManufacturer();
    }
}
