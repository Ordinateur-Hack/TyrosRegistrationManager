package com.yamaha.model.chunkFramework;

/**
 * The GPm-chunk is basically the "holder" for all the specific keyboard functions which
 * can be stored in the .RGT-file. In order to separate these functions (Song, Style, Voice, Harmony etc.)
 * there are different GPm-chunks. Note that often there are several GPm-chunks for one keyboard function
 * (e. g. GPm type 0B and 0D for Voice Right 1).<p>The "Registration Memory Contents"-groups that can be
 * set up on the keyboard determine which GPm-chunks appear in the data of the particular BHd-chunk.
 * This also means that there is no need for GPm-chunks to appear in the data of the BHd-chunk. It might
 * be that a specific GPm-chunk doesn't appear in the whole .RGT-file at all.
 * It is useful not to have every GPm-chunk in the file in order to maintain some settings in the keyboard
 * when switching to another registration button. For example, you could adjust the volume of your
 * microphone but not save it in the whole file. Thus, the volume stays the same all the time and you
 * don't need to worry to set the microphone volume up for every registration button.
 * @author Dominic Plein
 * @version 1.0
 */
public class GPm extends Chunk {

    public static final String CHUNK_ID = "47506D"; // hex code for "GPm" (ASCII coded)
    private String type = ""; // the hex type of this chunk in a more user-friendly representation using the enum GPmType
    // !!! type = "" initialization in order to prevent null pointer exception in method getGPmChunk(int registrationNumber, GPmType gpmType)

    @Override
    public void setHexType(String hexType) {
        super.setHexType(hexType);
        setType(hexType); // when setting up the hex type, set up the type (using the enum GPmType) of this chunk as well
    }

    /**
     * Sets the type of this chunk represented as a GPmType (enum)
     * @param gpmType the type of this chunk
     * @see GPmType
     */
    public void setType(GPmType gpmType) {
        this.type = gpmType.name();
    }

    /**
     * Sets the type of this chunk represented as a GPmType (enum)
     * @param hexType the hex type of this chunk
     */
    private void setType(String hexType) {
        for (GPmType type : GPmType.values()) {
            if (type.getHexType().equals(hexType))
                this.type = type.name();
        }
    }

    /**
     * Returns the type of this chunk represented as a GPmType (enum)
     * @return the GPmType of this chunk
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GPm Chunk\t" + "HexType: " + getHexType() + "\tType: " + getType() + "\tNumberOfDataBytes: " + getNumberOfDataBytes() + "\tData: "
                + getHexData();
    }

    public String toHexString() { // see Assembler.java
        return GPm.CHUNK_ID + getHexType() + toHexNumberOfDataBytesString() + getHexData();
    }

}