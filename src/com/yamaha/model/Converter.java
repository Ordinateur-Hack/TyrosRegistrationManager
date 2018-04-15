package com.yamaha.model;

public class Converter {

    public static String asciitoHex(String asciiString) {
        // http://www.baeldung.com/java-convert-hex-to-ascii

        // Convert String to char array
        char[] chars = asciiString.toCharArray();

        // Cast each char to an int
        // Use Integer.toHexString() to convert it to Hex
        // Build up the new Hex-String using the StringBuilder
        StringBuilder hex = new StringBuilder();
        for (char ch : chars)
            hex.append(Integer.toHexString((int) ch));

        return hex.toString();
    }

// =================================================================================== //

    public static String hexToAscii(String hexString) {
        // http://www.baeldung.com/java-convert-hex-to-ascii

        // Cut the Hex value in 2 char groups
        // Convert it to base 16 Integer using Integer.parseInt(hex, 16) and cast to char
        // Append all chars in a String Builder
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < hexString.length(); i += 2) {
            String string = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(string, 16));
        }

        return output.toString();
    }

    // different approach
//	public static String hextoAscii(String hexString) {
//		// Cut the Hex value in 2 char groups
//		// Convert it to a base 16 byte using Byte.parseByte(hex, 16);
//		// Put all the bytes in a ByteBuffer
//		// Construct a new String using String(byte[] bytes) and the ASCII charset
//
//		ByteBuffer buffer = ByteBuffer.allocate(hexString.length());
//		for (int i = 0; i < hexString.length(); i += 2) {
//			String hex = hexString.substring(i, i + 2);
//			buffer.put(Byte.parseByte(hex, 16));
//		}
//		return new String(buffer.array(), StandardCharsets.US_ASCII);
//	}

}