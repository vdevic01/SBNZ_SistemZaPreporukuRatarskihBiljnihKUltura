package com.ftn.sbnz.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.ftn.sbnz.model.models.Biljka;

import org.drools.template.DataProviderCompiler;
import org.drools.template.DataProvider;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
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

	@Bean
	public KieContainer kieContainer(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		return kContainer;
	}

	@Bean
	public KieSession kieSession() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		KieSession kieSession = kContainer.newKieSession("cepKsession");
		// return kieSession;
		return createKieSessionFromTemplate();
	}

	public KieSession createKieSessionFromTemplate() {
        ClassPathResource classPathResource = new ClassPathResource("/rules/cep/template-rules.drt");
        InputStream template;
        try {
            template = classPathResource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1700", "K_FAO700", "&&"},
				new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1600", "K_FAO600", "&&"},
				new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1500", "K_FAO500", "&&"},
				new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1400", "K_FAO400", "&&"},
				new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1300", "K_FAO300", "&&"},
				new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1200", "K_FAO200", "&&"},
				new String[]{"KUKURUZ", "APRIL", "OCTOBER", "1100", "K_FAO100", "&&"},
				new String[]{"SECERNA_REPA", "APRIL", "OCTOBER", "0", "SECERNA_REPA", "&&"},
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1100", "S_VRLO_RANI", "&&"},
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1160", "S_RANI", "&&"},
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1220", "S_SREDNJE_RANI", "&&"},
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1280", "S_SREDNJE_KASNI", "&&"},
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1340", "S_KASNI", "&&"},
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1100", "S_VRLO_KASNI", "&&"},
				new String[]{"PSENICA", "OCTOBER", "JUNE", "550", "P_VRLO_RANA", "||"},
				new String[]{"PSENICA", "OCTOBER", "JUNE", "640", "P_RANA", "||"},
				new String[]{"PSENICA", "OCTOBER", "JUNE", "730", "P_KASNA", "||"},
				new String[]{"ULJANA_REPICA", "OCTOBER", "MAY", "350", "UR_VRLO_RANA", "||"},
				new String[]{"ULJANA_REPICA", "OCTOBER", "MAY", "385", "UR_RANA", "||"},
				new String[]{"ULJANA_REPICA", "OCTOBER", "MAY", "420", "UR_KASNA", "||"},
			});

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        return createKieSessionFromDRL(drl);
    }

    private KieSession createKieSessionFromDRL(String drl) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: " + message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}

	 // Degree day se racuna kao Max((MaxTemp + MinTemp) / 2 - 10, 0)
