package com.yamaha.model.editor;

/**
 * This enum represents the FingeringType for the ACMP (accompaniment of the style).
 */
public enum FingeringType {

    SINGLE_FINGER(1, "Single Finger"),
    MULTI_FINGER(2, "Multi Finger"),
    AI_FINGERED(3, "AI Fingered"),
    FINGERED(4, "Fingered"),
    FINGERED_ON_BASS(6, "Fingered On Bass"),
    FULL_KEYBOARD(7, "Full Keyboard"),
    AI_FULL_KEYBOARD(12, "AI Full Keyboard");

    private int representationNumber;
    private String labelText;

    private FingeringType(int representationNumber, String labelText) {
        this.representationNumber = representationNumber;
        this.labelText = labelText;
    }

    public int getRepresentationNumber() {
        return representationNumber;
    }

    public static FingeringType getFingeringType(int representationNumber) {
        for (FingeringType fingeringType : FingeringType.values()) {
            if (fingeringType.getRepresentationNumber() == representationNumber)
                return fingeringType;
        }
        return null;

//		switch (representationNumber) {
//		case 1:
//			return SINGLE_FINGER;
//		case 2:
//			return MULTI_FINGER;
//		case 3:
//			return AI_FINGERED;
//		case 4:
//			return FINGERED;
//		case 6:
//			return FINGERED_ON_BASS;
//		case 7:
//			return FULL_KEYBOARD;
//		case 12:
//			return AI_FULL_KEYBOARD;
//		}
//		return null;
    }

    @Override
    public String toString() {
        return labelText;
    }

}