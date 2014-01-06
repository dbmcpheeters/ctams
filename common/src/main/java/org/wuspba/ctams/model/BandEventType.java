/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 *
 * @author atrimble
 */
@XmlEnum
public enum BandEventType {
    MSR,
    MEDLEY,
    QMM,
    DRUM_SALUTE,
    MINI_BAND,
    TRIO,
    QUARTET,
    MSM68
}
