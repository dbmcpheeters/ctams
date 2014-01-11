/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.util;

import java.util.Date;
import java.util.GregorianCalendar;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandContestEntry;
import org.wuspba.ctams.model.BandEventType;
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.BandResult;
import org.wuspba.ctams.model.BandType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.Instrument;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgePanelType;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.JudgeType;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Result;
import org.wuspba.ctams.model.Roster;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloContestEntry;
import org.wuspba.ctams.model.SoloEventType;
import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.model.SoloResult;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */
public enum TestFixture {
    INSTANCE;

    private final Date date = new GregorianCalendar(2013, 5, 4, 21, 56).getTime();
    private final Date start = new GregorianCalendar(2013, 1, 1, 22, 59).getTime();
    private final Date end = new GregorianCalendar(2013, 11, 30, 20, 0).getTime();

    public Band skye; // NOSONAR
    public Band scots; // NOSONAR
    public BandRegistration bandRegistration; // NOSONAR
    public Roster roster1; // NOSONAR
    public Roster roster2; // NOSONAR
    public BandMember andyMember; // NOSONAR
    public BandMember jamieMember; // NOSONAR
    public BandMember bobMember; // NOSONAR
    public BandContest bandContest; // NOSONAR
    public BandContest bandNonContest; // NOSONAR
    public BandContestEntry bandContestEntry; // NOSONAR
    public BandResult bandResult; // NOSONAR
    public Result result1; // NOSONAR
    public Result result2; // NOSONAR
    public Result result3; // NOSONAR
    public Result result4; // NOSONAR
    public Result result5; // NOSONAR
    public SoloContest soloContest; // NOSONAR
    public SoloContest soloNonContest; // NOSONAR
    public SoloContestEntry soloContestEntry; // NOSONAR
    public SoloResult soloResult; // NOSONAR
    public JudgeQualification drummingQual; // NOSONAR
    public JudgeQualification pipingQual; // NOSONAR
    public JudgeQualification ensembleQual; // NOSONAR
    public JudgeQualification localQual; // NOSONAR
    public Judge judgeEoin; // NOSONAR
    public Judge judgeAndy; // NOSONAR
    public Judge judgeJamie; // NOSONAR
    public Judge judgeBob; // NOSONAR
    public Judge judgeElaine; // NOSONAR
    public HiredJudge hiredJudgeEoin; // NOSONAR
    public HiredJudge hiredJudgeAndy; // NOSONAR
    public HiredJudge hiredJudgeJamie; // NOSONAR
    public HiredJudge hiredJudgeBob; // NOSONAR
    public HiredJudge hiredJudgeElaine; // NOSONAR
    public Person eoin; // NOSONAR
    public Person andy; // NOSONAR
    public Person jamie; // NOSONAR
    public Person bob; // NOSONAR
    public Person elaine; // NOSONAR
    public Person mason; // NOSONAR
    public Venue venue; // NOSONAR
    public SoloRegistration soloRegistration; // NOSONAR

    TestFixture() {
        createBand();
        birthEoin();
        birthAndy();
        birthJamie();
        birthBob();
        birthElaine();
        birthMason();
        createJudgeQualifications();
        createJudgeEoin();
        createJudgeAndy();
        createJudgeJamie();
        createJudgeBob();
        createJudgeElaine();
        createHiredJudgeEoin();
        createHiredJudgeAndy();
        createHiredJudgeJamie();
        createHiredJudgeBob();
        createHiredJudgeElaine();
        createVenue();
        createResults();
        createBandContest();
        createBandContestEntry();
        createBandResult();
        createSoloContest();
        createSoloContestEntry();
        createSoloResult();
        createSoloRegistration();
        createBandMembers();
        createRoster();
        createBandRegistration();
    }

    private void createJudgeQualifications() {
        pipingQual = new JudgeQualification();
        pipingQual.setId("pipingQual");
        pipingQual.setPanel(JudgePanelType.A);
        pipingQual.setType(JudgeType.BAND_PIPING);

        ensembleQual = new JudgeQualification();
        ensembleQual.setId("ensembleQual");
        ensembleQual.setPanel(JudgePanelType.B);
        ensembleQual.setType(JudgeType.BAND_ENSEMBLE);

        drummingQual = new JudgeQualification();
        drummingQual.setId("drummingQual");
        drummingQual.setPanel(JudgePanelType.LOCAL);
        drummingQual.setType(JudgeType.BAND_DRUMMING);
        
        localQual = new JudgeQualification();
        localQual.setId("localQual");
        localQual.setPanel(JudgePanelType.LOCAL);
        localQual.setType(JudgeType.SOLO_SNARE);
    }

    private void createJudgeEoin() {
        judgeEoin = new Judge();
        judgeEoin.setId("eoinJudge");
        judgeEoin.setPerson(eoin);
        judgeEoin.getQualifications().add(drummingQual);
    }

    private void createJudgeAndy() {
        judgeAndy = new Judge();
        judgeAndy.setId("andyJudge");
        judgeAndy.setPerson(andy);
        judgeAndy.getQualifications().add(ensembleQual);
    }

    private void createJudgeJamie() {
        judgeJamie = new Judge();
        judgeJamie.setId("jamieJudge");
        judgeJamie.setPerson(jamie);
        judgeJamie.getQualifications().add(pipingQual);
    }

    private void createJudgeBob() {
        judgeBob = new Judge();
        judgeBob.setId("bobJudge");
        judgeBob.setPerson(bob);
        judgeBob.getQualifications().add(pipingQual);
    }

    private void createJudgeElaine() {
        judgeElaine = new Judge();
        judgeElaine.setId("elaineJudge");
        judgeElaine.setPerson(elaine);
        judgeElaine.getQualifications().add(localQual);
    }

    private void createHiredJudgeEoin() {
        hiredJudgeEoin = new HiredJudge();
        hiredJudgeEoin.setId("eoinHiredJudge");
        hiredJudgeEoin.setJudge(judgeEoin);
        hiredJudgeEoin.setType(JudgeType.BAND_DRUMMING);
    }

    private  void createHiredJudgeAndy() {
        hiredJudgeAndy = new HiredJudge();
        hiredJudgeAndy.setId("andyHiredJudge");
        hiredJudgeAndy.setJudge(judgeAndy);
        hiredJudgeAndy.setType(JudgeType.BAND_ENSEMBLE);
    }

    private  void createHiredJudgeJamie() {
        hiredJudgeJamie = new HiredJudge();
        hiredJudgeJamie.setId("jamieHiredJudge");
        hiredJudgeJamie.setJudge(judgeJamie);
        hiredJudgeJamie.setType(JudgeType.BAND_PIPING);
    }

    private  void createHiredJudgeBob() {
        hiredJudgeBob = new HiredJudge();
        hiredJudgeBob.setId("bobHiredJudge");
        hiredJudgeBob.setJudge(judgeBob);
        hiredJudgeBob.setType(JudgeType.BAND_PIPING);
    }

    private  void createHiredJudgeElaine() {
        hiredJudgeElaine = new HiredJudge();
        hiredJudgeElaine.setId("elaineHiredJudge");
        hiredJudgeElaine.setJudge(judgeElaine);
        hiredJudgeElaine.setType(JudgeType.SOLO_SNARE);
    }

    private  void createVenue() {
        venue = new Venue();
        venue.setAddress("333 Pikes Peak Ave.");
        venue.setBandContest(true);
        venue.setBranch(Branch.INTERMOUNTAIN);
        venue.setCity("Colorado Springs");
        venue.setEmail("joseph.poch@innovativemoments.com");
        venue.setId("venue");
        venue.setLocation("Memorial Park");
        venue.setName("Pikes Peak Celtic Festival");
        venue.setPhone("555-555-5555");
        venue.setSoloContest(true);
        venue.setSponsor("Innovative Moments");
        venue.setState("CO");
        venue.setUrl("pikespeakcelticfestival.com");
        venue.setZip("80903");
    }
    
    private  void birthEoin() {
        eoin = new Person();
        eoin.setAddress("1555 Northrop Grumman Pt.");
        eoin.setBranch(Branch.GREATBASIN);
        eoin.setCity("Colorado Springs");
        eoin.setEmail("eoin@comcast.net");
        eoin.setFirstName("Eoin");
        eoin.setId("eoinPerson");
        eoin.setLastName("McMahon");
        eoin.setLifeMember(false);
        eoin.setMiddleName("");
        eoin.setNickName("The Mac");
        eoin.setNotes("");
        eoin.setPhone("555-555-5555");
        eoin.setState("CO");
        eoin.setSuffix("");
        eoin.setTitle("Mr.");
        eoin.setZip("80902");
    }
    
    private  void birthAndy() {
        andy = new Person();
        andy.setAddress("214 S Hancock Ave.");
        andy.setBranch(Branch.INTERMOUNTAIN);
        andy.setCity("Colorado Springs");
        andy.setEmail("andy.trimble@gmail.com");
        andy.setFirstName("Andy");
        andy.setId("andyPerson");
        andy.setLastName("Trimble");
        andy.setLifeMember(true);
        andy.setMiddleName("Ryan");
        andy.setNickName("The Man");
        andy.setNotes("What a bad ass!");
        andy.setPhone("719-555-5555");
        andy.setState("CO");
        andy.setSuffix("");
        andy.setTitle("Mr.");
        andy.setZip("80903");
    }
    
    private  void birthJamie() {
        jamie = new Person();
        jamie.setAddress("231 Lame St.");
        jamie.setBranch(Branch.NORTHERN);
        jamie.setCity("Denver");
        jamie.setEmail("douche@baggery.com");
        jamie.setFirstName("Jamie");
        jamie.setId("jamiePerson");
        jamie.setLastName("Cuthill");
        jamie.setLifeMember(false);
        jamie.setMiddleName("Ross");
        jamie.setNickName("The Douche");
        jamie.setNotes("Lame!");
        jamie.setPhone("303-555-5555");
        jamie.setState("CO");
        jamie.setSuffix("");
        jamie.setTitle("Mrs.");
        jamie.setZip("80831");
    }

    private  void birthBob() {
        bob = new Person();
        bob.setAddress("111 Bob Ave.");
        bob.setBranch(Branch.SOUTHERN);
        bob.setCity("Denver");
        bob.setEmail("dont.have@one.com");
        bob.setFirstName("Robert");
        bob.setId("bobPerson");
        bob.setLastName("Mason");
        bob.setLifeMember(false);
        bob.setMiddleName("");
        bob.setNickName("The Old Man");
        bob.setNotes("");
        bob.setPhone("720-555-5555");
        bob.setState("CO");
        bob.setSuffix("");
        bob.setTitle("Mr.");
        bob.setZip("80891");
    }

    private  void birthElaine() {
        elaine = new Person();
        elaine.setAddress("222 Elaine Ave.");
        elaine.setBranch(Branch.INTERMOUNTAIN);
        elaine.setCity("Fort Collins");
        elaine.setEmail("elaine.hoffman@aol.com");
        elaine.setFirstName("Elaine");
        elaine.setId("elainePerson");
        elaine.setLastName("Hoffman");
        elaine.setLifeMember(true);
        elaine.setMiddleName("");
        elaine.setNickName("The Lady");
        elaine.setNotes("");
        elaine.setPhone("858-555-5555");
        elaine.setState("CO");
        elaine.setSuffix("");
        elaine.setTitle("Mrs.");
        elaine.setZip("80291");
    }

    private  void birthMason() {
        mason = new Person();
        mason.setAddress("999 Mason St.");
        mason.setBranch(Branch.INTERMOUNTAIN);
        mason.setCity("Albuquerque");
        mason.setEmail("head.butt@gmail.com");
        mason.setFirstName("Mason");
        mason.setId("masonPerson");
        mason.setLastName("Parsons");
        mason.setLifeMember(false);
        mason.setMiddleName("");
        mason.setNickName("Crazy Eyes");
        mason.setNotes("");
        mason.setPhone("505-555-5555");
        mason.setState("NM");
        mason.setSuffix("");
        mason.setTitle("Mr.");
        mason.setZip("90210");
    }

    private  void createBandContest() {
        bandContest = new BandContest();
        bandContest.setDate(date);
        bandContest.getJudges().add(hiredJudgeEoin);
        bandContest.getJudges().add(hiredJudgeAndy);
        bandContest.setEventType(BandEventType.MEDLEY);
        bandContest.setGrade(Grade.FOUR);
        bandContest.setId("bandContest");
        bandContest.getJudges().add(hiredJudgeJamie);
        bandContest.getJudges().add(hiredJudgeBob);
        bandContest.setSeason(2013);
        bandContest.setVenue(venue);
        
        bandNonContest = new BandContest();
        bandNonContest.setDate(date);
        bandNonContest.getJudges().add(hiredJudgeEoin);
        bandNonContest.getJudges().add(hiredJudgeAndy);
        bandNonContest.setEventType(BandEventType.MEDLEY);
        bandNonContest.setGrade(Grade.FOUR);
        bandNonContest.setId("bandNonContest");
        bandNonContest.getJudges().add(hiredJudgeJamie);
        bandNonContest.getJudges().add(hiredJudgeBob);
        bandNonContest.setSeason(2013);
        bandNonContest.setVenue(venue);
    }

    private void createBandContestEntry() {
        bandContestEntry = new BandContestEntry();
        bandContestEntry.setBand(skye);
        bandContestEntry.setContest(bandContest);
        bandContestEntry.setId("bandContestEntry");
    }

    private void createSoloContestEntry() {
        soloContestEntry = new SoloContestEntry();
        soloContestEntry.setPerson(elaine);
        soloContestEntry.setContest(soloContest);
        soloContestEntry.setId("soloContestEntry");
    }

    private  void createBand() {
        skye = new Band();
        skye.setAddress("214 S Hancock");
        skye.setCity("Colorado Springs");
        skye.setState("CO");
        skye.setBranch(Branch.INTERMOUNTAIN);
        skye.setDissolved(true);
        skye.setEmail("andy.trimble@gmail.com");
        skye.setGrade(Grade.THREE);
        skye.setId("skyeBand");
        skye.setName("Colorado Skye");
        skye.setTelephone("719-963-9844");
        skye.setType(BandType.COMPETITIVE);
        skye.setUrl("www.coloradoskye.com");
        skye.setZip("80903");

        scots = new Band();
        scots.setAddress("999 Holywood Blvd");
        scots.setCity("Holywood");
        scots.setState("CA");
        scots.setBranch(Branch.SOUTHERN);
        scots.setDissolved(false);
        scots.setEmail("pipe.major@example.com");
        scots.setGrade(Grade.ONE);
        scots.setId("laScots");
        scots.setName("LA Scots");
        scots.setTelephone("555-555-555");
        scots.setType(BandType.COMPETITIVE);
        scots.setUrl("www.lascots.com");
        scots.setZip("90210");
    }

    private void createResults() {
        result1 = new Result();
        result1.setId("result1");
        result1.setType(JudgeType.BAND_PIPING);
        result1.setPlace(4);
        result1.setPoints(99);
        result1.setEvaluation("C");
        
        result2 = new Result();
        result2.setId("result2");
        result2.setType(JudgeType.BAND_PIPING);
        result2.setPlace(4);
        result2.setPoints(98);
        result2.setEvaluation("D");
        
        result3 = new Result();
        result3.setId("result3");
        result3.setType(JudgeType.BAND_ENSEMBLE);
        result3.setPlace(1);
        result3.setPoints(97);
        result3.setEvaluation("B");
        
        result4 = new Result();
        result4.setId("result4");
        result4.setType(JudgeType.BAND_DRUMMING);
        result4.setPlace(1);
        result4.setPoints(96);
        result4.setEvaluation("A");
        
        result5 = new Result();
        result5.setId("result5");
        result5.setType(JudgeType.SOLO_PIPING);
        result5.setPlace(1);
        result5.setPoints(95);
        result5.setEvaluation("4");
    }

    private  void createBandResult() {
        bandResult = new BandResult();
        bandResult.setBand(skye);
        bandResult.setChallengeUp(false);
        bandResult.setContest(bandContest);
        bandResult.getResults().add(result1);
        bandResult.getResults().add(result2);
        bandResult.getResults().add(result3);
        bandResult.getResults().add(result4);
        bandResult.setId("bandResult");
        bandResult.setPlace(2);
        bandResult.setPoints(99);
    }

    private  void createSoloContest() {
        soloContest = new SoloContest();
        soloContest.setContestants(1);
        soloContest.setDate(date);
        soloContest.setEventType(SoloEventType.MSR);
        soloContest.setGrade(Grade.TWO);
        soloContest.setId("soloContest");
        soloContest.getJudges().add(hiredJudgeBob);
        soloContest.getJudges().add(hiredJudgeJamie);
        soloContest.getJudges().add(hiredJudgeAndy);
        soloContest.setLeet(0);
        soloContest.setSeason(2013);
        soloContest.setVenue(venue);
        
        soloNonContest = new SoloContest();
        soloNonContest.setContestants(1);
        soloNonContest.setDate(date);
        soloNonContest.setEventType(SoloEventType.MSR);
        soloNonContest.setGrade(Grade.TWO);
        soloNonContest.setId("soloNonContest");
        soloNonContest.getJudges().add(hiredJudgeBob);
        soloNonContest.getJudges().add(hiredJudgeJamie);
        soloNonContest.getJudges().add(hiredJudgeAndy);
        soloNonContest.setLeet(0);
        soloNonContest.setSeason(2013);
        soloNonContest.setVenue(venue);
    }

    private  void createSoloResult() {
        soloResult = new SoloResult();
        soloResult.setContest(soloContest);
        soloResult.getResults().add(result5);
        soloResult.setPlace(1);
        soloResult.setId("soloResult");
        soloResult.setSoloist(elaine);
    }

    private  void createSoloRegistration() {
        soloRegistration = new SoloRegistration();
        soloRegistration.setEnd(end);
        soloRegistration.setGrade(Grade.TWO);
        soloRegistration.setId("soloRegistration");
        soloRegistration.setPerson(elaine);
        soloRegistration.setSeason(2013);
        soloRegistration.setStart(start);
        soloRegistration.setType(Instrument.PIPING);
        soloRegistration.setNumber("129");
    }

    private  void createBandMembers() {
        andyMember = new BandMember();
        andyMember.setId("andyMember");
        andyMember.setPerson(andy);
        andyMember.setType(BandMemberType.PIPE_MAJOR);
        
        jamieMember = new BandMember();
        jamieMember.setId("jamieMember");
        jamieMember.setPerson(jamie);
        jamieMember.setType(BandMemberType.PIPER);
        
        bobMember = new BandMember();
        bobMember.setId("bobMember");
        bobMember.setPerson(bob);
        bobMember.setType(BandMemberType.PIPER);
    }

    private  void createRoster() {
        roster1 = new Roster();
        roster1.setId("roster1");
        roster1.setBand(skye);
        roster1.setVersion(1);
        roster1.setSeason(2013);
        roster1.getMembers().add(andyMember);
        roster1.getMembers().add(jamieMember);
        
        roster2 = new Roster();
        roster2.setId("roster2");
        roster2.setBand(skye);
        roster2.setVersion(2);
        roster2.setSeason(2013);
        roster2.getMembers().add(andyMember);
        roster2.getMembers().add(jamieMember);
    }

    private  void createBandRegistration() {
        bandRegistration = new BandRegistration();
        bandRegistration.setBand(skye);
        bandRegistration.setEnd(end);
        bandRegistration.setGrade(Grade.THREE);
        bandRegistration.setId("bandRegistration");
        bandRegistration.setSeason(2009);
        bandRegistration.setStart(start);
    }

}
