package com.yamaha.model.editor;

/**
 * The section of the style on the keyboard. The different sections are used to control what the "background band"
 * plays. In other words, the instruments play different patterns when switching to another section. Normally, the
 * climax is reached with section MAIN_D. Furthermore, there are sections which play a special role, e. g. the
 * BREAK_FILL section that serves for a short break.
 * <br><br>The main StyleSections describe the four buttons of the so-called "Main Variation" on the keyboard:
 * <ul>
 * <li>MAIN_A</li>
 * <li>MAIN_B</li>
 * <li>MAIN_C</li>
 * <li>MAIN_D</li>
 * </ul>
 *
 * <br>The special StyleSections describe the other seven "special" sections of the style:
 * <ul>
 * <li>INTRO_1</li>
 * <li>INTRO_2</li>
 * <li>INTRO_3</li>
 * <li>BREAK_FILL</li>
 * <li>ENDING_1</li>
 * <li>ENDING_2</li>
 * <li>ENDING_3</li>
 * </ul>
 *
 * <br>The special StyleSections also include
 * <ul>
 * <li>A_FILL</li>
 * <li>B_FILL</li>
 * <li>C_FILL</li>
 * <li>D_FILL</li>
 * </ul>
 * However, there is no button for these sections because they are indicated by flashing main StyleSection buttons.
 *
 * @see StyleEditor#mainStyleSectionProperty()
 * @see StyleEditor#specialStyleSectionProperty()
 */
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

    public boolean isFillIn() {
        return this == A_FILL || this == B_FILL || this == C_FILL || this == D_FILL;
    }

    public boolean isMainVariation() {
        return this == MAIN_A || this == MAIN_B || this == MAIN_C || this == MAIN_D;
    }

    public boolean isIntroOrEnding() {
        return this == INTRO_1 || this == INTRO_2 || this == INTRO_3 || this == ENDING_1 || this == ENDING_2
                || this == ENDING_3;
    }
}
