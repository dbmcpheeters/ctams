/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.fixture;

import java.util.Collections;
import java.util.List;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class RestFixtures {

    public static List<Band> getBands() {
        return Collections.singletonList(TestFixture.INSTANCE.skye);
    }
}
