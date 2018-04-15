package com.yamaha.model;

public class Formatter {

    public static String formatIntToHex(int value, int numberOfUsedBytes) {
        if (value < 0 || value > Math.pow(16, numberOfUsedBytes * 2) - 1 ) { // + das was mit den anderen darstellbar ist
            System.out.println("Exception handling");
            System.out.println("value ist " + value);
            return null;
        }
//		if (value == Math.pow(16, numberOfUsedBytes * 2) - 1)
//			return Integer.toHexString(value);
        for (int i = 1; i <= numberOfUsedBytes * 2 - 1; i++) {
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

//	public static void main(String[] args) {
//		System.out.println(Formatter.formatIntToHex(5, 1));
//		System.out.println(Formatter.formatIntToHex(254, 1));
//		System.out.println(Formatter.formatIntToHex(65535, 2));
//	}
}