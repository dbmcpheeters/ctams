/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 *
 * @author atrimble
 */
@XmlRegistry
public class ObjectFactory {
    private static final String NAMESPACE = "ctams";
    
    private static final QName CTAMS_QNAME = new QName(NAMESPACE, "CTAMS");
    private static final QName BAND_QNAME = new QName(NAMESPACE, "Band");
    private static final QName BAND_CONTEST_QNAME = new QName(NAMESPACE, "BandContest");
    private static final QName BAND_CONTEST_ENTRY_QNAME = new QName(NAMESPACE, "BandContestEntry");
    private static final QName BAND_RESULT_QNAME = new QName(NAMESPACE, "BandResult");
    private static final QName BAND_REGISTRATION_QNAME = new QName(NAMESPACE, "BandRegistration");
    private static final QName BAND_MEMBER_QNAME = new QName(NAMESPACE, "BandMember");
    private static final QName JUDGE_QNAME = new QName(NAMESPACE, "Judge");
    private static final QName JUDGE_QUALIFICATION_QNAME = new QName(NAMESPACE, "JudgeQualification");
    private static final QName PERSON_QNAME = new QName(NAMESPACE, "Person");
    private static final QName ROSTER_QNAME = new QName(NAMESPACE, "Roster");
    private static final QName SOLO_CONTEST_QNAME = new QName(NAMESPACE, "SoloContest");
    private static final QName SOLO_CONTEST_ENTRY_QNAME = new QName(NAMESPACE, "SoloContestEntry");
    private static final QName SOLO_RESULT_QNAME = new QName(NAMESPACE, "SoloResult");
    private static final QName SOLO_REGISTRATION_QNAME = new QName(NAMESPACE, "SoloRegistration");
    private static final QName VENUE_QNAME = new QName(NAMESPACE, "Venue");
    
    @XmlElementDecl(scope = CTAMSDocument.class, namespace = NAMESPACE, name = "CTAMS")
    JAXBElement<CTAMSDocument> createCTAMSDocument(CTAMSDocument value) {
        return new JAXBElement<>(CTAMS_QNAME, CTAMSDocument.class, value);
    }
    
    @XmlElementDecl(scope = Band.class, namespace = NAMESPACE, name = "Band")
    JAXBElement<Band> createBand(Band value) {
        return new JAXBElement<>(BAND_QNAME, Band.class, value);
    }
    
    @XmlElementDecl(scope = BandContest.class, namespace = NAMESPACE, name = "BandContest")
    JAXBElement<BandContest> createBandContest(BandContest value) {
        return new JAXBElement<>(BAND_CONTEST_QNAME, BandContest.class, value);
    }
    
    @XmlElementDecl(scope = BandContestEntry.class, namespace = NAMESPACE, name = "BandContestEntry")
    JAXBElement<BandContestEntry> createBandContestEntry(BandContestEntry value) {
        return new JAXBElement<>(BAND_CONTEST_ENTRY_QNAME, BandContestEntry.class, value);
    }
    
    @XmlElementDecl(scope = BandResult.class, namespace = NAMESPACE, name = "BandResult")
    JAXBElement<BandResult> createBandContest(BandResult value) {
        return new JAXBElement<>(BAND_RESULT_QNAME, BandResult.class, value);
    }

    @XmlElementDecl(scope = BandRegistration.class, namespace = NAMESPACE, name = "BandRegistration")
    JAXBElement<BandRegistration> createBandRegistration(BandRegistration value) {
        return new JAXBElement<>(BAND_REGISTRATION_QNAME, BandRegistration.class, value);
    }
    
    @XmlElementDecl(scope = BandMember.class, namespace = NAMESPACE, name = "BandMember")
    JAXBElement<BandMember> createBandMember(BandMember value) {
        return new JAXBElement<>(BAND_MEMBER_QNAME, BandMember.class, value);
    }
    
    @XmlElementDecl(scope = Roster.class, namespace = NAMESPACE, name = "Roster")
    JAXBElement<Roster> createRoster(Roster value) {
        return new JAXBElement<>(ROSTER_QNAME, Roster.class, value);
    }
    
    @XmlElementDecl(scope = Judge.class, namespace = NAMESPACE, name = "Judge")
    JAXBElement<Judge> createJudge(Judge value) {
        return new JAXBElement<>(JUDGE_QNAME, Judge.class, value);
    }
    
    @XmlElementDecl(scope = JudgeQualification.class, namespace = NAMESPACE, name = "JudgeQualification")
    JAXBElement<JudgeQualification> createJudgeQualification(JudgeQualification value) {
        return new JAXBElement<>(JUDGE_QUALIFICATION_QNAME, JudgeQualification.class, value);
    }
    
    @XmlElementDecl(scope = Person.class, namespace = NAMESPACE, name = "Person")
    JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<>(PERSON_QNAME, Person.class, value);
    }
    
    @XmlElementDecl(scope = SoloContest.class, namespace = NAMESPACE, name = "SoloContest")
    JAXBElement<SoloContest> createSoloContest(SoloContest value) {
        return new JAXBElement<>(SOLO_CONTEST_QNAME, SoloContest.class, value);
    }
    
    @XmlElementDecl(scope = SoloContestEntry.class, namespace = NAMESPACE, name = "SoloContestEntry")
    JAXBElement<SoloContestEntry> createSoloContestEntry(SoloContestEntry value) {
        return new JAXBElement<>(SOLO_CONTEST_ENTRY_QNAME, SoloContestEntry.class, value);
    }
    
    @XmlElementDecl(scope = SoloResult.class, namespace = NAMESPACE, name = "SoloResult")
    JAXBElement<SoloResult> createSoloResult(SoloResult value) {
        return new JAXBElement<>(SOLO_RESULT_QNAME, SoloResult.class, value);
    }
    
    @XmlElementDecl(scope = SoloRegistration.class, namespace = NAMESPACE, name = "SoloRegistration")
    JAXBElement<SoloRegistration> createSoloRegistration(SoloRegistration value) {
        return new JAXBElement<>(SOLO_REGISTRATION_QNAME, SoloRegistration.class, value);
    }

    @XmlElementDecl(scope = Venue.class, namespace = NAMESPACE, name = "Venue")
    JAXBElement<Venue> createVenue(Venue value) {
        return new JAXBElement<>(VENUE_QNAME, Venue.class, value);
    }

}
