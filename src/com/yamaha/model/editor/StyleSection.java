package com.yamaha.model.editor;

public enum StyleSection {
    // IMPORTANT: Do not change the order to this enum.
    INTRO_1(0),
    INTRO_2(1),
    INTRO_3(2),
    MAIN_A(8),
    MAIN_B(9),
    MAIN_C(10),
    MAIN_D(11),
    A_FILL(16),
    B_FILL(17),
    C_FILL(18),
    D_FILL(19),
    BREAK_FILL(24),
    ENDING_1(32),
    ENDING_2(33),
    ENDING_3(34);

    public static final StyleSection values[] = values();
    int representationNumber;

    StyleSection(int representationNumber) {
        this.representationNumber = representationNumber;
    }

    /**
     * @return the representationNumber of this StyleSection
     */
    public int getRepresentationNumber() {
        return representationNumber;
    }

    /**
     * @param representationNumber the representationNumber of this StyleSection
     * @return the styleSection associated with the given representationNumber
     */
    public static StyleSection getStyleSection(int representationNumber) {
        for (StyleSection styleSection : StyleSection.values()) {
            if (styleSection.getRepresentationNumber() == representationNumber)
                return styleSection;
        }
        return null;
    }
}
