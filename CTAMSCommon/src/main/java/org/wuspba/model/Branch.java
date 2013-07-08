/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 *
 * @author atrimble
 */
@XmlEnum
public enum Branch {
    NORTHERN,
    INTERMOUNTAIN,
    GREATBASIN,
    SOUTHERN,
    OTHER
}
