package com.yamaha.model.chunkFramework;

import com.yamaha.model.Formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * The 8 BHd-chunks represent the registration buttons on the keyboard. However, the first
 * BHd-chunk contains the registration sequence data of the .RGT-file (useful when playing with
 * a foot pedal). Finally, there ought to be no more than 9 BHd-chunks in the whole .RGT-file.
 * Each BHd-chunk occurs only one time in the whole file.
 * @author Dominic Plein
 * @version 1.0
 */
public class BHd extends Chunk {

    public static final String CHUNK_ID = "424864"; // hex code for "BHd" (ASCII coded)
    private ArrayList<GPm> gpmChunks = new ArrayList<>(); // hierarchy: a BHd-chunk owns several GPm-chunks

    /**
     * Returns a list of subordinated GPm-chunks.
     * @return the subordinated GPm-chunks.
     */
    public ArrayList<GPm> getGPmChunks() {
        return gpmChunks;
    }

    @Override
    public String toString() {
        return "BHd Chunk\tType: " + hexType + "\tNumberOfDataBytes: " + numberOfDataBytes
                + "\tData: " + hexData;
    }

    public String toHexString() {
        return BHd.CHUNK_ID + getHexType() + Formatter.formatIntToHex(numberOfDataBytes, 2) + getHexData();
    }

}