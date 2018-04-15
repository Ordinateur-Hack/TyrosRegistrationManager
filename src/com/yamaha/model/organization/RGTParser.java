package com.yamaha.model.organization;

import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.SpfF;

import java.util.ArrayList;
import java.util.List;
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
    private static SpfF spffChunk; // the root element of the file, the topmost element of the logical hierarchy

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

            /* Considered improvement:
             * The following block of code is practically identically to the respective block of code in the
             * arrangeGPm() method. Thus, another method could eventually shorten the code.
             */
            // and fill this chunk up with data (--> hexType, numberOfDataBytes, hexData)
            int beginIndex = matcher.end();
            bhdChunk.setHexType(fileData.substring(beginIndex, (beginIndex += 2)));
            int numberOfDataBytes = Integer.parseInt(fileData.substring(beginIndex, (beginIndex += 4)), 16);
            bhdChunk.setNumberOfDataBytes(numberOfDataBytes);
            bhdChunk.setHexData(fileData.substring(beginIndex, beginIndex + numberOfDataBytes * 2 /* times 2 dealing with hex code */));
            bhdChunks.add(bhdChunk); // then add this chunk to the empty list, so that the hierarchy is ensured
            // (SpfF-chunk owns the BHd-chunks hierarchically
        }

        // Console Output
        System.out.println("=============== BHD Chunks ===============");
        for (BHd bhdChunk : bhdChunks)
            System.out.println(bhdChunk.toString() + "\n");
    }

    /**
     * Parses the GPm-chunks for each of the 8 BHd-chunks that contain GPm-chunks.
     * The first BHd-chunk doesn't contain any GPm chunk. It represents the registration sequence.
     */
    private static void parseGPm() {
        Pattern pattern = Pattern.compile(GPm.TYPE_ID);

        for (int i = 1; i <= 8; i++) { // 8 Registrations
            BHd bhdChunk = spffChunk.getBHdChunks().get(i);
            List<GPm> gpmChunks = bhdChunk.getGPmChunks(); // empty list from the current BHd-chunk that will be filled up with GPm-chunks

            String bhdChunkHexData = bhdChunk.getHexData();
            Matcher matcher = pattern.matcher(bhdChunkHexData); // the matcher will search in the hex data from the current BHd-chunk

            while (matcher.find()) { // while a new GPm-chunk can be found
                GPm gpmChunk = new GPm(); // create an empty GPm-chunk
                // and fill this chunk up with data (--> hexType, numberOfDataBytes, hexData)
                int beginIndex = matcher.end();
                gpmChunk.setHexType(bhdChunkHexData.substring(beginIndex, (beginIndex += 2)));
                int numberOfDataBytes = Integer.parseInt(bhdChunkHexData.substring(beginIndex, (beginIndex += 4)), 16);
                gpmChunk.setNumberOfDataBytes(numberOfDataBytes);
                gpmChunk.setHexData(bhdChunkHexData.substring(beginIndex, beginIndex + numberOfDataBytes * 2 /* times 2 dealing with hex code */));
                gpmChunks.add(gpmChunk); // then add this chunk to the empty list, so that the hierarchy is ensured
                // (each BHd-chunk owns several GPm-chunks except of the first (hierarchical, not in the java language!))

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
     * Parses the given hex code and sorts it in chunks. First it sorts the SpfF Chunk,
     * then the BHd-chunks and finally the GPm-chunks for every BHd-chunk except for the
     * first BHd-chunk (it represents the registration sequence, thus it contains no GPm-chunk).
     * These chunks are organized in the editor who can later change specific properties
     * (manipulate the organized hex code)
     * @param fileData the fileData to be parsed
     * @param spffChunk the editor which will have access to the organized chunks
     */
    public static void parseFileData(String fileData, SpfF spffChunk) {
        RGTParser.fileData = fileData;
        RGTParser.spffChunk = spffChunk;
        // the following order of method calls is very important to maintain the logical hierarchy!
        // (1 SpfF-chunk --> 9 BHd-chunks --> several GPm-chunks)
        parseSpfF();
        parseBHd();
        parseGPm();
    }

}