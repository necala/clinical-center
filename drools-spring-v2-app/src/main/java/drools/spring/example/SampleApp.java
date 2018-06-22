package drools.spring.example;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.drools.core.ClassObjectFilter;
import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.MonitoringIssue;
import drools.spring.example.model.events.HeartBeatEvent;
import drools.spring.example.model.events.OxygenLevelEvent;
import drools.spring.example.model.events.UrinalSumEvent;

@SpringBootApplication
public class SampleApp {
	
	private static Logger log = LoggerFactory.getLogger(SampleApp.class);
	
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(SampleApp.class, args); 

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        StringBuilder sb = new StringBuilder("Application beans:\n");
        for (String beanName : beanNames) {
            sb.append(beanName + "\n");
        }
        //log.info(sb.toString());
        
        log.info("Application started");
        
        KieSession kieSession = realtimeSession();
        
        runRealTimeHeartBeat(kieSession);
        runRealTimeDialysis(kieSession);
        runRealTimeOxygen(kieSession);
	}
	
	
	@Bean
    public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("drools-spring-v2","drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);
		return kContainer;
    }
	
	
	public static KieSession realtimeSession(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("drools-spring-v2","drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));
		
		KieBaseConfiguration kconf = ks.newKieBaseConfiguration();
		kconf.setOption(EventProcessingOption.STREAM);
		KieBase kieBase = kContainer.newKieBase(kconf);
		
		KieSessionConfiguration kconfig1 = ks.newKieSessionConfiguration();
		kconfig1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession ksession = kieBase.newKieSession(kconfig1, null);
		
		return ksession;
	}
	
	public static void runRealTimeHeartBeat(KieSession realtimeSession) throws InterruptedException {
		System.out.println("Pocinjem sa real time monitoringom heartbeat!");
		Thread.sleep(1000*60);
		
		Thread t = new Thread() {
            @Override
            public void run() {
                for (int index = 0; index < 30; index++) {
                    HeartBeatEvent event = new HeartBeatEvent();
                    event.setPatientId(1L);
                    realtimeSession.insert(event);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            //do nothing
        }
        realtimeSession.getAgenda().getAgendaGroup("heartbeat-event").setFocus();
        realtimeSession.fireUntilHalt();
        Collection<MonitoringIssue> newEvent = (Collection<MonitoringIssue>) realtimeSession.getObjects(new ClassObjectFilter(MonitoringIssue.class));

        Iterator<MonitoringIssue> iter = newEvent.iterator();
        
        while (iter.hasNext()){
        	MonitoringIssue mi = iter.next();
        	
        	if (mi.getIssue().equals(MonitoringIssue.Issue.ACCELERATED_HEARTBEAT)){
        		String message = "Patient: Rados Acimovic [ 12452 ] has accelerated heartbeat (more than 25 beats in 10 seconds)!";
        		
        		System.out.println(message);
        	}
        }
	}
	
	private static void runRealTimeDialysis(KieSession ksession) throws InterruptedException {
		System.out.println("Pocinjem sa real time monitoringom dijaliza!");
		Thread.sleep(1000*60);
		
		Thread t = new Thread() {
            @Override
            public void run() {
            	
            	Diagnose diagnose = new Diagnose();
        		diagnose.setIllnessName("Chronic Kidney Disease");
        		diagnose.setPatientId(1L);
        		ksession.insert(diagnose);
            	
                for (int index = 0; index < 15; index++) {
                    HeartBeatEvent event = new HeartBeatEvent();
                    event.setPatientId(1L);
                    ksession.insert(event);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
                
                for (int i = 0; i < 5; i++) {
                    UrinalSumEvent event = new UrinalSumEvent();
                    event.setPatientId(1L);
                    event.setAmount(10);
                    ksession.insert(event);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            //do nothing
        }
        ksession.getAgenda().getAgendaGroup("dialysis-event").setFocus();
        ksession.fireUntilHalt();
        Collection<MonitoringIssue> newEvents = (Collection<MonitoringIssue>) ksession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
        
        Iterator<MonitoringIssue> iter = newEvents.iterator();
        
        while (iter.hasNext()){
        	MonitoringIssue mi = iter.next();
        	
        	if (mi.getIssue().equals(MonitoringIssue.Issue.URGENT_DIALYSIS)){
        		String message = "Patient: Kosta Lalic [ 85052 ] needs dialysis urgently!";
        		
        		System.out.println(message);
        	}
        }
        
    }
	
	public static void runRealTimeOxygen(KieSession realtimeSession) {	
		
		System.out.println("Pocinjem sa real time monitoringom oxygen!");
        Thread t = new Thread() {
            @Override
            public void run() {
            	
                for (int index = 0; index < 10; index++) {
                	OxygenLevelEvent event = new OxygenLevelEvent();
                    event.setPatientId(1L);
                    event.setLevel(50);
                    realtimeSession.insert(event);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000*60*1);
        } catch (InterruptedException e) {
            //do nothing
        }
        realtimeSession.getAgenda().getAgendaGroup("oxygen-event").setFocus();
        realtimeSession.fireUntilHalt();
        Collection<MonitoringIssue> newEvents = (Collection<MonitoringIssue>) realtimeSession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
        
        Iterator<MonitoringIssue> iter = newEvents.iterator();
        
        while (iter.hasNext()){
        	MonitoringIssue mi = iter.next();
        	if (mi.getIssue().equals(MonitoringIssue.Issue.OXYGEN_ISSUE)){
        		String message = "Patient: Radmila Matic [ 5023 ] has some serious Oxygen Level issue!";
        		System.out.println(message);
        	}
        }
        
        
        
	}
}
