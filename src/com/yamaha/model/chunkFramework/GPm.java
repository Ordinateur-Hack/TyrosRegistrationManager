package com.yamaha.model.chunkFramework;

import com.yamaha.model.Formatter;

/**
 * The GPm-chunk is basically the "holder" for all the specific keyboard functions which
 * can be stored in the .RGT-file. In order to separate these functions (Song, Style, Voice, Harmony etc.)
 * there are different GPm-chunks. Note that often there are several GPm-chunks for one keyboard function
 * (e. g. GPm type 0B and 0D for Voice Right 1).<p>The "Registration Memory Content"-groups can be
 * set up on the keyboard to determine which GPm-chunks appear in the data of the particular BHd-chunk.
 * This also means that there is no need for GPm-chunks to appear in the data of the BHd-chunk. It might
 * be that a specific GPm-chunk doesn't appear in the whole .RGT-file at all.
 * It is useful not to have every GPm-chunk in the file in order to maintain some settings in the keyboard
 * when switching to another registration button. For example, you could adjust the volume of your
 * microphone but not save it in the whole file. Thus, the volume stays the same all the time and you
 * don't need to set the volume up for every registration button.
 * @author Dominic Plein
 * @version 1.0
 */
public class GPm extends Chunk {

    public static final String CHUNK_ID = "47506D"; // hex code for "GPm" (ASCII coded)
    private GPmType gpmType = GPmType.NOT_YET_SPECIFIED; // the GPmType of this chunk (a more user-friendly
    // representation of the hexType using the enum GPmType

    @Override
    public void setHexType(String hexType) {
        super.setHexType(hexType);
        this.gpmType = GPmType.getGPmType(hexType); // when setting up the hex type, set up the type (using the enum
        // GPmType) of this chunk as well;
    }

    /**
     * @return the GPmType of this chunk
     */
    public GPmType getGPmType() {
        return gpmType;
    }

    @Override
    public String toString() {
        return String.format("GPmChunk    Type: %s %-25s NumberOfDataBytes: %-3s Data: %s", hexType, gpmType.toString(),
                numberOfDataBytes, hexData);
    }

    public String toHexString() { // see Merger.java
        return GPm.CHUNK_ID + hexType + Formatter.formatIntToHex(numberOfDataBytes, 2) + hexData;
    }

}