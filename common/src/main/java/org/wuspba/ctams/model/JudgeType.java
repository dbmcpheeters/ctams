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
public enum JudgeType {
    BAND_PIPING, BAND_DRUMMING, BAND_ENSEMBLE,
    SOLO_PIPING, PIOBAIREACHD, SOLO_SNARE,
    SOLO_BASS_TENOR, DRUM_MAJOR
}
