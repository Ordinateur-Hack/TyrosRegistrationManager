package com.yamaha.controller;

import com.jfoenix.controls.JFXTextField;
import com.yamaha.application.Main;
import com.yamaha.model.editor.TitleEditor;
import javafx.fxml.FXML;

public class HomeController extends EditorController {

    private TitleEditor titleEditor;

    @FXML
    private JFXTextField registrationButtonName;

    @FXML
    public void initialize() {
        registrationButtonName.textProperty().addListener(change ->
                titleEditor.setTitle(registrationButtonName.getText()));
    }

    public void updateUI() {
        titleEditor = Main.getFooterController().getCurrentPRG().getTitleEditor();
        registrationButtonName.setText(titleEditor.getTitle());
    }

}