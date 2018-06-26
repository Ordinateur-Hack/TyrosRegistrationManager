package com.yamaha.controller;

import com.jfoenix.controls.JFXButton;
import com.yamaha.application.Main;
import com.yamaha.model.chunkFramework.SpfF;
import com.yamaha.model.editor.RMGroup;
import com.yamaha.model.editor.RegistrationProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

import java.util.Arrays;
import java.util.List;

public class FooterController {

    @FXML
    private JFXButton resetButton;

    private RegistrationProgram[] prgs;
    private RegistrationProgram currentPRG;

    //<editor-fold desc="PRG-buttons">
    @FXML
    private ToggleButton prg1Button;
    @FXML
    private ToggleButton prg2Button;
    @FXML
    private ToggleButton prg3Button;
    @FXML
    private ToggleButton prg4Button;
    @FXML
    private ToggleButton prg5Button;
    @FXML
    private ToggleButton prg6Button;
    @FXML
    private ToggleButton prg7Button;
    @FXML
    private ToggleButton prg8Button;
    private List<ToggleButton> prgButtons;
    //</editor-fold>

    @FXML
    public void initialize() {
        prgButtons = Arrays.asList(prg1Button, prg2Button, prg3Button, prg4Button, prg5Button, prg6Button,
                prg7Button, prg8Button);
        for (ToggleButton prgButton : prgButtons)
            prgButton.setDisable(true);

        // initResetButton();
    }

    /**
     * Initializes the PRG-buttons by setting up the RegistrationPrograms.
     *
     * @param spffChunk the root element of the file structure
     */
    public void initPRGs(SpfF spffChunk) {
        initialize();

        prgs = new RegistrationProgram[8];
        for (int i = 1; i <= 8; i++) {
            prgs[i - 1] = new RegistrationProgram(spffChunk, i); // create new RegistrationPrograms for every PRG-button
            if (prgs[i - 1].hasData()) {
                prgButtons.get(i - 1).setDisable(false);
            } else {
                // although at the beginning all buttons are disabled, buttons which don't store any data need to be
                // disabled during runtime because they can be enabled when loading the second, third ... file.
                prgButtons.get(i - 1).setDisable(true);
            }
        }
        currentPRG = prgs[0]; // first PRG-button as starting point
    }

    @FXML
    public void changePRG(ActionEvent actionEvent) {
        currentPRG = prgs[prgButtons.indexOf(actionEvent.getSource())];
        initMenuBarControls();
    }

    /**
     * Initializes the menu bar controls every time the user clicks on a PRG-button.
     */
    private void initMenuBarControls() {
        MenuBarController menuBarController = Main.getMenuBarController();
        RMGroup currentRMGroup = menuBarController.getCurrentRMGroup(); // last RMGroup before changing the PRG

        if (currentPRG.hasRMGroup(currentRMGroup))
            menuBarController.loadRMGroup(currentRMGroup);
        else
            menuBarController.loadHome();

        // find another solution for this one here
        // menuBarController.getRMGroupButton(RMGroup.STYLE).setDisable(!currentPRG.hasRMGroup(RMGroup.STYLE));
        // later: for every RMGroup

        // check which RMGroups are represented in the PRG to be load
        // accordingly to this information, disable/enable the appropriate MenuBarControls

        /*for (RMGroup rmGroup : RMGroup.values()) {
            if (currentPRG.hasRMGroup(rmGroup))
                menuBarController.enableRMGroup(rmGroup, true);
            else
                menuBarController.enableRMGroup(rmGroup, false);
        }*/

        List<RMGroup> workingRMGroups = Arrays.asList(RMGroup.TITLE, RMGroup.STYLE);
        for (RMGroup rmGroup : workingRMGroups) {
            if (currentPRG.hasRMGroup(rmGroup)) {
                menuBarController.enableRMGroupButton(rmGroup, true);
            } else {
                menuBarController.enableRMGroupButton(rmGroup, false);
            }
        }
    }

    /**
     * @return the current RegistrationProgram
     */
    public RegistrationProgram getCurrentPRG() {
        return currentPRG;
    }

    public RegistrationProgram[] getPRGs() {
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
        resetButton.setOnMouseClicked(event -> {
//            if (event.isControlDown())
//                resetButton.setStyle("-fx-background-color: #c63f17");
        });
    }

}