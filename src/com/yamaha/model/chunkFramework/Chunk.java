package com.yamaha.model.chunkFramework;

/**
 * Chunk is the basic framework for the sole SpfF-chunk, the 9 BHd-chunks and several GPm-chunks.
 * It is used to store the data from the .RGT-file and to easily manipulate it.
 * Every chunk has a CHUNK_ID which represents the ASCII code used to separate the hexData
 * into these chunks, meaning "SpfF", "BHd" and "GPm".
 * <p> The CHUNK_ID is followed by the chunk type. Concerning the BHd-chunks, there are the sequence
 * type and the registration type. Concerning the GPm-chunks, there are a lot of different
 * types, e. g. several for the Style settings, others for the Voices and so on.
 * <p> The type of the chunk is followed by the number of data bytes following the "header" of each chunk.
 * (header: CHUNK_ID + type of chunk + number of data bytes; data: the actual data of the chunk)
 * @author Dominic Plein
 * @version 1.0
 * @see SpfF, BHd, GPm
 */
public abstract class Chunk {

    protected int numberOfDataBytes;
    protected String hexType;
    protected String hexData;

    /**
     * Sets the number of bytes following the header of this chunk.
     * @param numberOfDataBytes the number of bytes following the header of this chunk
     */
    public void setNumberOfDataBytes(int numberOfDataBytes) {
        this.numberOfDataBytes = numberOfDataBytes;
    }

    /**
     * Returns the number of bytes following the header of this chunk.
     * @return the number of bytes following the header of this chunk
     */
    public int getNumberOfDataBytes() {
        return this.hexData.length() / 2;
    }

    public String toHexNumberOfDataBytesString() {
        int numberOfDataBytes = this.numberOfDataBytes;
        if (numberOfDataBytes < 16) {
            return "000" + Integer.toHexString(numberOfDataBytes);
        } else if (numberOfDataBytes < 256) {
            return "00" + Integer.toHexString(numberOfDataBytes);
        } else if (numberOfDataBytes < 4096) {
            return "0" + Integer.toHexString(numberOfDataBytes);
        } else {
            return Integer.toHexString(numberOfDataBytes);
        }
    }

    /**
     * Sets the type of this chunk represented in a hex value.
     * @param hexType the type of this chunk (hex value)
     */
    public void setHexType(String hexType) {
        this.hexType = hexType;
    }

    /**
     * Returns the type of this chunk represented in a hex value.
     * @return the type of this chunk (hex value)
     */
    public String getHexType() {
        return hexType;
    }

    /**
     * Sets the data to be stored in this chunk as a hex code
     * @param hexData the data to be stored in this chunk (hex code)
     */
    public void setHexData(String hexData) {
        this.hexData = hexData;
    }

    public void changeHexData(int positionOfDataByte, String hexData) {
        int beginIndex = (positionOfDataByte - 1) * 2;
        StringBuffer buf = new StringBuffer(this.hexData);
        buf.replace(beginIndex, beginIndex + 2, hexData);
        this.hexData = buf.toString();
    }

    /**
     * Gets the data stored in this chunk as a hex code.
     * @return the data stored in this chunk (hex code)
     */
    public String getHexData() {
        return hexData;
    }

    /**
     * Gets the data of a data byte on a certain position stored in this chunk as a hex value.
     * @param positionOfDataByte the position of the data byte in the header of this chunk
     * @return the byte of the given data byte position (hex value)
     */
    public String getHexData(int positionOfDataByte) {
        int beginIndex = (positionOfDataByte - 1) * 2;
        return hexData.substring(beginIndex, beginIndex + 2);
    }

    // Console Output
    @Override
    public String toString() { // see Assembler.java
        return "Chunk\t" + "Type: " + hexData + "\tNumberOfDataBytes: " + numberOfDataBytes
                + "\tData: " + hexData;
    }

    /**
     * Returns the hex code representation of this chunk, just like it is stored in the .RGT-file.
     * @return the hex code representation of this chunk
     */
    public abstract String toHexString();

}