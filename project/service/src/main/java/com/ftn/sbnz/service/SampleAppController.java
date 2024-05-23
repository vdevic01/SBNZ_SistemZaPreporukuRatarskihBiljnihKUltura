package com.ftn.sbnz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.events.FailedLoginAttempt;
import com.ftn.sbnz.model.events.Item;
import com.ftn.sbnz.model.models.GlavnaParcela;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.dto.GlavnaParcelDTO;


@RestController
public class SampleAppController {
	private static Logger log = LoggerFactory.getLogger(SampleAppController.class);

	private final SampleAppService sampleService;

	@Autowired
	public SampleAppController(SampleAppService sampleService) {
		this.sampleService = sampleService;
	}

	@RequestMapping(value = "/item", method = RequestMethod.GET, produces = "application/json")
	public Item getQuestions(@RequestParam(required = true) String id, @RequestParam(required = true) String name,
			@RequestParam(required = true) double cost, @RequestParam(required = true) double salePrice) {

		Item newItem = new Item(Long.parseLong(id), name, cost, salePrice);

		log.debug("Item request received for: " + newItem);

		Item i2 = sampleService.getClassifiedItem(newItem);

		return i2;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(@RequestParam(required = true) long user, @RequestParam(required = true) String ip) {
		FailedLoginAttempt fla = new FailedLoginAttempt(user, ip);
		this.sampleService.login(fla);
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {
		this.sampleService.test();
	}

	@RequestMapping(value = "/preporuke", method = RequestMethod.POST)
	public List<String> dobaviPreporuke(@RequestBody GlavnaParcelDTO parcelaDTO){
		GlavnaParcela parcela = new GlavnaParcela(parcelaDTO.getId(), parcelaDTO.getGeografskaSirina(), parcelaDTO.getGeografskaDuzina(), new Korisnik(1, null, null), parcelaDTO.getHumus(), parcelaDTO.getPoslednjaBiljnaKultura(), parcelaDTO.getOcekivanaJacinaVetra());
		return this.sampleService.dobaviPreporuke(parcela);
	}
	
}
