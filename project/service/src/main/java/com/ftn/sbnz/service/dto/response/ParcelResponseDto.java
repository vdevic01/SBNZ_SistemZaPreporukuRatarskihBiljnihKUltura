package com.ftn.sbnz.service.dto.response;

import java.util.List;

import com.ftn.sbnz.model.models.GrupaZrenja;
import com.ftn.sbnz.model.models.JacinaVetra;
import com.ftn.sbnz.service.model.Parcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelResponseDto {
    private Long id;
    private double latitude;
    private double longitude;
    private double humusContent;
    private JacinaVetra expectedWindStrength;
    private List<GrupaZrenja> recommendations;

    public ParcelResponseDto(Parcel parcel){
        this.id = parcel.getId();
        this.latitude = parcel.getLatitude();
        this.longitude = parcel.getLongitude();
        this.humusContent = parcel.getHumusContent();
        this.expectedWindStrength = parcel.getExpectedWindStrength();
        this.recommendations = parcel.getRecommendations();
    }
}
