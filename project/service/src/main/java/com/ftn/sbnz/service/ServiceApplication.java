package com.ftn.sbnz.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ftn.sbnz.model.models.Biljka;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ServiceApplication  {
	
	private static Logger log = LoggerFactory.getLogger(ServiceApplication.class);
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ServiceApplication.class, args);

		// String[] beanNames = ctx.getBeanDefinitionNames();
		// Arrays.sort(beanNames);

		// StringBuilder sb = new StringBuilder("Application beans:\n");
		// for (String beanName : beanNames) {
		// 	sb.append(beanName + "\n");
		// }
		// log.info(sb.toString());
	}

	// @Bean
	// public KieSession kieSession() {
	// 	KieServices ks = KieServices.Factory.get();
	// 	KieContainer kContainer = ks
	// 			.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
	// 	KieScanner kScanner = ks.newKieScanner(kContainer);
	// 	kScanner.start(1000);
	// 	KieSession kieSession = kContainer.newKieSession("cepKsession");
	// 	return kieSession;
	// }
	
	@Bean
	public KieSession kieSession() {
		var b1 = new Biljka("Golosemenice", "Biljka");
		var b2 = new Biljka("Skrivenosemenice", "Biljka");

		var b3 = new Biljka("Alge", "Golosemenice");
		var b4 = new Biljka("Kombu alga", "Alga");
		var b5 = new Biljka("Nori alga", "Alga");
		var b6 = new Biljka("Agar agar alga", "Alga");

		var b7 = new Biljka("Mahovine", "Golosemenice");
		var b8 = new Biljka("Plutajuća Riccia", "Mahovine");
		var b9 = new Biljka("Javanska mahovina", "Mahovine");

		var b10 = new Biljka("Paprati", "Golosemenice");
		var b11 = new Biljka("Dicksonia antarctica", "Paprati");
		var b12 = new Biljka("Blechnum nudum", "Paprati");

		var b13 = new Biljka("Sa pupoljkom", "Skrivenosemenice");
		var b14 = new Biljka("Ljiljan", "Sa pupoljkom");
		var b15 = new Biljka("Orhideja", "Sa pupoljkom");
		var b16 = new Biljka("Ruza", "Sa pupoljkom");

		var b17 = new Biljka("Bez pupoljka", "Skrivenosemenice");
		var b18 = new Biljka("Drvo zivota", "Bez pupoljka");
		var b19 = new Biljka("Asparagus", "Bez pupoljka");
		var b20 = new Biljka("Hrizantema", "Bez pupoljka");

		List<Biljka> items = List.of(
			b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20
		);
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		KieSession kieSession = kContainer.newKieSession("bwKsession");

		for(Biljka b : items){
			kieSession.insert(b);
		}
		kieSession.insert("go");
		kieSession.fireAllRules();

		return kieSession;
	}

	/*
	 * KieServices ks = KieServices.Factory.get(); KieContainer kContainer =
	 * ks.newKieContainer(ks.newReleaseId("drools-spring-v2",
	 * "drools-spring-v2-kjar", "0.0.1-SNAPSHOT")); KieScanner kScanner =
	 * ks.newKieScanner(kContainer); kScanner.start(10_000); KieSession kSession =
	 * kContainer.newKieSession();
	 */
}
