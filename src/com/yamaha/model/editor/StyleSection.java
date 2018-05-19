package com.yamaha.model.editor;

public enum SpecialStyleSection {
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

    public static final SpecialStyleSection values[] = values();
    int representationNumber;

    SpecialStyleSection(int representationNumber) {
        this.representationNumber = representationNumber;
    }

    /**
     * @return the representationNumber of this SpecialStyleSection
     */
    public int getRepresentationNumber() {
        return representationNumber;
    }

    /**
     * @param representationNumber the representationNumber of this SpecialStyleSection
     * @return the styleSection associated with the given representationNumber
     */
    public static SpecialStyleSection getStyleSection(int representationNumber) {
        for (SpecialStyleSection styleSection : SpecialStyleSection.values()) {
            if (styleSection.getRepresentationNumber() == representationNumber)
                return styleSection;
        }
        return null;
    }
}
