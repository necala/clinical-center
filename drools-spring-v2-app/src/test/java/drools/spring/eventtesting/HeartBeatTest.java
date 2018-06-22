package drools.spring.eventtesting;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClassObjectFilter;
import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.beans.factory.annotation.Autowired;

import drools.spring.example.model.MonitoringIssue;
import drools.spring.example.model.Patient;
import drools.spring.example.model.events.HeartBeatEvent;
import drools.spring.example.model.events.OxygenLevelEvent;
import drools.spring.example.service.PatientService;

public class HeartBeatTest {
	
	@Test
	public void testOxygenProblem(){
		
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.newKieContainer(kieServices.newReleaseId("drools-spring-v2", "drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));
		
		
		KieBaseConfiguration kconf = kieServices.newKieBaseConfiguration();
		kconf.setOption(EventProcessingOption.STREAM);
		KieBase kieBase = kContainer.newKieBase(kconf);
		
		KieSessionConfiguration kconfig1 = kieServices.newKieSessionConfiguration();
		kconfig1.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
		KieSession kSession1 = kieBase.newKieSession(kconfig1, null);
		
		KieSessionConfiguration kconfig2 = kieServices.newKieSessionConfiguration();
		kconfig2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
		KieSession kSession2 = kieBase.newKieSession(kconfig2, null);
		
		KieSessionConfiguration kconfig3 = kieServices.newKieSessionConfiguration();
		kconfig3.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession kSession3 = kieBase.newKieSession(kconfig3, null);
		
		KieSessionConfiguration kconfig4 = kieServices.newKieSessionConfiguration();
		kconfig4.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession kSession4 = kieBase.newKieSession(kconfig4, null);
		
		runPositiveTest(kSession1);
		runNegativeTest(kSession2);
		runRealtimePositiveTest(kSession3);
		runRealtimeNegativeTest(kSession4);
	}
	
	
	private void runPositiveTest(KieSession kieSession){
		SessionPseudoClock clock = kieSession.getSessionClock();
		for (int i = 0; i < 30; i++) {
            HeartBeatEvent event = new HeartBeatEvent();
            event.setPatientId(1L);
            kieSession.insert(event);
        }
		
		clock.advanceTime(5, TimeUnit.SECONDS);
		kieSession.getAgenda().getAgendaGroup("heartbeat-event").setFocus();
		kieSession.fireUntilHalt();
		Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
		assertEquals(newEvents.size(), 1);
		MonitoringIssue mi = (MonitoringIssue) newEvents.iterator().next();
		assertThat(mi.getPatientId(), equalTo(1L));
		assertThat(mi.getIssue(), equalTo(MonitoringIssue.Issue.ACCELERATED_HEARTBEAT));
		
		String message = "Patient: Nikola Stanojevic [ 1785 ] has accelerated heartbeat!";
		
		System.out.println(message);
	}
	
	private void runNegativeTest(KieSession kieSession){
		SessionPseudoClock clock = kieSession.getSessionClock();
		for (int i = 0; i < 10; i++) {
            HeartBeatEvent event = new HeartBeatEvent();
            event.setPatientId(1L);
            kieSession.insert(event);
        }
		
		clock.advanceTime(15, TimeUnit.SECONDS);
		kieSession.getAgenda().getAgendaGroup("heartbeat-event").setFocus();
		int firedRules = kieSession.fireAllRules();
		assertEquals(firedRules, 0);
		Collection<?> newEvents = kieSession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
		assertEquals(newEvents.size(), 0);

	}
	
	
	private void runRealtimePositiveTest(KieSession ksession) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int index = 0; index < 30; index++) {
                    HeartBeatEvent event = new HeartBeatEvent();
                    event.setPatientId(1L);
                    ksession.insert(event);
                    try {
                        Thread.sleep(10);
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
        ksession.getAgenda().getAgendaGroup("heartbeat-event").setFocus();
        ksession.fireUntilHalt();
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
        assertThat(newEvents.size(), equalTo(1));
        
        String message = "Patient: Milan Minic [ 2085 ] has accelerated heartbeat!";
		
		System.out.println(message);
    }
	
	
	private void runRealtimeNegativeTest(KieSession ksession) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int index = 0; index < 10; index++) {
                    HeartBeatEvent event = new HeartBeatEvent();
                    event.setPatientId(1L);
                    ksession.insert(event);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            //do nothing
        }
        ksession.getAgenda().getAgendaGroup("heartbeat-event").setFocus();
        ksession.fireAllRules();
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(MonitoringIssue.class));
        assertThat(newEvents.size(), equalTo(0));
    }
}
