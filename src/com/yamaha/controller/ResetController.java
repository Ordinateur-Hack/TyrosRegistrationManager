package com.yamaha.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.yamaha.application.Main;
import javafx.fxml.FXML;

public class ResetController {

    @FXML
    private JFXCheckBox styleCheck;
    @FXML
    private JFXCheckBox songCheck;
    @FXML
    private JFXCheckBox voiceCheck;


    @FXML
    public void initialize() {
        // only show check boxes if these categories are available in the current PRG!
    }

    @FXML
    public void reset() {
        if (styleCheck.isSelected())
            resetStyle();
        if (songCheck.isSelected())
            resetSong();
        if (voiceCheck.isSelected())
            resetVoice();
    }

    private void resetStyle() {
        // Main.getFooterController().getCurrentPRG().getStyle().init();
        Main.getMenuBarController().loadStyle();
    }

    private void resetSong() {

    }

    private void resetVoice() {

    }

}