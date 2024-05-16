package com.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.FailedLoginAttempt;
import com.ftn.sbnz.model.events.Item;


@Service
public class SampleAppService {

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

}
