package com.yamaha.controller;

import com.jfoenix.controls.JFXTextField;
import com.yamaha.application.Main;
import com.yamaha.model.editor.TitleEditor;
import javafx.fxml.FXML;

public class HomeController extends EditorController {

    private TitleEditor titleEditor;

    @FXML
    private JFXTextField prgName;


    public void updateUI() {
        titleEditor = Main.getFooterController().getCurrentPRG().getTitleEditor();
        prgName.textProperty().bindBidirectional(titleEditor.titleProperty());
        addResetCtrlFunctionality(prgName, () -> titleEditor.initTitleProperty());
    }

}