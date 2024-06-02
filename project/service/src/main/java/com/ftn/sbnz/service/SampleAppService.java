package com.ftn.sbnz.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.FailedLoginAttempt;
import com.ftn.sbnz.model.events.Item;
import com.ftn.sbnz.model.models.BiljnaKultura;
import com.ftn.sbnz.model.models.GlavnaParcela;
import com.ftn.sbnz.model.models.JacinaVetra;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.MeteoroloskiPodaci;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Service
public class SampleAppService {

	@Value("${app.keys.visual-crossing-api-key}")
	public String apiKey;

	private static Logger log = LoggerFactory.getLogger(SampleAppService.class);

	private final KieSession kieSession;

	@Autowired
	public SampleAppService(KieSession kieSession) {
		log.info("Initialising a new example session.");
		this.kieSession = kieSession;
	}

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
