/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

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
    private static final QName CTAMS_QNAME = new QName("ctams", "CTAMS");
    private static final QName BAND_QNAME = new QName("ctams", "Band");
    private static final QName BAND_CONTEST_QNAME = new QName("ctams", "BandContest");
    private static final QName BAND_RESULT_QNAME = new QName("ctams", "BandResult");
    private static final QName JUDGE_QNAME = new QName("ctams", "Judge");
    private static final QName JUDGE_QUALIFICATION_QNAME = new QName("ctams", "JudgeQualification");
    private static final QName PERSON_QNAME = new QName("ctams", "Person");
    private static final QName SOLO_CONTEST_QNAME = new QName("ctams", "SoloContest");
    private static final QName SOLO_RESULT_QNAME = new QName("ctams", "SoloResult");
    private static final QName SOLOIST_QNAME = new QName("ctams", "Soloist");
    private static final QName VENUE_QNAME = new QName("ctams", "Venue");
    
    @XmlElementDecl(scope = CTAMSDocument.class, namespace = "ctams", name = "CTAMS")
    JAXBElement<CTAMSDocument> createCTAMSDocument(CTAMSDocument value) {
        return new JAXBElement<CTAMSDocument>(CTAMS_QNAME, CTAMSDocument.class, value);
    }
    
    @XmlElementDecl(scope = Band.class, namespace = "ctams", name = "Band")
    JAXBElement<Band> createBand(Band value) {
        return new JAXBElement<Band>(BAND_QNAME, Band.class, value);
    }
    
    @XmlElementDecl(scope = BandContest.class, namespace = "ctams", name = "BandContest")
    JAXBElement<BandContest> createBandContest(BandContest value) {
        return new JAXBElement<BandContest>(BAND_CONTEST_QNAME, BandContest.class, value);
    }
    
    @XmlElementDecl(scope = BandResult.class, namespace = "ctams", name = "BandResult")
    JAXBElement<BandResult> createBandContest(BandResult value) {
        return new JAXBElement<BandResult>(BAND_RESULT_QNAME, BandResult.class, value);
    }
    
    @XmlElementDecl(scope = Judge.class, namespace = "ctams", name = "Judge")
    JAXBElement<Judge> createJudge(Judge value) {
        return new JAXBElement<Judge>(JUDGE_QNAME, Judge.class, value);
    }
    
    @XmlElementDecl(scope = JudgeQualification.class, namespace = "ctams", name = "JudgeQualification")
    JAXBElement<JudgeQualification> createJudgeQualification(JudgeQualification value) {
        return new JAXBElement<JudgeQualification>(JUDGE_QUALIFICATION_QNAME, JudgeQualification.class, value);
    }
    
    @XmlElementDecl(scope = Person.class, namespace = "ctams", name = "Person")
    JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(PERSON_QNAME, Person.class, value);
    }
    
    @XmlElementDecl(scope = SoloContest.class, namespace = "ctams", name = "SoloContest")
    JAXBElement<SoloContest> createSoloContest(SoloContest value) {
        return new JAXBElement<SoloContest>(SOLO_CONTEST_QNAME, SoloContest.class, value);
    }
    
    @XmlElementDecl(scope = SoloResult.class, namespace = "ctams", name = "SoloResult")
    JAXBElement<SoloResult> createSoloResult(SoloResult value) {
        return new JAXBElement<SoloResult>(SOLO_RESULT_QNAME, SoloResult.class, value);
    }
    
    @XmlElementDecl(scope = Venue.class, namespace = "ctams", name = "Venue")
    JAXBElement<Venue> createVenue(Venue value) {
        return new JAXBElement<Venue>(VENUE_QNAME, Venue.class, value);
    }
}
