package com.yamaha.model.organization;

import com.yamaha.model.Formatter;
import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.SpfF;

public class Merger {

    SpfF spffChunk;

    public Merger(SpfF spffChunk) {
        this.spffChunk = spffChunk;
    }

//	public String assemble() {
//		List<BHd> bhdChunks = spffChunk.getBHdChunks();
//
//		StringBuffer fileDataBuffer = new StringBuffer(spffChunk.toHexString());
//		fileDataBuffer.append(bhdChunks.get(0).toHexString()); // BHd Sequence
//
//		updateData();
//		updateSpfFHeader(editor.getSpfFChunk(), editor.getSpfFChunk().getHexData());
//
//		fileDataBuffer.append("46456E640000"); // FileEnd..
//		return fileDataBuffer.toString();
//	}

    public String assemble() {
        updateData();
        updateSpfFHeader();
        return spffChunk.toHexString();
    }

    public void updateData() {
        StringBuffer spffHexData = new StringBuffer();

        BHd bhdSequence = spffChunk.getBHdChunks().get(0);
        spffHexData.append(bhdSequence.toHexString());

        for (int i = 1; i <= 8; i++) { // 8 Registrations
            StringBuffer bhdData = new StringBuffer();
            BHd bhdChunk = spffChunk.getBHdChunks().get(i);
            int dataByteCounter = 0;
            for (GPm gpmChunk : bhdChunk.getGPmChunks()) {
                bhdData.append(gpmChunk.toHexString());
                dataByteCounter += gpmChunk.getNumberOfDataBytes();
                dataByteCounter += 6; // number of bytes of a header of a GPm-Chunk
            }
            bhdChunk.setHexData(bhdData.toString());
            bhdChunk.setNumberOfDataBytes(dataByteCounter);
            spffHexData.append(bhdChunk.toHexString());
        }
        spffHexData.append("46456E640000"); // FileEnd..
        spffChunk.setHexData(spffHexData.toString());
    }

    public void updateSpfFHeader() {
        int numberOfBytesWholeFile = spffChunk.getHexData().length() / 2 + 22; // 22: constant number of bytes in the header
        // of the SpfF Chunk
        String HexNumberOfBytesWholeFile = Formatter.formatIntToHex(numberOfBytesWholeFile, 4);

        StringBuffer spffHeaderData = new StringBuffer(spffChunk.getSpfFHeaderData());
        spffHeaderData.replace(20, 28, HexNumberOfBytesWholeFile);
        spffChunk.setSpfFHeaderData(spffHeaderData.toString());
    }

    // noch den Header für die Anzahl der Bytes der gesamten Datei updaten!!!

    // public void updateSpfFData(SpfF spffChunk) {
    // StringBuffer headerData = new StringBuffer(spffChunk.getHeaderData())
    // spffChunk.setHeaderData();
    // }

}