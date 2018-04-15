package com.yamaha.controller;

import com.jfoenix.controls.JFXButton;
import com.yamaha.model.chunkFramework.SpfF;
import com.yamaha.model.editor.RMGroup;
import com.yamaha.model.editor.RegistrationProgram;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FooterController {

    @FXML
    private JFXButton resetButton;

    private RegistrationProgram prg1;
    private RegistrationProgram prg2;
    private RegistrationProgram prg3;
    private RegistrationProgram prg4;
    private RegistrationProgram prg5;
    private RegistrationProgram prg6;
    private RegistrationProgram prg7;
    private RegistrationProgram prg8;
    private List<RegistrationProgram> prgs;
    private RegistrationProgram currentPRG;

    @FXML
    private JFXButton prg1Button;
    @FXML
    private JFXButton prg2Button;
    @FXML
    private JFXButton prg3Button;
    @FXML
    private JFXButton prg4Button;
    @FXML
    private JFXButton prg5Button;
    @FXML
    private JFXButton prg6Button;
    @FXML
    private JFXButton prg7Button;
    @FXML
    private JFXButton prg8Button;
    private static List<JFXButton> prgButtons;

    @FXML
    public void initialize() {
        prgButtons = Arrays.asList(prg1Button, prg2Button, prg3Button, prg4Button, prg5Button, prg6Button, prg7Button, prg8Button);
        for (JFXButton prgButton : prgButtons)
            prgButton.setDisable(true);

        initResetButton();
    }

    public void initPRG(SpfF spffChunk) {
        prg1 = new RegistrationProgram(spffChunk, 1);
        prg2 = new RegistrationProgram(spffChunk, 2);
        prg3 = new RegistrationProgram(spffChunk, 3);
        prg4 = new RegistrationProgram(spffChunk, 4);
        prg5 = new RegistrationProgram(spffChunk, 5);
        prg6 = new RegistrationProgram(spffChunk, 6);
        prg7 = new RegistrationProgram(spffChunk, 7);
        prg8 = new RegistrationProgram(spffChunk, 8);
        prgs = Arrays.asList(prg1, prg2, prg3, prg4, prg5, prg6, prg7, prg8);
        currentPRG = prg1;

        for (JFXButton prgButton : prgButtons) {
            prgButton.setStyle("-fx-background-color: #29b6f6");
            if (prgs.get(prgButtons.indexOf(prgButton)).hasData())
                prgButton.setDisable(false);
                // although at the beginning all buttons are disabled, buttons which don't store any data need to be disabled
                // during runtime because they can be enabled when loading the second, third ... file.
            else
                prgButton.setDisable(true);
        }
    }

    @FXML
    public void changePRG(ActionEvent actionEvent) {
        // old PRG
        int prgIndex = currentPRG.getRegistrationNumber() - 1;
        prgButtons.get(prgIndex).setStyle("-fx-background-color:  #29b6f6"); // blue

        // new PRG
        prgIndex = prgButtons.indexOf(actionEvent.getSource());
        currentPRG = prgs.get(prgIndex);
        prgButtons.get(prgIndex).setStyle("-fx-background-color:  #ff7043"); // orange

        initMenuBarControls();
    }

    private void initMenuBarControls() {
        MenuBarController menuBarController = com.yamaha.application.Main.getMenuBarController();
        RMGroup currentRMGroup = menuBarController.getCurrentRMGroup();

        if (!currentPRG.hasRMGroup(currentRMGroup))
            menuBarController.loadHome();
        else
            menuBarController.loadRegistrationMemoryContentGroup(currentRMGroup);

        menuBarController.getRMGroupButton(RMGroup.STYLE).setDisable(!currentPRG.hasRMGroup(RMGroup.STYLE)); // later: for every RMGroup
    }

    /**
     * Returns the current Registration Program.
     * @return the current Registration Program
     */
    public RegistrationProgram getCurrentPRG() {
        return currentPRG;
    }

    public List<RegistrationProgram> getPRGs() {
        return prgs;
    }

    /*
    @FXML
    public void reset() {
        try {
            com.yamaha.application.Main.showResetUI(FXMLLoader.load(getClass().getResource("/view/Reset.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public void initResetButton() {
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.isControlDown()) {
                    resetButton.setStyle("-fx-background-color: #c63f17");
                }
            }
        });
    }

}