package com.ftn.sbnz.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import org.drools.template.DataProviderCompiler;
import org.drools.core.io.impl.InputStreamResource;
import org.drools.template.DataProvider;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceApplication  {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	public KieSession kieSession() {

		KieHelper kieHelper = new KieHelper();
		ClassPathResource drlClasspath = new ClassPathResource("/rules/cep/cep.drl");
		try {
			org.springframework.core.io.InputStreamResource drlInputStreamResource = new org.springframework.core.io.InputStreamResource(drlClasspath.getInputStream());
			Resource drlResource = new InputStreamResource(drlInputStreamResource.getInputStream());
			kieHelper.addResource(drlResource, ResourceType.DRL);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		

		ClassPathResource classPathResource = new ClassPathResource("/rules/cep/ripening-group-rules.drt");
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
				new String[]{"SUNCOKRET", "APRIL", "SEPTEMBER", "1400", "S_VRLO_KASNI", "&&"},
				new String[]{"SOJA", "APRIL", "SEPTEMBER", "1100", "SJ_VRLO_RANA", "&&"},
				new String[]{"SOJA", "APRIL", "SEPTEMBER", "1250", "SJ_RANA", "&&"},
				new String[]{"SOJA", "APRIL", "SEPTEMBER", "1400", "SJ_KASNA", "&&"},
				new String[]{"PSENICA", "OCTOBER", "JUNE", "550", "P_VRLO_RANA", "||"},
				new String[]{"PSENICA", "OCTOBER", "JUNE", "640", "P_RANA", "||"},
				new String[]{"PSENICA", "OCTOBER", "JUNE", "730", "P_KASNA", "||"},
				new String[]{"ULJANA_REPICA", "OCTOBER", "MAY", "350", "UR_VRLO_RANA", "||"},
				new String[]{"ULJANA_REPICA", "OCTOBER", "MAY", "385", "UR_RANA", "||"},
				new String[]{"ULJANA_REPICA", "OCTOBER", "MAY", "420", "UR_KASNA", "||"},
			});

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

		kieHelper.addContent(drl, ResourceType.DRL);
		Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: " + message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build(EventProcessingOption.STREAM).newKieSession();
	}
}

	 // Degree day se racuna kao Max((MaxTemp + MinTemp) / 2 - 10, 0)
