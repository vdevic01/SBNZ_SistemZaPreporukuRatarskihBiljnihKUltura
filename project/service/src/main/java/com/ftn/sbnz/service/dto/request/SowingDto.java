package com.ftn.sbnz.service.dto.request;

import com.ftn.sbnz.model.models.BiljnaKultura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SowingDto {
    private String date;
    private BiljnaKultura plant;
}
