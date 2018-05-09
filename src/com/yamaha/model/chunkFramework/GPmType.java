package com.yamaha.model.chunkFramework;

/**
 * A GPmType represents the different types of a GPm-chunk.
 * This type is strongly related to the different keyboard functions, although
 * there might be several GPm-chunks for one keyboard function, e. g. "07" and "08"
 * (Standard Style and Style Attributes) are both related to the "style function".
 *
 * @author Dominic Plein
 * @version 1.0
 */
public enum GPmType {

    // incomplete list, see many different types in my Tyros 3 specification
    // enum is extended when more functionalities are added to the program
    // currently: just very basic framework
    NOT_YET_SPECIFIED("00"),
    REGISTRATION_NAME("01"),
    SONG("04"),
    SONG_PATH("05"),
    STANDARD_STYLE("07"),
    STYLE_ATTRIBUTES("08"),
    VOICE_GLOABAL("0A"),
    VOICE_LEFT("13"),
    VOICE_RIGHT1("0B"),
    VOICE_RIGHT2("0F"),
    VOICE_RIGHT3("17"),
    VOICE_DIVERS_RIGHT1("0D"),
    VOICE_DIVERS_RIGHT2("11"),
    VOICE_DIVERS_RIGHT3("19"),
    BASIC_REVERB("1B"),
    BASIC_CHORUS("1C"),
    BASIC_DSP1TO9("1D"),
    MULTIPAD("24"),
    TEMPO("29");

    private String hexType; // hex representation of the GPmType

    GPmType(String hexType) {
        this.hexType = hexType;
    }

    /**
     * @return the GPmType in a hex representation
     */
    public String getHexType() {
        return hexType;
    }

    /**
     * @param hexType the GPmType in a hex representation
     * @return the GPmType corresponding to the hexType
     */
    public static GPmType getGPmType(String hexType) {
        for (GPmType gpmType : GPmType.values()) {
            if (gpmType.hexType.equals(hexType))
                return gpmType;
        }
        return NOT_YET_SPECIFIED;
    }

}