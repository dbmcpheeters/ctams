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

    private TestUtils() {

    }

    public static void populateData(CrudRepository repo) {
        repo.save(TestFixture.INSTANCE.skye);
        repo.save(TestFixture.INSTANCE.eoin);
        repo.save(TestFixture.INSTANCE.andy);
        repo.save(TestFixture.INSTANCE.jamie);
        repo.save(TestFixture.INSTANCE.bob);
        repo.save(TestFixture.INSTANCE.elaine);
        repo.save(TestFixture.INSTANCE.drummingQual);
        repo.save(TestFixture.INSTANCE.ensembleQual);
        repo.save(TestFixture.INSTANCE.pipingQual);
        repo.save(TestFixture.INSTANCE.judgeEoin);
        repo.save(TestFixture.INSTANCE.judgeAndy);
        repo.save(TestFixture.INSTANCE.judgeJamie);
        repo.save(TestFixture.INSTANCE.judgeBob);
        repo.save(TestFixture.INSTANCE.venue);
        repo.save(TestFixture.INSTANCE.bandContest);
        repo.save(TestFixture.INSTANCE.bandResult);
        repo.save(TestFixture.INSTANCE.soloContest);
        repo.save(TestFixture.INSTANCE.soloResult);
        repo.save(TestFixture.INSTANCE.soloRegistration);
        repo.save(TestFixture.INSTANCE.andyMember);
        repo.save(TestFixture.INSTANCE.jamieMember);
        repo.save(TestFixture.INSTANCE.roster);
        repo.save(TestFixture.INSTANCE.bandRegistration);
        repo.save(TestFixture.INSTANCE.andyInstructor);
    }
}
