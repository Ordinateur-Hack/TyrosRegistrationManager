package com.yamaha.model.editor.Style;

import com.yamaha.model.editor.Style.StyleCategory;

import java.util.ArrayList;
import java.util.List;

public enum StyleName {

    HARD_ROCK(StyleCategory.POP_AND_ROCK, "HardRock", "16", "40"),
    EIGHTIES_POWER_ROCK(StyleCategory.POP_AND_ROCK, "80'sPowerRock", "16", "3F"),
    EIGHTIES_POP_ROCK(StyleCategory.POP_AND_ROCK, "80'sPopRock", "16", "53"),
    EIGHTIES_GTR_POP(StyleCategory.POP_AND_ROCK, "80'sGtrPop", "16", "51"),
    BRIT_ROCK_POP(StyleCategory.POP_AND_ROCK, "BritRockPop", "16", "52"),
    EASY_POP(StyleCategory.POP_AND_ROCK, "Easy Pop (Standard)", "17", "40"),
    LIVE8BEAT(StyleCategory.POP_AND_ROCK, "Live8Beat", "16", "46"),
    CLASSIC8BEAT(StyleCategory.POP_AND_ROCK, "Classic8Beat", "17", "21"),
    COOL8BEAT(StyleCategory.POP_AND_ROCK, "Cool8Beat", "16", "03"),
    UK_SOUL_POP(StyleCategory.POP_AND_ROCK, "UKSoulPop", "16", "85"),
    EIGHTIES_SYNTH_ROCK(StyleCategory.POP_AND_ROCK, "80'sSynthRock", "22", "82"),
    EIGHTIES_POP(StyleCategory.POP_AND_ROCK, "80'sPop", "16", "54"),
    SIXTIES_VINTAGE_ROCK(StyleCategory.POP_AND_ROCK, "60'sVintageRock", "16", "44"),
    SIXTIES_PIANO_POP(StyleCategory.POP_AND_ROCK, "60'sPianoPop", "17", "23"),
    SIXTIES_VINTAGE_POP(StyleCategory.POP_AND_ROCK, "60'sVintagePop", "17", "AB"),
    CONTEMP_POP(StyleCategory.POP_AND_ROCK, "ContempPop", "20", "12"),
    CHART_PIANO_SHFL(StyleCategory.POP_AND_ROCK, "ChartPianoShfl", "24", "08"),
    CHART_ROCK_SHFL(StyleCategory.POP_AND_ROCK, "ChartRockShfl", "24", "09"),
    NINETIES_ROCK_BALLAD(StyleCategory.POP_AND_ROCK, "90'sRockBallad", "20", "49"),
    EIGHTIES_8BEAT(StyleCategory.POP_AND_ROCK, "80's8Beat", "17", "26"),
    STANDARD_ROCK(StyleCategory.POP_AND_ROCK, "StandardRock", "16", "47"),
    CONTEMP_ROCK(StyleCategory.POP_AND_ROCK, "ContempRock", "16", "4E"),
    ACOUSTIC_ROCK(StyleCategory.POP_AND_ROCK, "AcousticRock", "20", "44"),
    FUNK_POP_ROCK(StyleCategory.POP_AND_ROCK, "FunkPopRock", "20", "43"),
    POWER_ROCK(StyleCategory.POP_AND_ROCK, "PowerRock", "16", "43"),
    UPTEMPO_8BEAT(StyleCategory.POP_AND_ROCK, "Uptempo8Beat", "17", "20"),
    EIGHT_BEAT_MODERN(StyleCategory.POP_AND_ROCK, "8BeatModern", "16", "04"),
    VINTAGE_GTR_POP(StyleCategory.POP_AND_ROCK, "VintageGtrPop", "16", "01"),
    WEST_COAST_POP(StyleCategory.POP_AND_ROCK, "WestCoastPop", "20", "04"),
    STRAIGHT_8POP(StyleCategory.POP_AND_ROCK, "Straight8Pop", "16", "1D"),
    SOFT_ROCK(StyleCategory.POP_AND_ROCK, "SoftRock", "16", "42"),
    CONTEMP_ROCK_BLD(StyleCategory.POP_AND_ROCK, "ContempRockBld", "20", "45"),
    BRIT_POP(StyleCategory.POP_AND_ROCK, "BritPop", "20", "01"),
    BRIT_POP_SWING(StyleCategory.POP_AND_ROCK, "BritPopSwing", "1A", "00"),
    SIXTIES_CHART_SWING(StyleCategory.POP_AND_ROCK, "60'sChartSwing", "0E", "01"),
    CHART_GUITAR_POP(StyleCategory.POP_AND_ROCK, "ChartGuitarPop", "16", "1E"),
    SEVENTIES_8BEAT(StyleCategory.POP_AND_ROCK, "70's8Beat", "16", "06"),
    SIXTIES_8Beat(StyleCategory.POP_AND_ROCK, "60's8Beat", "16", "00"),
    SIXTIES_GUITAR_POP(StyleCategory.POP_AND_ROCK, "60'sGuitarPop", "16", "05"),
    BUBBLEGUM_POP(StyleCategory.POP_AND_ROCK, "BubblegumPop", "16", "07"),
    NINETIES_GUITAR_POP(StyleCategory.POP_AND_ROCK, "90'sGuitarPop", "20", "02"),
    SOUTHERN_ROCK(StyleCategory.POP_AND_ROCK, "SouthernRock", "16", "48"),
    CARIBBEAN_ROCK(StyleCategory.POP_AND_ROCK, "CaribbeanRock", "16", "4A"),
    UNPLUGGED_1(StyleCategory.POP_AND_ROCK, "Unplugged1", "16", "0A"),
    UNPLUGGED_2(StyleCategory.POP_AND_ROCK, "Unplugged2", "16", "0B"),
    SIXTIES_POP_ROCK(StyleCategory.POP_AND_ROCK, "60'sPopRock", "16", "45"),
    ROCK_SHUFFLE(StyleCategory.POP_AND_ROCK, "RockShuffle", "1E", "40"),
    EIGHT_BEAT_GTR_POP(StyleCategory.POP_AND_ROCK, "8BeatGtrPop", "16", "02"),
    CLASSIC_16BEAT(StyleCategory.POP_AND_ROCK, "Classic16Beat", "20", "2D"),
    JAZZ_POP(StyleCategory.POP_AND_ROCK, "JazzPop", "24", "03"),
    KOOL_SHUFFLE(StyleCategory.POP_AND_ROCK, "KoolShuffle", "24", "05"),
    POP_SHUFFLE(StyleCategory.POP_AND_ROCK, "PopShuffle", "24", "00"),
    FUSION_SHUFFLE(StyleCategory.POP_AND_ROCK, "FusionShuffle", "24", "02"),
    SCAND_POP_SHUFFLE(StyleCategory.POP_AND_ROCK, "ScandPopShuffle", "1A", "62"),
    J_POPHIT(StyleCategory.POP_AND_ROCK, "J-PopHit", "20", "09"),
    NOT_YET_SPECIFIED(StyleCategory.POP_AND_ROCK, "Not yet specified", "00", "00"),; // Is there a style with data "00
    // 00"? This could potentially lead to wrong interpretation!

    StyleCategory styleCategory;
    String stringRep;
    String hex1;
    String hex2;

    StyleName(StyleCategory styleCategory, String stringRep, String hex1, String hex2) {
        this.styleCategory = styleCategory;
        this.stringRep = stringRep;
        this.hex1 = hex1;
        this.hex2 = hex2;
    }

    public String getStringRep() {
        return stringRep;
    }

    public String getHex1() {
        return hex1;
    }

    public String getHex2() {
        return hex2;
    }

    public static StyleName getStyleName(String hex1, String hex2) {
        for (StyleName styleName : StyleName.values()) {
            if (styleName.getHex1().equals(hex1)) {
                if (styleName.getHex2().equals(hex2)) {
                    return styleName;
                }
            }
        }
        return NOT_YET_SPECIFIED;
    }

}
