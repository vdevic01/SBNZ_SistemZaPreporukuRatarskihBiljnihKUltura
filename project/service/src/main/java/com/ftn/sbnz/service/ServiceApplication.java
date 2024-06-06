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
		
		compileRipeningGroupRules(kieHelper);
		checkForCompilationErrors(kieHelper);
		compileManufacturerRules(kieHelper);
		checkForCompilationErrors(kieHelper);

        return kieHelper.build(EventProcessingOption.STREAM).newKieSession();
	}

	private void checkForCompilationErrors(KieHelper kieHelper){
		Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: " + message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
	}

	private void compileRipeningGroupRules(KieHelper kieHelper){
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
	}

	private void compileManufacturerRules(KieHelper kieHelper){
		ClassPathResource classPathResource = new ClassPathResource("/rules/cep/manufacturer-rules.drt");
        InputStream template;
        try {
            template = classPathResource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
                new String[]{"KUKURUZ", "K_FAO100", "PIONEER", "P8521"},
                new String[]{"KUKURUZ", "K_FAO100", "DEKALB", "DKC3330"},
                new String[]{"KUKURUZ", "K_FAO100", "KWS", "KWS2376"},
				new String[]{"KUKURUZ", "K_FAO100", "LIMAGRAIN", "LG 31.227"},
                new String[]{"KUKURUZ", "K_FAO100", "SYNGENTA", "NK Leader"},

				new String[]{"KUKURUZ", "K_FAO200", "PIONEER", "P9486"},
                new String[]{"KUKURUZ", "K_FAO200", "DEKALB", "DKC4590"},
                new String[]{"KUKURUZ", "K_FAO200", "KWS", "KWS2370"},
				new String[]{"KUKURUZ", "K_FAO200", "LIMAGRAIN", "LG 31.373"},
                new String[]{"KUKURUZ", "K_FAO200", "SYNGENTA", "NK Thermo"},

				new String[]{"KUKURUZ", "K_FAO300", "PIONEER", "P9911"},
                new String[]{"KUKURUZ", "K_FAO300", "DEKALB", "DKC5830"},
                new String[]{"KUKURUZ", "K_FAO300", "KWS", "KWS3381"},
				new String[]{"KUKURUZ", "K_FAO300", "LIMAGRAIN", "LG 32.08"},
                new String[]{"KUKURUZ", "K_FAO300", "SYNGENTA", "NK Octavious"},

				new String[]{"KUKURUZ", "K_FAO400", "PIONEER", "P0216"},
                new String[]{"KUKURUZ", "K_FAO400", "DEKALB", "DKC6777"},
                new String[]{"KUKURUZ", "K_FAO400", "KWS", "KWS4484"},
				new String[]{"KUKURUZ", "K_FAO400", "LIMAGRAIN", "LG 34.90"},
                new String[]{"KUKURUZ", "K_FAO400", "SYNGENTA", "NK Colossal"},

				new String[]{"KUKURUZ", "K_FAO500", "PIONEER", "P1745"},
                new String[]{"KUKURUZ", "K_FAO500", "DEKALB", "DKC7200"},
                new String[]{"KUKURUZ", "K_FAO500", "KWS", "KWS5471"},
				new String[]{"KUKURUZ", "K_FAO500", "LIMAGRAIN", "LG 35.80"},
                new String[]{"KUKURUZ", "K_FAO500", "SYNGENTA", "NK Titan"},

				new String[]{"KUKURUZ", "K_FAO600", "PIONEER", "P1964"},
                new String[]{"KUKURUZ", "K_FAO600", "DEKALB", "DKC7927"},
                new String[]{"KUKURUZ", "K_FAO600", "KWS", "KWS6180"},
				new String[]{"KUKURUZ", "K_FAO600", "LIMAGRAIN", "LG 36.98"},
                new String[]{"KUKURUZ", "K_FAO600", "SYNGENTA", "NK Power"},

				new String[]{"KUKURUZ", "K_FAO700", "PIONEER", "P2088"},
                new String[]{"KUKURUZ", "K_FAO700", "DEKALB", "DKC7998"},
                new String[]{"KUKURUZ", "K_FAO700", "KWS", "KWS7575"},
				new String[]{"KUKURUZ", "K_FAO700", "LIMAGRAIN", "LG 37.85"},
                new String[]{"KUKURUZ", "K_FAO700", "SYNGENTA", "NK Ultra"},

				new String[]{"SUNCOKRET", "S_VRLO_RANI", "SYNGENTA", "NK Brio"},
				new String[]{"SUNCOKRET", "S_VRLO_RANI", "PIONEER", "P64LE25"},
				new String[]{"SUNCOKRET", "S_VRLO_RANI", "LIMAGRAIN", "LG 5452 HO"},
				new String[]{"SUNCOKRET", "S_VRLO_RANI", "ADVANTA_SEEDS", "SF11"},
				new String[]{"SUNCOKRET", "S_VRLO_RANI", "NUSEED", "N4H302 E"},

				new String[]{"SUNCOKRET", "S_RANI", "SYNGENTA", "NK Neoma"},
				new String[]{"SUNCOKRET", "S_RANI", "PIONEER", "P63ME70"},
				new String[]{"SUNCOKRET", "S_RANI", "LIMAGRAIN", "LG 5543 CL"},
				new String[]{"SUNCOKRET", "S_RANI", "ADVANTA_SEEDS", "SF20"},
				new String[]{"SUNCOKRET", "S_RANI", "NUSEED", "N4H470 CL"},

				new String[]{"SUNCOKRET", "S_SREDNJE_RANI", "SYNGENTA", "NK Petrina"},
				new String[]{"SUNCOKRET", "S_SREDNJE_RANI", "PIONEER", "P64LE68"},
				new String[]{"SUNCOKRET", "S_SREDNJE_RANI", "LIMAGRAIN", "LG 5653 HO"},
				new String[]{"SUNCOKRET", "S_SREDNJE_RANI", "ADVANTA_SEEDS", "SF30"},
				new String[]{"SUNCOKRET", "S_SREDNJE_RANI", "NUSEED", "N4H650 E"},

				new String[]{"SUNCOKRET", "S_SREDNJE_KASNI", "SYNGENTA", "NK Forio"},
				new String[]{"SUNCOKRET", "S_SREDNJE_KASNI", "PIONEER", "P64LE75"},
				new String[]{"SUNCOKRET", "S_SREDNJE_KASNI", "LIMAGRAIN", "LG 5744 HO"},
				new String[]{"SUNCOKRET", "S_SREDNJE_KASNI", "ADVANTA_SEEDS", "SF40"},
				new String[]{"SUNCOKRET", "S_SREDNJE_KASNI", "NUSEED", "N4H810 E"},

				new String[]{"SUNCOKRET", "S_KASNI", "SYNGENTA", "NK Parcelle"},
				new String[]{"SUNCOKRET", "S_KASNI", "PIONEER", "P64LE82"},
				new String[]{"SUNCOKRET", "S_KASNI", "LIMAGRAIN", "LG 5887 HO"},
				new String[]{"SUNCOKRET", "S_KASNI", "ADVANTA_SEEDS", "SF50"},
				new String[]{"SUNCOKRET", "S_KASNI", "NUSEED", "N5H1451 E"},

				new String[]{"SUNCOKRET", "S_VRLO_KASNI", "SYNGENTA", "NK Prospera"},
				new String[]{"SUNCOKRET", "S_VRLO_KASNI", "PIONEER", "P64LE90"},
				new String[]{"SUNCOKRET", "S_VRLO_KASNI", "LIMAGRAIN", "LG 5999 HO"},
				new String[]{"SUNCOKRET", "S_VRLO_KASNI", "ADVANTA_SEEDS", "SF60"},
				new String[]{"SUNCOKRET", "S_VRLO_KASNI", "NUSEED", "N5H1555 E"},

				new String[]{"PSENICA", "P_VRLO_RANA", "SYNGENTA", "SY Sunrise"},
				new String[]{"PSENICA", "P_VRLO_RANA", "KWS", "Durumi"},
				new String[]{"PSENICA", "P_VRLO_RANA", "PIONEER", "25R40"},
				new String[]{"PSENICA", "P_VRLO_RANA", "LIMAGRAIN", "LG Avonlea"},
				new String[]{"PSENICA", "P_VRLO_RANA", "SYNGENTA", "Durum 1404"},

				new String[]{"PSENICA", "P_RANA", "SYNGENTA", "SY Monument"},
				new String[]{"PSENICA", "P_RANA", "KWS", "Sideral"},
				new String[]{"PSENICA", "P_RANA", "PIONEER", "25R55"},
				new String[]{"PSENICA", "P_RANA", "LIMAGRAIN", "LG Astral"},
				new String[]{"PSENICA", "P_RANA", "SYNGENTA", "Meridian 1515"},

				new String[]{"PSENICA", "P_KASNA", "SYNGENTA", "SY Prospect"},
				new String[]{"PSENICA", "P_KASNA", "KWS", "Barrel"},
				new String[]{"PSENICA", "P_KASNA", "PIONEER", "25R70"},
				new String[]{"PSENICA", "P_KASNA", "LIMAGRAIN", "LG Caliber"},
				new String[]{"PSENICA", "P_KASNA", "SYNGENTA", "Prestige 1620"},

				new String[]{"SOJA", "SJ_VRLO_RANA", "ASGROW", "AG08X9"},
				new String[]{"SOJA", "SJ_VRLO_RANA", "PIONEER", "P09T10R"},
				new String[]{"SOJA", "SJ_VRLO_RANA", "MONSANTO", "3309R2X"},
				new String[]{"SOJA", "SJ_VRLO_RANA", "SYNGENTA", "S09T6X"},
				new String[]{"SOJA", "SJ_VRLO_RANA", "CORTEVA_AGRISCIENCE", "DSR-2821RX"},

				new String[]{"SOJA", "SJ_RANA", "ASGROW", "AG13X7"},
				new String[]{"SOJA", "SJ_RANA", "PIONEER", "P14T15R"},
				new String[]{"SOJA", "SJ_RANA", "MONSANTO", "4432R2X"},
				new String[]{"SOJA", "SJ_RANA", "SYNGENTA", "S14T8X"},
				new String[]{"SOJA", "SJ_RANA", "CORTEVA_AGRISCIENCE", "DSR-3921RX"},

				new String[]{"SOJA", "SJ_KASNA", "ASGROW", "AG21X9"},
				new String[]{"SOJA", "SJ_KASNA", "PIONEER", "P24T30R"},
				new String[]{"SOJA", "SJ_KASNA", "MONSANTO", "6633R2X"},
				new String[]{"SOJA", "SJ_KASNA", "SYNGENTA", "S24T18X"},
				new String[]{"SOJA", "SJ_KASNA", "CORTEVA_AGRISCIENCE", "DSR-6221RX"},

				new String[]{"ULJANA_REPICA", "UR_VRLO_RANA", "DEKALB", "DK Exstorm"},
				new String[]{"ULJANA_REPICA", "UR_VRLO_RANA", "INVIGOR", "1030"},
				new String[]{"ULJANA_REPICA", "UR_VRLO_RANA", "SYNGENTA", "SY Arista"},
				new String[]{"ULJANA_REPICA", "UR_VRLO_RANA", "BAYER", "PR46W21"},
				new String[]{"ULJANA_REPICA", "UR_VRLO_RANA", "RAGT", "Meteor"},

				new String[]{"ULJANA_REPICA", "UR_RANA", "DEKALB", "DK Expedient"},
				new String[]{"ULJANA_REPICA", "UR_RANA", "INVIGOR", "2020"},
				new String[]{"ULJANA_REPICA", "UR_RANA", "SYNGENTA", "SY Fix"},
				new String[]{"ULJANA_REPICA", "UR_RANA", "BAYER", "PR46W20"},
				new String[]{"ULJANA_REPICA", "UR_RANA", "RAGT", "Marshall"},

				new String[]{"ULJANA_REPICA", "UR_KASNA", "DEKALB", "DK Exalte"},
				new String[]{"ULJANA_REPICA", "UR_KASNA", "INVIGOR", "L233P"},
				new String[]{"ULJANA_REPICA", "UR_KASNA", "SYNGENTA", "SY Harnas"},

				new String[]{"SECERNA_REPA", "SECERNA_REPA", "KWS", "Bella"},
				new String[]{"SECERNA_REPA", "SECERNA_REPA", "BAYER", "Betaseed 6832N"},
				new String[]{"SECERNA_REPA", "SECERNA_REPA", "SES_VANDERHAVE", "Magnum"},
				new String[]{"SECERNA_REPA", "SECERNA_REPA", "SYNGENTA", "Hilleshog 6024"},
				new String[]{"SECERNA_REPA", "SECERNA_REPA", "LIMAGRAIN", "LG 6611"},
			});

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

		kieHelper.addContent(drl, ResourceType.DRL);
	}	
}

	 // Degree day se racuna kao Max((MaxTemp + MinTemp) / 2 - 10, 0)
