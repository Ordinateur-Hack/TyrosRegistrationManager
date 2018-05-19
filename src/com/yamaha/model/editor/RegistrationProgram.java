package com.yamaha.model.editor;

import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.SpfF;

import java.util.Arrays;
import java.util.List;

/**
 * A RegistrationProgram represents a registration button on the keyboard. It is used to
 * store the editors for the different Registration Memory Content Groups that can be set up
 * on the keyboard, e. g. style, voice, song etc.
 * <p>The FooterController has 8 RegistrationPrograms in order to represent the 8 registration buttons.
 * Thus, the RegistrationProgram allows to switch between these registration buttons and
 * to manipulate each setting individually.
 * <p>The RegistrationProgram provides hasX() methods, e g. hasStyle(), in order to check
 * if a specific Registration Memory Content Group has been checked when setting up the
 * registration button.
 * <p>NOTE: It's common that there are several empty registration buttons.
 * @author Dominic Plein
 */
public class RegistrationProgram {

    // change title? no, the GPm-chunk saves this information
    // change icon? no, the GPm-chunk saves this information
    // Registration Memory Content groups ...
    // has Registration Memory Content group
    // how to check if the registration button is empty or not? iterate through the BHd-chunks; simply check the length of the BHd-chunk

    private int registrationNumber; // the number of the registration button (in range 1 to 8)
    private BHd bhdChunk;
    private boolean isEmpty;

    // Editors
    private TitleEditor titleEditor;
    private StyleEditor styleEditor;
    // private VoiceEditor voice;
    // private SongEditor song;
    // ...
    private List<Editor> editors;


    // Song, Voice etc.

    public RegistrationProgram(SpfF spffChunk, int registrationNumber) {
        if (registrationNumber > 8 || registrationNumber < 1)
            throw new IllegalArgumentException("The registrationNumber has to be in range of 1 to 8");
        this.registrationNumber = registrationNumber;

        bhdChunk = spffChunk.getBHdChunks().get(registrationNumber); // index 0 is bhdChunk for sequence, so BHd-chunk for first button is at index 1
        isEmpty = bhdChunk.getNumberOfDataBytes() == 0; // check if the RegistrationProgram contains data

        if (!isEmpty) { // initialize the editors
            titleEditor = new TitleEditor(bhdChunk);
            styleEditor = new StyleEditor(bhdChunk);

            editors = Arrays.asList(titleEditor, styleEditor);
            // init properties for every editor
            for (Editor editor : editors) {
                if (editor.isRepresented())
                    editor.initProperties();
            }
        }
    }

    public void transferEditorProperties() {
        if (hasData()) {
            for (Editor editor: editors) {
                if (editor.isRepresented())
                    editor.mergeProperties();
            }
        }
    }

    /**
     * Returns if this RegistrationProgram has data, meaning if it is empty or not.
     * @return true if this RegistrationProgram has data, false if it is empty
     */
    public boolean hasData() {
        return !isEmpty;
    }

    public boolean hasRMGroup(RMGroup rmGroup) {
        switch (rmGroup) {
            case TITLE:
                return titleEditor.isRepresented();
            case SONG:
                // return song.isRepresented();
            case STYLE:
                return styleEditor.isRepresented();
            case VOICE:
                // return voice.isRepresented();
            default:
                return false; // if group button exists but is not handled here, rather return false than true in order to prevent a NullPointerException; pretend to the user that this group doesn't exist
        }

    }

    public StyleEditor getStyleEditor() {
        return styleEditor; // might return null! controller needs to check first
    }

    public TitleEditor getTitleEditor() {
        return titleEditor;
    }

/*    // Registration Memory Content Groups
    public boolean hasStyle() {
        return styleEditor.isRepresented();
    }*/

    /**
     * @return the registration number of this RegistrationProgram
     */
    public int getRegistrationNumber() {
        return registrationNumber;
    }

}