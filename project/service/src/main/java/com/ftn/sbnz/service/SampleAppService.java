package com.ftn.sbnz.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.model.models.GlavnaParcela;
import com.ftn.sbnz.model.models.Hibrid;
import com.ftn.sbnz.model.models.MeteoroloskiPodaci;
import com.ftn.sbnz.model.models.PosadjenaKultura;
import com.ftn.sbnz.model.models.PreferencaProizvodjaca;
import com.ftn.sbnz.model.models.Proizvodjac;
import com.ftn.sbnz.service.dto.request.ParcelDto;
import com.ftn.sbnz.service.dto.response.ParcelResponseDto;
import com.ftn.sbnz.service.exception.ForbiddenException;
import com.ftn.sbnz.service.exception.NotFoundException;
import com.ftn.sbnz.service.model.HybridRecommendation;
import com.ftn.sbnz.service.model.Parcel;
import com.ftn.sbnz.service.model.User;
import com.ftn.sbnz.service.repository.ParcelRepository;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SampleAppService {

	@Value("${app.keys.visual-crossing-api-key}")
	public String apiKey;

	private final KieSession kieSession;

	private final ParcelRepository parcelRepository;

	private static DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

	private User getAuthenticatedUser(){
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private List<MeteoroloskiPodaci> getMeteorologicalData(Parcel parcel){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime timeframeStart = LocalDateTime.of(now.getYear() - 1, 1, 1, 0, 0, 0);
		LocalDateTime timeframeEnd = LocalDateTime.of(now.getYear() - 1, 12, 31, 23, 59, 59);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String from = timeframeStart.format(formatter);
		String to = timeframeEnd.format(formatter);
		// from = "2024-05-04";
		// to = "2024-05-05";
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + parcel.getLatitude() + "%2C%20" + parcel.getLongitude() + "/" + from + "/" + to + "?unitGroup=metric&elements=datetime%2CdatetimeEpoch%2Ctempmax%2Ctempmin&include=days&key=" + apiKey + "&contentType=json";
		HttpResponse<JsonNode> jsonResponse;
		try {
			jsonResponse = Unirest.get(url).asJson();
		} catch (UnirestException e) {
			throw new RuntimeException();
		}
		JSONObject responseBody = jsonResponse.getBody().getObject();
		JSONArray days = responseBody.getJSONArray("days");
		List<MeteoroloskiPodaci> output = new ArrayList<>();
		for(int i = 0; i < days.length(); i++){
			JSONObject day = days.getJSONObject(i);
			LocalDateTime date = LocalDate.parse(day.getString("datetime"), formatter).atStartOfDay();
			double minTemp = day.getDouble("tempmin");
			double maxTemp = day.getDouble("tempmax");
			double degreeDays = Math.max((maxTemp + minTemp) / 2.0 - 10, 0);
			output.add(new MeteoroloskiPodaci(parcel.getId(), date, degreeDays));
		}
		return output;
	}

	public ParcelResponseDto createParcel(ParcelDto parcelDto){
		User owner = getAuthenticatedUser();
		Parcel parcel = new Parcel(parcelDto, owner);
		parcel = this.parcelRepository.save(parcel);
		
		List<MeteoroloskiPodaci> temperatureData = getMeteorologicalData(parcel);
		
		GlavnaParcela parcelDrl = new GlavnaParcela(parcel.getId(), parcel.getLatitude(), parcel.getLongitude(), parcel.getHumusContent(), parcel.getExpectedWindStrength());
		if(parcelDto.getLastSowing() != null){
			try{
				LocalDateTime localDate = LocalDate.parse(parcelDto.getLastSowing().getDate(), isoFormatter).atStartOfDay();
				Instant instant = localDate.atZone(ZoneId.systemDefault()).toInstant();        
				Date date = Date.from(instant);
				PosadjenaKultura lastSowingDrl = new PosadjenaKultura(parcel.getId(), date, parcelDto.getLastSowing().getPlant());
				kieSession.insert(lastSowingDrl);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		for(Proizvodjac manufacturer : parcelDto.getManufacturerPreferences()){
			kieSession.insert(new PreferencaProizvodjaca(parcel.getId(), manufacturer));
		}
		kieSession.insert(parcelDrl);
		for(MeteoroloskiPodaci mp : temperatureData){
			kieSession.insert(mp);
		}

		kieSession.fireAllRules();
		for(Hibrid recommendation : parcelDrl.getPreporuke()){
			parcel.getRecommendations().add(new HybridRecommendation(recommendation.getBiljnaKultura(), recommendation.getProizvodjac(), recommendation.getNaziv(), parcel));
		}
		parcel = parcelRepository.save(parcel);
		return new ParcelResponseDto(parcel);
	}

	public List<ParcelResponseDto> getParcels(){
		User owner = getAuthenticatedUser();
		List<Parcel> parcels = parcelRepository.findParcelsByOwner(owner);
		return parcels.stream().map(ParcelResponseDto::new).toList();
	}

	public ParcelResponseDto getParcel(long parcelId){
		Optional<Parcel> parceOptional = parcelRepository.findById(parcelId);
		Parcel parcel = parceOptional.orElseThrow(() -> new NotFoundException("Parcel not found"));
		User owner = getAuthenticatedUser();
		if(owner.getId() != parcel.getOwner().getId()){
			throw new ForbiddenException("Forribiden request");
		}
		return new ParcelResponseDto(parcel);
	}

	public ParcelResponseDto plant(Long parcelId, BiljnaKultura plant){
		Optional<Parcel> parcelOpt = parcelRepository.findById(parcelId);
		Parcel parcel = parcelOpt.orElseThrow(() -> new NotFoundException("Parcel not found"));
		User owner = getAuthenticatedUser();
		if(owner.getId() != parcel.getId()){
			throw new ForbiddenException("Forribiden request");
		}
		PosadjenaKultura pk = new PosadjenaKultura(parcelId, new Date(), plant);
		kieSession.insert(pk);
		kieSession.fireAllRules();
		parcel.clearRecommendations();
		for(Object obj : kieSession.getObjects(new ClassObjectFilter(GlavnaParcela.class))){
			GlavnaParcela glavnaParcela = (GlavnaParcela) obj;
			if(glavnaParcela.getId() == parcel.getId()){
				for(Hibrid recommendation : glavnaParcela.getPreporuke()){
					parcel.getRecommendations().add(new HybridRecommendation(recommendation.getBiljnaKultura(), recommendation.getProizvodjac(), recommendation.getNaziv(), parcel));
				}
			}
		}
		parcelRepository.save(parcel);
		ParcelResponseDto response = new ParcelResponseDto(parcel);
		return response;
	}
}
