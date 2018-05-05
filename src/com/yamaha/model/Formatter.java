package com.yamaha.model;

public class Formatter {

    /**
     * Converts a given int value to a hex value and fills up preceding gaps with 0s according to the number of used
     * bytes.<br><br>
     * <em>Example:</em>
     * <ul>
     * <li>value = 15</li>
     * <li>numberOfUsedBytes = 2</li>
     * <li>--> "000F"</li>
     * </ul>
     *
     * @param value             the given int value to be converted (in the range 0 to 16^(numberOfUsedBytes * 2)
     * @param numberOfUsedBytes the number of used bytes to represent the int value
     * @return a hex String which represents the int value in the hex file
     */
    public static String formatIntToHex(int value, int numberOfUsedBytes) {
        // Exception handling
        if (value < 0) {
            throw new IllegalArgumentException("The negative integer value " + value + " cannot be converted!");
        }
        if (value > Math.pow(16, numberOfUsedBytes * 2) -1) {
            throw new IllegalArgumentException("The integer value " + value + " cannot be represented using " +
                    numberOfUsedBytes + " bytes as the number is too big!");
        }

//		if (value == Math.pow(16, numberOfUsedBytes * 2) - 1)
//			return Integer.toHexString(value);
        for (int i = 1; i < numberOfUsedBytes * 2; i++) {
            if (value < Math.pow(16, i)) {
                StringBuffer hexData = new StringBuffer();
                for (int j = 0; j < numberOfUsedBytes * 2 - i; j++) {
                    hexData.append("0");
                }
                hexData.append(Integer.toHexString(value));
                return hexData.toString();
            }
        }
        return Integer.toHexString(value);
    }

//    == Test ==
//    public static void main(String[] args) {
//        System.out.println(Formatter.formatIntToHex(5, 1));
//        System.out.println(Formatter.formatIntToHex(254, 1));
//        System.out.println(Formatter.formatIntToHex(65535, 2));
//    }
}