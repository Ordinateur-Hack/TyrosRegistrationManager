package com.yamaha.model.chunkFramework;

/**
 * A GPmType represents the different types of a GPm-chunk.
 * This type is strongly related to the different keyboard functions, although
 * there might be several GPm-chunks for one keyboard function, e. g. "07" and "08"
 * (Standard Style and Style Attributes) are both related to the "style function".
 * @author Dominic Plein
 * @version 1.0
 */
public enum GPmType {

    // incomplete list, see many different types in my Tyros 3 specification
    // enum is extended when more functionalities are added to the program
    // currently in version 1.0: just very basic framework in order to see "how it works"
    REGISTRATION_NAME("01"),
    SONG("04"),
    STANDARD_STYLE("07"),
    STYLE_ATTRIBUTES("08"),
    VOICE_LEFT("13"),
    VOICE_RIGHT1("0B"),
    VOICE_RIGHT2("0F"),
    VOICE_RIGHT3("17");

    private String hexType; // hex representation of the GPmType

    GPmType(String hexType) {
        this.hexType = hexType;
    }

    /**
     * Returns the GPmType in a hex representation
     * @return the GPmType in a hex representation
     */
    public String getHexType() {
        return hexType;
    }

}