package com.yamaha.model.editor;

import com.yamaha.model.Converter;
import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.GPmType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TitleEditor extends Editor {

    StringProperty title;

    public TitleEditor(BHd bhdChunk) {
        super(bhdChunk);
    }

    @Override
    public boolean isRepresented() {
        return getGPmChunk(GPmType.REGISTRATION_NAME) != null;
    }

    @Override
    public void initProperties() {
        initTitleProperty();
    }

    @Override
    public void mergeProperties() {
        mergeTitleProperty();
    }


    // |||||||||||||||||||
    // ||||   Title   ||||
    // |||||||||||||||||||

    /**
     * Initializes the property title by searching in the chunk's structure.
     */
    public void initTitleProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.REGISTRATION_NAME);
        String title = Converter.hexToAscii(gpmChunk.getHexData());
        setTitle(title);
    }

    /**
     * @return the title of the PRG
     */
    public final String getTitle() {
        if (title != null)
            return title.get();
        return null;
    }

    /**
     * Sets the property title.
     * @param title the title of the PRG
     */
    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    /**
     * @return the property title
     */
    public final StringProperty titleProperty() {
        if (title == null)
            title = new SimpleStringProperty("InitialValue");
        return title;
    }

    /**
     * Merge the property title into the chunk's structure.
     */
    public void mergeTitleProperty() {
        GPm gpmChunk = getGPmChunk(GPmType.REGISTRATION_NAME);
        String hexTitle = Converter.asciitoHex(getTitle());
        gpmChunk.setNumberOfDataBytes(hexTitle.length() / 2);
        gpmChunk.setHexData(hexTitle);
    }

}
