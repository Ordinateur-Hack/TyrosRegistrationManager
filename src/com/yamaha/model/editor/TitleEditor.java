package com.yamaha.model.editor;

import com.yamaha.model.Converter;
import com.yamaha.model.chunkFramework.BHd;
import com.yamaha.model.chunkFramework.GPm;
import com.yamaha.model.chunkFramework.GPmType;

public class TitleEditor extends Editor {

    String title;

    public TitleEditor(BHd bhdChunk) {
        super(bhdChunk);
    }

    @Override
    public boolean isRepresented() {
        return getGPmChunk(GPmType.REGISTRATION_NAME) != null;
    }

    @Override
    public void initProperties() {
        initTitle();
    }

    @Override
    public void transferProperties() {
        changeTitle();
    }


    // |||||||||||||||||||
    // ||||   Title   ||||
    // |||||||||||||||||||
    public void initTitle() {
        GPm gpmChunk = getGPmChunk(GPmType.REGISTRATION_NAME);
        String title = Converter.hexToAscii(gpmChunk.getHexData());
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void changeTitle() {
        GPm gpmChunk = getGPmChunk(GPmType.REGISTRATION_NAME);
        String hexTitle = Converter.asciitoHex(title);
        gpmChunk.setNumberOfDataBytes(hexTitle.length() / 2);
        gpmChunk.setHexData(hexTitle.toString());
    }

    public String getTitle() {
        return title;
    }

}
