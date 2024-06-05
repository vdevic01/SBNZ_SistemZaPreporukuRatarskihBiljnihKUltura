package com.ftn.sbnz.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.FailedLoginAttempt;
import com.ftn.sbnz.model.events.Item;
import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.model.models.GlavnaParcela;
import com.ftn.sbnz.model.models.GrupaZrenja;
import com.ftn.sbnz.model.models.JacinaVetra;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.MeteoroloskiPodaci;
import com.ftn.sbnz.model.models.PosadjenaKultura;
import com.ftn.sbnz.service.dto.request.ParcelDto;
import com.ftn.sbnz.service.dto.response.ParcelResponseDto;
import com.ftn.sbnz.service.exception.ForbiddenException;
import com.ftn.sbnz.service.exception.NotFoundException;
import com.ftn.sbnz.service.model.Parcel;
import com.ftn.sbnz.service.model.User;
import com.ftn.sbnz.service.repository.ParcelRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SampleAppService {

	@Value("${app.keys.visual-crossing-api-key}")
	public String apiKey;

	private final KieSession kieSession;

	private final ParcelRepository parcelRepository;

	private static DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE;

	public Item getClassifiedItem(Item i) {
		kieSession.insert(i);
		kieSession.fireAllRules();
		kieSession.dispose();
		return i;
	}

	public void login(FailedLoginAttempt fla){
		kieSession.insert(fla);
		kieSession.fireAllRules();
	}

	public void test(){
		System.out.println("======== LOG LINE 1 ========");
		Korisnik vlasnik = new Korisnik(1, "fasfasfasf", "asfasfasf");
		GlavnaParcela parcela = new GlavnaParcela(1, 45.123, 25.09, vlasnik, 0.08, BiljnaKultura.PSENICA, JacinaVetra.SLAB);
		kieSession.insert(parcela);
		kieSession.fireAllRules();
		System.out.println("======== LOG LINE 2 ========");
	}

	private User getAuthenticatedUser(){
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public ParcelResponseDto createParcel(ParcelDto parcelDto){
		User owner = getAuthenticatedUser();
		Parcel parcel = new Parcel(parcelDto, owner);
		parcel = this.parcelRepository.save(parcel);
		LocalDateTime ldt01 = LocalDateTime.of(2023, 7, 2, 0, 0, 0);
		LocalDateTime ldt02 = LocalDateTime.of(2023, 8, 2, 0, 0, 0);
		MeteoroloskiPodaci mp01 = new MeteoroloskiPodaci(parcel.getId(), ldt01, 1200);
		MeteoroloskiPodaci mp02 = new MeteoroloskiPodaci(parcel.getId(), ldt02, 250);

		Korisnik ownerDrl = new Korisnik(owner.getId(), null, null);
		GlavnaParcela parcelDrl = new GlavnaParcela(parcel.getId(), parcel.getLatitude(), parcel.getLongitude(), ownerDrl, parcel.getHumusContent(), parcel.getLastPlant(), parcel.getExpectedWindStrength());
		if(parcelDto.getLastSowing() != null){
			try{
				LocalDateTime localDate = LocalDateTime.parse(parcelDto.getLastSowing().getDate(), isoFormatter);
				Instant instant = localDate.atZone(ZoneId.systemDefault()).toInstant();        
				Date date = Date.from(instant);
				PosadjenaKultura lastSowingDrl = new PosadjenaKultura(parcel.getId(), date, parcelDto.getLastSowing().getPlant());
				kieSession.insert(lastSowingDrl);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}

		kieSession.insert(parcelDrl);
		kieSession.insert(mp01);
		kieSession.insert(mp02);
		kieSession.fireAllRules();
		for(String recommendation : parcelDrl.getPreporukeGrupa()){
			parcel.getRecommendations().add(GrupaZrenja.valueOf(recommendation));
		}
		parcel = parcelRepository.save(parcel);
		return new ParcelResponseDto(parcel);
	}

	public List<ParcelResponseDto> getParcels(){
		User owner = getAuthenticatedUser();
		List<Parcel> parcels = parcelRepository.findParcelsByOwner(owner);
		return parcels.stream().map(ParcelResponseDto::new).toList();
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
		parcel.getRecommendations().clear();
		for(Object obj : kieSession.getObjects(new ClassObjectFilter(GlavnaParcela.class))){
			GlavnaParcela glavnaParcela = (GlavnaParcela) obj;
			if(glavnaParcela.getId() == parcel.getId()){
				for(String recommendation : glavnaParcela.getPreporukeGrupa()){
					parcel.getRecommendations().add(GrupaZrenja.valueOf(recommendation));
				}
			}
		}
		ParcelResponseDto response = new ParcelResponseDto(parcel);
		parcelRepository.save(parcel);
		return response;
	} 

	public List<String> dobaviPreporuke(GlavnaParcela parcela){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime timeframeStart = LocalDateTime.of(now.getYear() - 1, 1, 1, 0, 0, 0);
		LocalDateTime timeframeEnd = LocalDateTime.of(now.getYear() - 1, 12, 31, 23, 59, 59);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String from = timeframeStart.format(formatter);
		String to = timeframeEnd.format(formatter);
		from = "2024-05-04";
		to = "2024-05-05";
		String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + parcela.getGeografskaSirina() + "%2C%20" + parcela.getGeografskaDuzina() + "/" + from + "/" + to + "?unitGroup=metric&elements=datetime%2CdatetimeEpoch%2Ctempmax%2Ctempmin&include=days&key=" + apiKey + "&contentType=json";
		// HttpResponse<JsonNode> jsonResponse;
		// try {
		// 	jsonResponse = Unirest.get(url).asJson();
		// } catch (UnirestException e) {
		// 	return null;
		// }
		// JsonNode responseBody = jsonResponse.getBody();
		// System.out.println(responseBody.toString());
		LocalDateTime ldt01 = LocalDateTime.of(2023, 7, 2, 0, 0, 0);
		LocalDateTime ldt02 = LocalDateTime.of(2023, 8, 2, 0, 0, 0);
		MeteoroloskiPodaci mp01 = new MeteoroloskiPodaci(1, ldt01, 1200);
		MeteoroloskiPodaci mp02 = new MeteoroloskiPodaci(1, ldt02, 250);
		kieSession.insert(parcela);
		kieSession.insert(mp01);
		kieSession.insert(mp02);
		kieSession.fireAllRules();
		return parcela.getPreporukeGrupa();
	}
}
