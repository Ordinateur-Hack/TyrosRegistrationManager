package com.yamaha.model.organization;

import java.io.*;

/**
 * A FileHandler can extract hex code from a given file
 * and write hex code to a predefined file.
 *
 * @author Dominic Plein
 * @version 1.0
 */
public class FileHandler {

    /**
     * Returns the hex code from a given file.
     * @param file the file whose data should be converted to hex code
     * @return the converted hex code
     * @throws IOException if the file cannot be found (if an I/O error occurs)
     */
    public static String convertFileToHex(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        StringBuilder sbHex = new StringBuilder();
        int read = 0;
        while ((read = in.read()) != -1) { // while end of file not yet reached
            sbHex.append(String.format("%02X", read)); // convert to hex value with "X" specifier
        }
        in.close();
        return sbHex.toString();
    }

    /**
     * @param file the file to which the hex code is written
     * @param hexData the hex code that is written to the file
     * @throws IOException if the file cannot be found (if an I/O error occurs)
     */
    public static void writeFile(File file, String hexData) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int beginIndex = 0;
        while(beginIndex <= hexData.length() - 2) { // while two characters before end of String not yet reached
            // get two hex characters and write them to the file via the outputStream
            String hexValue = hexData.substring(beginIndex, (beginIndex += 2));
            os.write(Integer.parseInt(hexValue, 16));
        }
        System.out.println("The File has been successfully saved!");
        os.close();
    }

}