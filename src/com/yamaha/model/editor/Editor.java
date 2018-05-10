package com.yamaha.model.editor;

import com.yamaha.model.Formatter;
import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.GPmType;

/**
 * The editor can manipulate the GPm-chunks in order to change the parameters
 * of the keyboard functionalities. It provides a method for searching a specific
 * GPm-chunk based on the GPmType. Further, the editor provides methods to edit basic controls,
 * such as a slide control, a spin control and a toggle button. <p>Do not mistake
 * this with the controls for the UI. The "basic controls" in the editor are used
 * by its subclasses of to easily manipulate values for controls on the keyboard
 * (e. g. on the keyboard there are several slide controls etc.).
 *
 * @author Dominic Plein
 */
public abstract class Editor {

    protected BHd bhdChunk;

    public Editor(BHd bhdChunk) {
        this.bhdChunk = bhdChunk;
        // DO NOT init the properties here, as this has to be done by the RegistrationProgram
        // after having checked if the editor is represented in the PRG!
    }

    /**
     * Searches in the BHd-chunk associated with this Editor and returns the GPm-chunk associated with the given
     * GPmType. This method might return null if there is no corresponding GPm-chunk in the BHd-chunk. However,
     * before initializing the properties in the Editor, the RegistrationProgram checks if this Editor is represented
     * in the current PRG.
     * @param gpmType the GPmType of the GPm-chunk searched for
     * @return the searched GPm-chunk (null if it does not exist)
     */
    public GPm getGPmChunk(GPmType gpmType) {
        GPm gpmChunk = null;
        for (GPm currentGPmChunk : bhdChunk.getGPmChunks()) {
            if (currentGPmChunk.getGPmType() == gpmType) {
                gpmChunk = currentGPmChunk;
            }
        }
        return gpmChunk;
    }

    /**
     * @return true if the Registration Memory Content Group linked to this editor is represented in the BHdChunk
     * @see RegistrationProgram
     */
    public abstract boolean isRepresented();

    /**
     * Initializes the properties of this Editor by searching in the chunk's structure.
     * This includes getting the hex data of the GPm-chunks and interpreting it.
     */
    public abstract void initProperties();

    /**
     * Merge the properties of this Editor into the actual chunk's structure.
     * This includes writing hex code in the GPm-chunks which are later used to
     * reconstruct the file in order to save it.
     */
    public abstract void mergeProperties();


    // GENERAL CONTROLS (used in subclasses of editor)

    // On/Off toggle button

    /**
     * Represents an on/off toggle button on the keyboard.
     * Sets the data of a specified GPm-chunk to "active" or "disabled", thus
     * it can activate or disable a keyboard function.
     *
     * @param gpmChunk           the GPm-chunk to be manipulated
     * @param positionOfDataByte the position of the data byte in the GPm-chunk which represents the on/off toggle
     *                           button
     * @param isActive           true if the functionality is activated, determines the state of the functionality
     *                           (on/off)
     */
    public void setEnabled(GPm gpmChunk, int positionOfDataByte, boolean isActive) {
        String hexData = isActive ? "7F" : "00";
        gpmChunk.changeHexDataByte(positionOfDataByte, hexData);
    }

    /**
     * Returns what the state of a keyboard functionality would be
     * if the data contained the given isActiveValue as a hex value.
     *
     * @param isActiveValue the hex value of the data byte which determines if a keyboard
     *                      functionality is activated or disabled.
     * @return true if, and only if the isActiveValue represents the "activated value" ("7F") for
     * keyboard functionalities with two states (on/off).
     */
    public boolean isEnabled(String isActiveValue) {
        switch (isActiveValue) {
            case "7F": // keyboard functionality: on
                return true;
            case "00": // keyboard functionality: off
                return false;
            default:
                return false;
        }
    }

    // Slide control

    /**
     * Represents a slide control on the keyboard.
     * Sets the value of this slide control.
     *
     * @param gpmChunk           the GPm-chunk to be manipulated
     * @param positionOfDataByte the position of the data byte in the GPm-chunk which represents a slide control
     * @param value              the value this slide control is set to (usually within the range of 0 to 127,
     *                           sometimes: 1 to 127)
     */
    public void setValueSlideControl(GPm gpmChunk, int positionOfDataByte, int value) {
        // exception handling if value < 0 || value > 127
        gpmChunk.changeHexDataByte(positionOfDataByte, Formatter.formatIntToHex(value, 1 /* one byte used */));
    }

    /**
     * Returns what the value of a slide control would be
     * if the data contained the given hex value.
     * (Basically this method just converts the hex value to
     * an Integer.)
     *
     * @param value the hex value of the data byte which determines
     *              the value of a slide control
     * @return what the value of a slide control would be with the given input hex value
     */
    public int getValueSlideControl(String value) {
        return Integer.parseInt(value, 16);
    }

    // Spin control

    /**
     * Represents a spin control on the keyboard.
     * Sets the value of this spin control.
     *
     * @param gpmChunk           the GPm-chunk to be manipulated
     * @param positionOfDataByte the position of the data byte in the GPm-chunk which represents a spin control
     * @param value              the value this spin control is set to (usually within the range of -64 to +63,
     *                           sometimes: -63 to +63)
     */
    public void setValueSpinControl(GPm gpmChunk, int positionOfDataByte, int value) {
        // exception handling if value < -64 || value > 63
        String hexValue = Formatter.formatIntToHex(value + 64, 1 /* one byte used */);
        gpmChunk.changeHexDataByte(positionOfDataByte, hexValue);
    }

    /**
     * Returns what the value of a spin control would be
     * if the data contained the given hex value.
     * (Basically this method just converts the hex value to
     * an Integer.)
     *
     * @param value the hex value of the data byte which determines
     *              the value of a spin control
     * @return what the value of a spin control would be with the given input hex value
     */
    public int getValueSpinControl(String value) {
        return Integer.parseInt(value, 16);
    }

    // "Change Byte" Indicator

    /**
     * Represents a "Change Byte". This byte of a GPm-chunk indicates whether a related property has changed.
     * <p>NOTE: This "Change Byte" hasn't been fully examined.
     *
     * @param gpmChunk           the GPm-chunk to be manipulated
     * @param positionOfDataByte the position of the data byte in the GPm-chunk which represents the "change byte"
     * @param hasChanged         true if the related property has changed
     */
    public void setChanged(GPm gpmChunk, int positionOfDataByte, boolean hasChanged) {
        String hexData = hasChanged ? "FF" : "00";
        gpmChunk.changeHexDataByte(positionOfDataByte, hexData);
    }

    /**
     * Returns if the given hex value of the "change byte" stands for a change or not.
     *
     * @param hasChanged the hex value of the "change byte"
     * @return true if the hex value represents a change
     */
    public boolean hasChanged(String hasChanged) {
        switch (hasChanged) {
            case "FF": // change
                return true;
            case "00": // no change
                return false;
            default:
                return false;
        }
    }


    // --> BHd sequence data will probably be manipulated directly via the BHd-chunk, no subordinate elements

}