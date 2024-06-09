package com.ftn.sbnz.service.dto.request;

import java.util.List;

import com.ftn.sbnz.model.models.JacinaVetra;
import com.ftn.sbnz.model.models.Proizvodjac;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelDto {
    private String name;
    private double latitude;
    private double longitude;
    private double humusContent;
    private SowingDto lastSowing;
    private JacinaVetra expectedWindStrength;
    private List<Proizvodjac> manufacturerPreferences;
}
