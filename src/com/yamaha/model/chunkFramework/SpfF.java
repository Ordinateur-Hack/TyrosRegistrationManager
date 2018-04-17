package com.yamaha.model.chunkFramework;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpfF-chunk represents the header of every .RGT-file. Note that the SpfF chunk has no chunk type
 * because there is only one SpfF-chunk in every .RGT-file. Nevertheless, the number of data bytes is indicated
 * in this chunk (--> constant value: 00 10), followed by the data (which is partly constant as well:
 * 0C 12 52 47 53 54 00 02 00 02 xx xx xx xx 00 70; x is the hex value of bytes in the whole document including the
 * SpfF-chunk).
 * @author Dominic Plein
 * @version 1.0
 */
public class SpfF extends Chunk {

    public static final String CHUNK_ID = "53706646"; // hex code for "SpfF" (ASCII coded)
    private ArrayList<BHd> bhdChunks = new ArrayList<>(9); // hierarchy: a SpfF-chunk owns several BHd-chunks
    private String headerData;

    /**
     * Returns a list of subordinated BHd-chunks.
     * @return the subordinated BHD-chunks
     */
    public ArrayList<BHd> getBHdChunks() {
        return bhdChunks;
    }

    /**
     * Sets the header data of this chunk (the bytes following the type of this chunk and followed by the
     * "real" hex data (BHD-chunks etc.).
     * as a hex code.
     * @param headerData the header data of this chunk (hex code)
     */
    public void setSpfFHeaderData(String headerData) {
        this.headerData = headerData;
    }

    /**
     * Returns the header data of this chunk (the bytes following the type of this chunk and followed by the
     * "real" hex data (BHD-chunks etc.).
     * @return the header data of this chunk (hex code)
     */
    public String getSpfFHeaderData() {
        return headerData;
    }

    // Console output
    @Override
    public String toString() {
        return "SpfF Header Chunk\t" + "Number of data bytes: " + numberOfDataBytes + "\tHeader data: " + headerData + "\tData: " + hexData;
    }

    public String toHexString() {
        return SpfF.CHUNK_ID + "0010" + getSpfFHeaderData() + getHexData();
    }

}