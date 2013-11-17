/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.util;

import java.util.Date;
import java.util.GregorianCalendar;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandEventType;
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.BandResult;
import org.wuspba.ctams.model.BandType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Instructor;
import org.wuspba.ctams.model.Instrument;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgePanelType;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.JudgeType;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Roster;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloEventType;
import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.model.SoloResult;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */
public enum TestData {
    INSTANCE;

    private final Date date = new GregorianCalendar(2013, 5, 4, 21, 56).getTime();
    private final Date start = new GregorianCalendar(2013, 6, 4, 22, 59).getTime();
    private final Date end = new GregorianCalendar(2013, 5, 3, 20, 0).getTime();

    public Band skye;
    public BandRegistration bandRegistration;
    public Roster roster;
    public BandMember andyMember;
    public BandMember jamieMember;
    public BandContest bandContest;
    public BandResult bandResult;
    public SoloContest soloContest;
    public SoloResult soloResult;
    public JudgeQualification drummingQual;
    public JudgeQualification pipingQual;
    public JudgeQualification ensembleQual;
    public Judge judgeEoin;
    public Judge judgeAndy;
    public Judge judgeJamie;
    public Judge judgeBob;
    public Person eoin;
    public Person andy;
    public Person jamie;
    public Person bob;
    public Person elaine;
    public Venue venue;
    public SoloRegistration soloRegistration;
    public Instructor andyInstructor;

    TestData() {
        createBand();
        birthEoin();
        birthAndy();
        birthJamie();
        birthBob();
        birthElaine();
        createJudgeQualifications();
        createJudgeEoin();
        createJudgeAndy();
        createJudgeJamie();
        createJudgeBob();
        createVenue();
        createBandContest();
        createBandResult();
        createSoloContest();
        createSoloResult();
        createSoloRegistration();
        createBandMembers();
        createRoster();
        createBandRegistration();
        createInstructor();
    }

    private void createJudgeQualifications() {
        pipingQual = new JudgeQualification();
        pipingQual.setId("pipingQual");
        pipingQual.setPanel(JudgePanelType.A);
        pipingQual.setType(JudgeType.BandPiping);

        ensembleQual = new JudgeQualification();
        ensembleQual.setId("ensembleQual");
        ensembleQual.setPanel(JudgePanelType.B);
        ensembleQual.setType(JudgeType.BandEnsemble);

        drummingQual = new JudgeQualification();
        drummingQual.setId("drummingQual");
        drummingQual.setPanel(JudgePanelType.Local);
        drummingQual.setType(JudgeType.BandDrumming);
    }

    private void createJudgeEoin() {
        judgeEoin = new Judge();
        judgeEoin.setId("eoinJudge");
        judgeEoin.setPerson(eoin);
        judgeEoin.getQualifications().add(drummingQual);
    }

    private  void createJudgeAndy() {
        judgeAndy = new Judge();
        judgeAndy.setId("andyJudge");
        judgeAndy.setPerson(andy);
        judgeAndy.getQualifications().add(ensembleQual);
    }

    private  void createJudgeJamie() {
        judgeJamie = new Judge();
        judgeJamie.setId("jamieJudge");
        judgeJamie.setPerson(jamie);
        judgeJamie.getQualifications().add(pipingQual);
    }

    private  void createJudgeBob() {
        judgeBob = new Judge();
        judgeBob.setId("bobJudge");
        judgeBob.setPerson(bob);
        judgeBob.getQualifications().add(pipingQual);
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

    private  void createBandContest() {
        bandContest = new BandContest();
        bandContest.setDate(date);
        bandContest.setDrumming(judgeEoin);
        bandContest.setEnsemble(judgeAndy);
        bandContest.setEventType(BandEventType.MEDLEY);
        bandContest.setGrade(Grade.FOUR);
        bandContest.setId("bandContest");
        bandContest.setPiping1(judgeJamie);
        bandContest.setPiping2(judgeBob);
        bandContest.setSeason(2013);
        bandContest.setVenue(venue);
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
        skye.setType(BandType.Competitive);
        skye.setUrl("www.coloradoskye.com");
        skye.setZip("80903");
    }

    private  void createBandResult() {
        bandResult = new BandResult();
        bandResult.setBand(skye);
        bandResult.setChallengeUp(false);
        bandResult.setContest(bandContest);
        bandResult.setDrummingEval("A");
        bandResult.setDrummingPlace(1);
        bandResult.setEnsembleEval("B");
        bandResult.setEnsemblePlace(1);
        bandResult.setId("bandResult");
        bandResult.setPiping1Eval("C");
        bandResult.setPiping1Place(4);
        bandResult.setPiping2Eval("D");
        bandResult.setPiping2Place(4);
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
        soloContest.setJudge2(judgeBob);
        soloContest.setJudge3(judgeJamie);
        soloContest.setLeet(0);
        soloContest.setPrimaryJudge(judgeAndy);
        soloContest.setSeason(2013);
        soloContest.setVenue(venue);
    }

    private  void createSoloResult() {
        soloResult = new SoloResult();
        soloResult.setContest(soloContest);
        soloResult.setCpl("4");
        soloResult.setId("soloResult");
        soloResult.setPlace(1);
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
        soloRegistration.setType(Instrument.Piping);
        soloRegistration.setNumber(129);
    }

    private  void createBandMembers() {
        andyMember = new BandMember();
        andyMember.setId("andyMember");
        andyMember.setPerson(andy);
        andyMember.setType(BandMemberType.PipeMajor);
        
        jamieMember = new BandMember();
        jamieMember.setId("jameiMember");
        jamieMember.setPerson(jamie);
        jamieMember.setType(BandMemberType.Piper);
    }

    private  void createRoster() {
        roster = new Roster();
        roster.setId("roster");
        roster.getMembers().add(andyMember);
        roster.getMembers().add(jamieMember);
    }

    private  void createBandRegistration() {
        bandRegistration = new BandRegistration();
        bandRegistration.setBand(skye);
        bandRegistration.setEnd(end);
        bandRegistration.setGrade(Grade.ONE);
        bandRegistration.setId("bandRegistration");
        bandRegistration.setRoster(roster);
        bandRegistration.setSeason(2009);
        bandRegistration.setStart(start);
    }

    private  void createInstructor() {
        andyInstructor = new Instructor();
        andyInstructor.setId("andyInstructor");
        andyInstructor.setPerson(andy);
        andyInstructor.setType(Instrument.Piping);
    }
}
