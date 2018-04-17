package com.yamaha.model.organization;

import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.Chunk;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.SpfF;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The RGTParser parses the given fileData and sorts it in chunks which can be used
 * for editing via an Editor. The Parser looks for the typical structure of an
 * .RGT file: "SpfF", "BHd" and "GPm". These are the precedent ASCII character
 * strings of every chunk.
 * <p> NOTE: There is no "FileEnd" chunk. This ASCII character string that appears
 * at the end of every .RGT file is added later via the Merger.
 *
 * @author Dominic Plein
 * @version 1.0
 */
public class RGTParser {

    private static String fileData;
    private static SpfF spffChunk = new SpfF(); // the root element of the file, the topmost element of the logical
    // hierarchy

    /**
     * Parses the SpfF-chunk.
     */
    private static void parseSpfF() {
        spffChunk.setNumberOfDataBytes(16); // constant
        spffChunk.setSpfFHeaderData(fileData.substring(12, 44));
        spffChunk.setHexData(fileData.substring(44)); // encompasses the following "BHd" chunks and their "GPm" chunks

        // Console Output
        System.out.println("=============== SpfF Header Chunk ===============");
        System.out.println(spffChunk.toString() + "\n");
    }

    /**
     * Parses the BHd-chunks.
     * The first BHd-chunk represents the registration sequence.
     * The remaining 8 BHd-chunks represent the eight buttons of the registration bank.
     */
    private static void parseBHd() {
        Pattern pattern = Pattern.compile(BHd.CHUNK_ID);
        Matcher matcher = pattern.matcher(fileData);
        ArrayList<BHd> bhdChunks = spffChunk.getBHdChunks(); // empty list from the SpfF-chunk that will be filled up
        // with BHd-chunks

        while (matcher.find()) { // while a new BHd-chunk can be found
            BHd bhdChunk = new BHd(); // create an empty BHd-chunk
            extractData(fileData, bhdChunk, matcher); // fill up the chunk
            bhdChunks.add(bhdChunk); // then add this chunk to the empty list, so that the hierarchy is ensured
            // (SpfF-chunk owns the BHd-chunks hierarchically
        }

        // Console Output
        System.out.println("=============== BHD Chunks ===============");
        for (BHd bhdChunk : bhdChunks)
            System.out.println(bhdChunk.toString());
        System.out.println();
    }

    /**
     * Parses the GPm-chunks for each of the 8 BHd-chunks that contain GPm-chunks.<br>
     * The first BHd-chunk doesn't contain any GPm chunk. It represents the registration sequence.
     */
    private static void parseGPm() {
        Pattern pattern = Pattern.compile(GPm.CHUNK_ID);

        for (int i = 1; i <= 8; i++) { // 8 Registrations
            BHd bhdChunk = spffChunk.getBHdChunks().get(i);
            ArrayList<GPm> gpmChunks = bhdChunk.getGPmChunks(); // empty list from the current BHd-chunk that will be
            // filled up with GPm-chunks

            String bhdChunkHexData = bhdChunk.getHexData();
            Matcher matcher = pattern.matcher(bhdChunkHexData); // the matcher will search in the hex data from the
            // current BHd-chunk

            while (matcher.find()) { // while a new GPm-chunk can be found
                GPm gpmChunk = new GPm(); // create an empty GPm-chunk
                extractData(bhdChunkHexData, gpmChunk, matcher); // fill up the chunk
                gpmChunks.add(gpmChunk); // then add this chunk to the empty list, so that the hierarchy is ensured
                // (each BHd-chunk owns several GPm-chunks hierarchically except of the first)
            }
        }

        // Console Output
        System.out.println("=============== GPm Chunks ===============");
        for (int i = 1; i <= 8; i++) {
            BHd bhdChunk = spffChunk.getBHdChunks().get(i);
            System.out.println("REGISTRATION BUTTON (PRG) " + i);
            for (GPm gpmChunk : bhdChunk.getGPmChunks()) {
                System.out.println(gpmChunk.toString());
            }
            System.out.println();
        }
    }

    /**
     * Extracts the data from the given fileData and fills the given chunk up with data pieces according to a
     * given matcher:<br>
     *     hexType, numberOfDataBytes, hexData
     * @param fileData the hex data containing the necessary information
     * @param chunk the Chunk to fill up with the data
     * @param matcher the Matcher to search for the specified beginning of the information piece
     */
    private static void extractData(String fileData, Chunk chunk, Matcher matcher) {
        int beginIndex = matcher.end();
        chunk.setHexType(fileData.substring(beginIndex, (beginIndex += 2)));
        int numberOfDataBytes = Integer.parseInt(fileData.substring(beginIndex, (beginIndex += 4)), 16);
        chunk.setNumberOfDataBytes(numberOfDataBytes);
        chunk.setHexData(fileData.substring(beginIndex, beginIndex + numberOfDataBytes * 2 /* times 2 dealing with hex
        code */));
    }

    /**
     * Parses the given hex code and sorts it in chunks. First it sorts the SpfF Chunk,
     * then the BHd-chunks and finally the GPm-chunks for every BHd-chunk except for the
     * first BHd-chunk (it represents the registration sequence, thus it contains no GPm-chunks).<br>
     * @param fileData  the hex fileData to be parsed
     * @return the root element of the file, the topmost element of the logical hierarchy which has contains all of
     * the BHd and GPm-chunks
     */
    public static SpfF parseFileData(String fileData) {
        RGTParser.fileData = fileData;
        RGTParser.spffChunk = spffChunk;
        // the following order of method calls is very important to maintain the logical hierarchy!
        // (1 SpfF-chunk --> 9 BHd-chunks --> several GPm-chunks except for first BHd-chunks)
        parseSpfF();
        parseBHd();
        parseGPm();
        System.out.println("Parsed hex code successfully!");
        return spffChunk;
    }

}