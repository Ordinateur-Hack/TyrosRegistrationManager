package com.yamaha.model.editor;

import com.yamaha.model.Formatter;
import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.GPmType;
import javafx.beans.property.*;

import java.util.Arrays;
import java.util.List;

/**
 * A Style can manipulate many functionalities/properties of a style on the keyboard.
 * A style on the keyboard is an accompaniment for the player, so to speak his band,
 * whereby the player doesn't fade into the background. The style is just supporting
 * him with up to eight independent instruments which can be adjusted in many different
 * ways.
 * <p> The Style class extends from Editor in order to get the methods for editing the
 * basic controls on the keyboard.
 * @author Dominic Plein
 */
public class StyleEditor extends Editor {

    /**
     * A StyleFunction represents a functionality/property of a style, e. g. the volume,
     * which channels are activated etc. Used to simplify the access to the different
     * style properties without having to remember all the data byte positions.
     * @author Dominic Plein
     * @version 1.0
     */
    public enum StyleFunction {
        VOLUME(1),
        ACMP(3), // accompaniment
        STYLE_PART(4),
        FINGERING_TYPE(9),
        SYNC_START(11),
        SYNC_STOP(12),
        CHANNEL_COMBINATION(8), // the channel combination is stored in one byte unlike in the Song where
        // every channel has "its own byte"
        VOLUME_CHANNEL_CHANGE(12);

        // NOTE: All properties concerning style channels are set using the Channel enum and not this StyleFunction enum

        private int dataBytePosition;
        private StyleChannel channel;

        StyleFunction(int dataBytePosition) {
            this.dataBytePosition = dataBytePosition;
        }

        StyleFunction(StyleChannel channel, int dataBytePosition) {
            this.channel = channel;
            this.dataBytePosition = dataBytePosition;
        }

        /**
         * Returns the position of the data byte for a specific StyleFunction.
         * @return the position of the data byte for a specific StyleFunction
         */
        public int getDataBytePosition() {
            return dataBytePosition;
        }

        /**
         * Returns the StyleChannel of a specific StyleFunction.
         * @return the StyleChannel of a specific StyleFunction
         */
        public StyleChannel getChannel() {
            return channel;
        }
    }

    public enum StylePart {
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

        int representationNumber;
        StylePart(int representationNumber) {
            this.representationNumber = representationNumber;
        }

        public int getRepresentationNumber() {
            return representationNumber;
        }

        public static StylePart getStylePart(int representationNumber) {
            for (StylePart stylePart : StylePart.values()) {
                if (stylePart.getRepresentationNumber() == representationNumber)
                    return stylePart;
            }
            return null;
        }
    }

    // Standard Style 07
    private BooleanProperty isACMPEnabled;
    private ObjectProperty<StylePart> stylePart;
    private BooleanProperty isSyncStartEnabled;
    private BooleanProperty isSyncStopEnabled;

    // Style Attributes 08
    private IntegerProperty volumeStyle;

    private String channelCombination;
    private BooleanProperty isRHY1On;
    private BooleanProperty isRHY2On;
    private BooleanProperty isBASSOn;
    private BooleanProperty isCHD1On;
    private BooleanProperty isCHD2On;
    private BooleanProperty isPADOn;
    private BooleanProperty isPHR1On;
    private BooleanProperty isPHR2On;
    private List<BooleanProperty> areChannelsEnabled = Arrays.asList(isRHY1On, isRHY2On, isBASSOn, isCHD1On, isCHD2On, isPADOn, isPHR1On, isPHR2On);

    private IntegerProperty volumeRHY1;
    private IntegerProperty volumeRHY2;
    private IntegerProperty volumeBASS;
    private IntegerProperty volumeCHD1;
    private IntegerProperty volumeCHD2;
    private IntegerProperty volumePAD;
    private IntegerProperty volumePHR1;
    private IntegerProperty volumePHR2;
    private List<IntegerProperty> volumeList = Arrays.asList(volumeStyle, volumeRHY1, volumeRHY2, volumeBASS, volumeCHD1, volumeCHD2, volumePAD, volumePHR1, volumePHR2);

    private ObjectProperty<FingeringType> fingeringType;


    public StyleEditor(BHd bhdChunk) {
        super(bhdChunk);
        // exception handling for wrong registrationNumber
        // first: check whether there are any style information on the current registration button
        // initProperties();
    }

    @Override
    public boolean isRepresented() {
        return getGPmChunk(GPmType.STANDARD_STYLE) != null /*&& getGPmChunk(GPmType.STYLE_ATTRIBUTES) != null*/;
    }

    public void initProperties() {
        initVolumeStyleProperty();
        initIsACMPEnabledProperty();
        initStylePartProperty();
        initFingeringTypeProperty();
        initIsSyncStartEnabledProperty();
        initIsSyncStopEnabledProperty();;
        for (StyleChannel styleChannel : StyleChannel.values()) {
            initIsChannelEnabledProperty(styleChannel);
            initVolumeProperty(styleChannel);
        }
    }

    public void transferProperties() {
        transferVolumeStyleProperty();
        transferIsACMPEnabledProperty();
        transferStylePartProperty();
        transferFingeringTypeProperty();
        transferIsSyncStartEnabledProperty();
        transferIsSyncStopEnabledProperty();
        for (StyleChannel styleChannel : StyleChannel.values()) {
            transferVolumeProperty(styleChannel);
            transferIsChannelEnabledProperty(styleChannel);
        }
    }

    // ================================================================================================================= //

    // ||||||||||||||||||
    // ||||   ACMP   ||||
    // ||||||||||||||||||

    /**
     * Initializes the property isACMPActive by searching in the chunk's structure.
     */
    public void initIsACMPEnabledProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        boolean isActive = isEnabled(gpmChunk.getHexData(StyleFunction.ACMP.getDataBytePosition()));
        setACMPEnabled(isActive);
    }

    /**
     * Returns if ACMP is enabled.
     * @return true if ACMP is enabled
     */
    public final boolean isACMPEnabled() {
        if (isACMPEnabled != null)
            return isACMPEnabled.get();
        return false;
    }

    /**
     * Sets the property isACMPActive to enabled or disabled.
     * @param isACMPEnabled true if ACMP is enabled
     */
    public final void setACMPEnabled(boolean isACMPEnabled) {
        isACMPEnabledProperty().set(isACMPEnabled);
    }

    /**
     * Returns the property isACMPActive.
     * @return the property isACMPActive
     */
    public final BooleanProperty isACMPEnabledProperty() {
        if (isACMPEnabled == null)
            isACMPEnabled = new SimpleBooleanProperty(false);
        return isACMPEnabled;
    }

    /**
     * Transfers the property isACMPActive to the chunk's structure.
     */
    public void transferIsACMPEnabledProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        setEnabled(gpmChunk, StyleFunction.ACMP.getDataBytePosition(), isACMPEnabled());
    }


    // |||||||||||||||||||||||
    // ||||   StylePart   ||||
    // |||||||||||||||||||||||
    public void initStylePartProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        int representationNumber = Integer.parseInt(gpmChunk.getHexData(StyleFunction.STYLE_PART.getDataBytePosition()), 16);
        setStylePart(StylePart.getStylePart(representationNumber));
    }

    public final StylePart getStylePart() {
        if (stylePart != null)
            return stylePart.get();
        return null;
    }

    public final void setStylePart(StylePart stylePart) {
        stylePartProperty().set(stylePart);
    }

    public final ObjectProperty<StylePart> stylePartProperty() {
        if (stylePart == null)
            stylePart = new SimpleObjectProperty<>(StylePart.MAIN_A);
        return stylePart;
    }

    public void transferStylePartProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        gpmChunk.changeHexDataByte(StyleFunction.STYLE_PART.getDataBytePosition(), Formatter.formatIntToHex(getStylePart().getRepresentationNumber(), 1));
    }



    // ||||||||||||||||||||||||||||
    // ||||   Fingering Type   ||||
    // ||||||||||||||||||||||||||||

    /**
     * Initializes the property fingeringType by searching in the chunk's structure.
     */
    public void initFingeringTypeProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        String hexFingeringType = gpmChunk.getHexData(StyleFunction.FINGERING_TYPE.getDataBytePosition());
        setFingeringType(FingeringType.getFingeringType(Integer.parseInt(hexFingeringType, 16)));
    }

    /**
     * Returns the FingeringType of the ACMP.
     * @return the FingeringType of the ACMP
     */
    public final FingeringType getFingeringType() {
        if (fingeringType != null)
            return fingeringType.get();
        return null;
    }

    /**
     * Sets the property fingeringType of the ACMP.
     * @param fingeringType the FingeringType of the ACMP
     */
    public void setFingeringType(FingeringType fingeringType) {
        fingeringTypeProperty().set(fingeringType);
    }

    /**
     * Returns the property fingeringType.
     * @return the property fingeringType
     */
    public final ObjectProperty<FingeringType> fingeringTypeProperty() {
        if (fingeringType == null)
            fingeringType = new SimpleObjectProperty<>(FingeringType.FINGERED);
        return fingeringType;
    }

    /**
     * Transfers the property fingeringType to the chunk's structure.
     */
    public void transferFingeringTypeProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        String hexFingeringType = Formatter.formatIntToHex(getFingeringType().getRepresentationNumber(), 1);
        gpmChunk.changeHexDataByte(StyleFunction.FINGERING_TYPE.getDataBytePosition(), hexFingeringType);
    }


    // ||||||||||||||||||||||||
    // ||||   Sync Start   ||||
    // ||||||||||||||||||||||||

    public void initIsSyncStartEnabledProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        boolean isEnabled = isEnabled(gpmChunk.getHexData(StyleFunction.SYNC_START.getDataBytePosition()));
        setSyncStartEnabled(isEnabled);
    }

    public boolean isSyncStartEnabled() {
        if (isSyncStartEnabled != null)
            return isSyncStartEnabled.get();
        return false;
    }

    public void setSyncStartEnabled(boolean isSyncStartEnabled) {
        isSyncStartEnabledProperty().set(isSyncStartEnabled);
    }

    public final BooleanProperty isSyncStartEnabledProperty() {
        if (isSyncStartEnabled == null)
            isSyncStartEnabled = new SimpleBooleanProperty(false);
        return isSyncStartEnabled;
    }

    public void transferIsSyncStartEnabledProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        setEnabled(gpmChunk, StyleFunction.SYNC_START.getDataBytePosition(), isSyncStartEnabled());
    }


    // |||||||||||||||||||||||
    // ||||   Sync Stop   ||||
    // |||||||||||||||||||||||

    public void initIsSyncStopEnabledProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        boolean isEnabled = isEnabled(gpmChunk.getHexData(StyleFunction.SYNC_STOP.getDataBytePosition()));
        setSyncStopEnabled(isEnabled);
    }

    public boolean isSyncStopEnabled() {
        if (isSyncStopEnabled != null)
            return isSyncStopEnabled.get();
        return false;
    }

    public void setSyncStopEnabled(boolean isSyncStopEnabled) {
        // modify that this property isSyncStopActive can only be changed if ACMP is activated
        // else: fault message for the user
        isSyncStopEnabledProperty().set(isSyncStopEnabled);
    }

    public final BooleanProperty isSyncStopEnabledProperty() {
        if (isSyncStopEnabled == null)
            isSyncStopEnabled = new SimpleBooleanProperty(false);
        return isSyncStopEnabled;
    }

    public void transferIsSyncStopEnabledProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STANDARD_STYLE);
        setEnabled(gpmChunk, StyleFunction.SYNC_STOP.getDataBytePosition(), isSyncStopEnabled());
    }


    // |||||||||||||||||||||||||||||
    // ||||   Channels On/Off   ||||
    // |||||||||||||||||||||||||||||

    /**
     * Initializes the state (active or disabled) of the given StyleChannel by searching in the file.
     * @param styleChannel the channel to be examined
     */
    public void initIsChannelEnabledProperty(StyleChannel styleChannel) {
        /*
         * The StyleChannel with the channel number is active if the channelNumber
         * appears in the progression which can be described as following:
         * 1. Take the value of the power 2^(channelNumber - 1) as a starting point
         * Do the following two steps until you reach the styleChannelNumber (highest number is 255):
         * 2. Add (2^(channelNumber - 1) - 1) times the number 1 to the start number.
         * 3. Add (2^(channelNumber - 1) + 1) to the number.
         *
         * Example for the progression based on channel 2: 2, 3 / 6, 7 / 10, 11 / 14, 15 / ...
         * Example for the progression channel 3: 4, 5, 6, 7 / 12, 13, 14, 15 / 20, 21, 22, 23 / ...
         */
        GPm gpmChunk = getGPmChunk(GPmType.STYLE_ATTRIBUTES);
        channelCombination = gpmChunk.getHexData(StyleFunction.CHANNEL_COMBINATION.getDataBytePosition()); // the channel combination number (hex value)
        int channelCombinationNumber = Integer.parseInt(channelCombination, 16); // the channel combination number (as an Integer)
        int channelNumber = styleChannel.getChannelNumber();
        int power = (int) Math.pow(2, channelNumber - 1);

        setChannelEnabled(styleChannel, false);
        for (int testNumber = power; testNumber <= channelCombinationNumber; testNumber += power + 1) {
            if (testNumber == channelCombinationNumber)
                setChannelEnabled(styleChannel, true);
            for (int i = 0; i < power - 1; i++) {
                testNumber++;
                if (testNumber == channelCombinationNumber) {
                    setChannelEnabled(styleChannel, true);
                }
            }
        }
        // no need to upload the channelCombinationNumber because no channels were disabled or activated
    }

    public final boolean isChannelEnabled(StyleChannel styleChannel) {
        BooleanProperty isChannelEnabled = areChannelsEnabled.get(styleChannel.getChannelNumber() - 1);
        if (isChannelEnabled != null)
            return isChannelEnabled.get();
        return false;
    }

    public final void setChannelEnabled(StyleChannel styleChannel, boolean isChannelEnabled) {
        if (isChannelEnabled(styleChannel)) { // if channel is active
            if (!isChannelEnabled) // change the state only if user wants to change it to disabled
                isChannelEnabledProperty(styleChannel).set(false);
        }
        else { // if channel is disabled
            if (isChannelEnabled) { // change the state only, if user wants to change it to enabled
                isChannelEnabledProperty(styleChannel).set(true);
            }
        }
    }

    public final BooleanProperty isChannelEnabledProperty(StyleChannel styleChannel) {
        if (areChannelsEnabled.get(styleChannel.getChannelNumber() - 1) == null)
            areChannelsEnabled.set(styleChannel.getChannelNumber() - 1, new SimpleBooleanProperty(false));
        return areChannelsEnabled.get(styleChannel.getChannelNumber() - 1);
    }

    /**
     * Sets the channel of this style to active or disabled according to the previously set property.
     * <p>In contrast to setChannelActive, this method only changes the hex code but not the
     * attributes of the Style Editor.
     * @param styleChannel the channel to be manipulated
     */
    public void transferIsChannelEnabledProperty(StyleChannel styleChannel) {
        /*
         * The StyleChannel with the channel number can be activated or disabled
         * using the following approach:
         * 1. Take the current channelCombinationNumber.
         * 2. If channel is activated and should be disabled: subtract 2^(channelNumber - 1)
         *    If channel is disabled and should be activated: add 2^(channelNumber - 1)
         *
         * Example for the approach based on channel 3 and channelCombinationNumber 0C.
         * In this channelCombinationNumber 0C channel 3 is activated. User wants to disable it, so
         * subtract 2^(3 - 1) = 4. 0C means 12 as an integer. 12 - 4 gives 8, so the new
         * channelCombinationNumber would be 08.
         */
        GPm gpmChunk = getGPmChunk(GPmType.STYLE_ATTRIBUTES);
        channelCombination = gpmChunk.getHexData(StyleFunction.CHANNEL_COMBINATION.getDataBytePosition()); // the channel combination number (hex value)
        int channelCombinationNumber = Integer.parseInt(channelCombination, 16); // the channel combination number (as an Integer)
        int channelNumber = styleChannel.getChannelNumber();
        int power = (int) Math.pow(2, channelNumber - 1);

        boolean isChannelActive = isChannelEnabled(styleChannel);
        this.initIsChannelEnabledProperty(styleChannel); // unbind before doing this!
        boolean wasChannelActive = isChannelEnabled(styleChannel);

        if (wasChannelActive && !isChannelActive) {
            channelCombinationNumber -= power;
        }
        else if (!wasChannelActive && isChannelActive) {
            channelCombinationNumber += power;
        }

//		int channelCombinationNumber = 0;// set all channels inactive
//		if (areChannelsActive.get(channelNumber - 1))
//			channelCombinationNumber += power; // then check if the current channel should be active and add power; if it is disabled, nothing else has to be done

        // upload channelCombination (hex value representation)
        channelCombination = Formatter.formatIntToHex(channelCombinationNumber, 1);
        // upload the data of the gpmChunk
        gpmChunk.changeHexDataByte(StyleFunction.CHANNEL_COMBINATION.getDataBytePosition(), channelCombination);
        setChannelEnabled(styleChannel, isChannelActive);
    }



    // ||||||||||||||||||||||||||||
    // ||||   Volume General   ||||
    // ||||||||||||||||||||||||||||

    public void initVolumeStyleProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.STYLE_ATTRIBUTES);
        int volumeStyle = getValueSlideControl(gpmChunk.getHexData(StyleFunction.VOLUME.getDataBytePosition()));
        setVolumeStyle(volumeStyle);
    }

    public final int getVolumeStyle() {
        if (volumeList.get(0) != null)
            return volumeList.get(0).get();
        return 0;
    }

    public final void setVolumeStyle(int volumeStyle) {
        volumeStyleProperty().set(volumeStyle);
    }

    public final IntegerProperty volumeStyleProperty() {
        if (volumeList.get(0) == null)
            volumeList.set(0, new SimpleIntegerProperty(0));
        return volumeList.get(0);
    }

    public void transferVolumeStyleProperty() {
        // exception handling if volume < 0 || volume > 127
        GPm gpmChunk = getGPmChunk(GPmType.STYLE_ATTRIBUTES);
        setValueSlideControl(gpmChunk, StyleFunction.VOLUME.getDataBytePosition(), getVolumeStyle());
    }


    // |||||||||||||||||||||||||||||
    // ||||   Volume Channels   ||||
    // |||||||||||||||||||||||||||||

    public void initVolumeProperty(StyleChannel styleChannel) {
        GPm gpmChunk = getGPmChunk(GPmType.STYLE_ATTRIBUTES);
        int volume = getValueSlideControl(gpmChunk.getHexData(styleChannel.getChannelNumber() + 12 /* position of data byte for the first channel is 13 */));
        setVolume(styleChannel, volume);
    }

    public final int getVolume(StyleChannel styleChannel) {
        if (volumeList.get(styleChannel.getChannelNumber()) != null)
            return volumeList.get(styleChannel.getChannelNumber()).get(); // volume for channel 1 is at index 1
        return 0;
    }

    public final void setVolume(StyleChannel styleChannel, int volume) {
        volumeProperty(styleChannel).set(volume);
    }

    public final IntegerProperty volumeProperty(StyleChannel styleChannel) {
        if (volumeList.get(styleChannel.getChannelNumber()) == null)
            volumeList.set(styleChannel.getChannelNumber(), new SimpleIntegerProperty(0));
        return volumeList.get(styleChannel.getChannelNumber());
    }

    public void transferVolumeProperty(StyleChannel styleChannel) {
        GPm gpmChunk = getGPmChunk(GPmType.STYLE_ATTRIBUTES);
        setValueSlideControl(gpmChunk, styleChannel.getChannelNumber() + 12 /* position of data byte for the first channel is 13 */,
                getVolume(styleChannel));

        // indicated volume change (change byte that indicates if the volume for a channel has changed)
        // change byte not fully examined!
        setChanged(gpmChunk, StyleFunction.VOLUME_CHANNEL_CHANGE.getDataBytePosition(), true);
    }

}