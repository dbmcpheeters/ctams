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
        repo.save(TestFixture.INSTANCE.localQual);
        repo.save(TestFixture.INSTANCE.judgeEoin);
        repo.save(TestFixture.INSTANCE.judgeAndy);
        repo.save(TestFixture.INSTANCE.judgeJamie);
        repo.save(TestFixture.INSTANCE.judgeBob);
        repo.save(TestFixture.INSTANCE.judgeElaine);
        repo.save(TestFixture.INSTANCE.hiredJudgeEoin);
        repo.save(TestFixture.INSTANCE.hiredJudgeAndy);
        repo.save(TestFixture.INSTANCE.hiredJudgeJamie);
        repo.save(TestFixture.INSTANCE.hiredJudgeBob);
        repo.save(TestFixture.INSTANCE.hiredJudgeElaine);
        repo.save(TestFixture.INSTANCE.venue);
        repo.save(TestFixture.INSTANCE.result1);
        repo.save(TestFixture.INSTANCE.result2);
        repo.save(TestFixture.INSTANCE.result3);
        repo.save(TestFixture.INSTANCE.result4);
        repo.save(TestFixture.INSTANCE.result5);
        repo.save(TestFixture.INSTANCE.bandContest);
        repo.save(TestFixture.INSTANCE.bandContestEntry);
        repo.save(TestFixture.INSTANCE.bandResult);
        repo.save(TestFixture.INSTANCE.soloContest);
        repo.save(TestFixture.INSTANCE.soloContestEntry);
        repo.save(TestFixture.INSTANCE.soloResult);
        repo.save(TestFixture.INSTANCE.soloRegistration);
        repo.save(TestFixture.INSTANCE.andyMember);
        repo.save(TestFixture.INSTANCE.jamieMember);
        repo.save(TestFixture.INSTANCE.roster1);
        repo.save(TestFixture.INSTANCE.roster2);
        repo.save(TestFixture.INSTANCE.bandRegistration);
    }
}
