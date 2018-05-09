package com.yamaha.controller;

import com.jfoenix.controls.JFXButton;
import com.yamaha.application.Main;
import com.yamaha.model.chunkFramework.SpfF;
import com.yamaha.model.editor.RMGroup;
import com.yamaha.model.editor.RegistrationProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.List;

public class FooterController {

    @FXML
    private JFXButton resetButton;

    private RegistrationProgram[] prgs;
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
        prgButtons = Arrays.asList(prg1Button, prg2Button, prg3Button, prg4Button, prg5Button, prg6Button,
                prg7Button, prg8Button);
        for (JFXButton prgButton : prgButtons)
            prgButton.setDisable(true);

        // reset color
        for (JFXButton prgButton : prgButtons) {
            prgButton.setStyle("-fx-background-color: #29b6f6");
        }

        // initResetButton();
    }

    /**
     * Initializes the PRG-buttons by setting up the RegistrationPrograms.
     *
     * @param spffChunk the root element of the file structure
     */
    public void initPRG(SpfF spffChunk) {
        initialize();

        prgs = new RegistrationProgram[8];
        for (int i = 1; i <= 8; i++) {
            prgs[i-1] = new RegistrationProgram(spffChunk, i); // link every PRG-button to a RegistrationProgram
            if (prgs[i-1].hasData()) {
                prgButtons.get(i-1).setDisable(false);
            } else {
                // although at the beginning all buttons are disabled, buttons which don't store any data need to be
                // disabled during runtime because they can be enabled when loading the second, third ... file.
                prgButtons.get(i-1).setDisable(true);
            }
        }
        currentPRG = prgs[0]; // first PRG-button as starting point
    }

    @FXML
    public void changePRG(ActionEvent actionEvent) {
        // old PRG
        int prgIndex = currentPRG.getRegistrationNumber() - 1;
        prgButtons.get(prgIndex).setStyle("-fx-background-color:  #29b6f6"); // blue

        // new PRG
        prgIndex = prgButtons.indexOf(actionEvent.getSource());
        currentPRG = prgs[prgIndex];
        prgButtons.get(prgIndex).setStyle("-fx-background-color:  #ff7043"); // orange

        initMenuBarControls();
    }

    /**
     * The menu bar controls are initialized every time the user clicks on another PRG-button.
     */
    private void initMenuBarControls() {
        MenuBarController menuBarController = Main.getMenuBarController();
        RMGroup currentRMGroup = menuBarController.getCurrentRMGroup(); // last RMGroup before changing the PRG

        if (!currentPRG.hasRMGroup(currentRMGroup))
            menuBarController.loadHome();
        else
            menuBarController.loadRMGroup(currentRMGroup);

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

        if (currentPRG.hasRMGroup(RMGroup.TITLE))
            menuBarController.enableRMGroupButton(RMGroup.TITLE, true);
        else
            menuBarController.enableRMGroupButton(RMGroup.TITLE,false);

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
            if (event.isControlDown())
                resetButton.setStyle("-fx-background-color: #c63f17");
        });
    }

}