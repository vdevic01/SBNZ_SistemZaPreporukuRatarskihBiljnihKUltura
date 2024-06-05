package com.ftn.sbnz.service.dto.request;

import com.ftn.sbnz.model.models.JacinaVetra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelDto {
    private double latitude;
    private double longitude;
    private double humusContent;
    private SowingDto lastSowing;
    private JacinaVetra expectedWindStrength;
}
