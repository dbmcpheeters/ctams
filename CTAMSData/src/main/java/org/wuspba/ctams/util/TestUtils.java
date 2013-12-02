/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.util;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author atrimble
 */
public class TestUtils {
    public static void populateData(CrudRepository repo) {
        repo.save(TestData.INSTANCE.skye);
        repo.save(TestData.INSTANCE.eoin);
        repo.save(TestData.INSTANCE.andy);
        repo.save(TestData.INSTANCE.jamie);
        repo.save(TestData.INSTANCE.bob);
        repo.save(TestData.INSTANCE.elaine);
        repo.save(TestData.INSTANCE.drummingQual);
        repo.save(TestData.INSTANCE.ensembleQual);
        repo.save(TestData.INSTANCE.pipingQual);
        repo.save(TestData.INSTANCE.judgeEoin);
        repo.save(TestData.INSTANCE.judgeAndy);
        repo.save(TestData.INSTANCE.judgeJamie);
        repo.save(TestData.INSTANCE.judgeBob);
        repo.save(TestData.INSTANCE.venue);
        repo.save(TestData.INSTANCE.bandContest);
        repo.save(TestData.INSTANCE.bandResult);
        repo.save(TestData.INSTANCE.soloContest);
        repo.save(TestData.INSTANCE.soloResult);
        repo.save(TestData.INSTANCE.soloRegistration);
        repo.save(TestData.INSTANCE.andyMember);
        repo.save(TestData.INSTANCE.jamieMember);
        repo.save(TestData.INSTANCE.roster);
        repo.save(TestData.INSTANCE.bandRegistration);
        repo.save(TestData.INSTANCE.andyInstructor);
    }
}
