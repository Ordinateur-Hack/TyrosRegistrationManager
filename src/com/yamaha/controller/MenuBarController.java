package com.yamaha.controller;

import com.jfoenix.controls.JFXButton;
import com.yamaha.application.Main;
import com.yamaha.model.FXUtil;
import com.yamaha.model.chunkFramework.SpfF;
import com.yamaha.model.editor.RMGroup;
import com.yamaha.model.editor.RegistrationProgram;
import com.yamaha.model.organization.FileHandler;
import com.yamaha.model.organization.Merger;
import com.yamaha.model.organization.RGTParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuBarController {

    private SpfF spffChunk; // the root element of the file, the topmost element of the logical hierarchy
    private File file;
    private String fileData;
    private RMGroup currentRMGroup;

    @FXML
    private Label fileName;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton saveButton;
    // Registration Memory Content Groups
    @FXML
    private JFXButton songButton;
    @FXML
    private JFXButton styleButton;
    @FXML
    private JFXButton voiceButton;
    @FXML
    private List<JFXButton> rmGroupButtons;
    private JFXButton currentButton;

    /**
     * Initializes the program by setting up the Registration Memory Content Group buttons.
     */
    @FXML
    public void initialize() {
        // Set Home button as starting point
        currentButton = homeButton;
        changeButtonActive(homeButton);

        rmGroupButtons = Arrays.asList(songButton, styleButton, voiceButton); // extend later!

        // Disable most buttons
        for (JFXButton groupButton : rmGroupButtons)
            groupButton.setDisable(true);
        saveButton.setDisable(true);
    }

    /**
     * Loads a new .RGT file.
     */
    @FXML
    public void loadFile() {
        // Set up the fileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load .RGT file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Registration Memory File (.RGT)", "*" +
                ".RGT"));

        // Show fileChooser and get chosen file
        File selectedFile = fileChooser.showOpenDialog(Main.getPrimaryStage());

        if (selectedFile == null) {
            NullPointerException e = new NullPointerException("User did not select a file.");
            e.printStackTrace();
            Alert alert = FXUtil.createAlert(Alert.AlertType.WARNING, 15, "You canceled the load file task. If you " +
                    "have " +
                    "already load one file, the program will continue working with this file.");
            alert.setTitle("File Warning");
            alert.setHeaderText("No File Selected");
            alert.showAndWait();
        }
        this.file = selectedFile;

        loadStructure();

        String fullFileName = selectedFile.getName();
        // remove file ending .RGT for the displayed file name
        fileName.setText(fullFileName.substring(0, fullFileName.length() - 4));
        for (JFXButton groupButton : rmGroupButtons)
            groupButton.setDisable(true);
        saveButton.setDisable(false); // from now on: save Button stays active all the time

    }

    @FXML
    public void saveFile() {
        if (spffChunk.getHexData() == null) {
            NullPointerException e = new NullPointerException();
            e.printStackTrace();
            Alert alert = FXUtil.createAlert(Alert.AlertType.ERROR, 15, "You need to load an existing file first in " +
                    "order " +
                    "to save a new file.");
            alert.setTitle("File Error");
            alert.setHeaderText("No File selected");
            alert.showAndWait();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save RGT File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Registration Memory File (.RGT)", "*" +
                ".RGT"));
        fileChooser.setInitialFileName("MyRegistrationMemory");
        File myFile = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (myFile == null) {
            NullPointerException e = new NullPointerException();
            e.printStackTrace();
            Alert alert = FXUtil.createAlert(Alert.AlertType.WARNING, 15, "You canceled the save file task.");
            alert.setTitle("File Warning");
            alert.setHeaderText("No File Selected");
            alert.showAndWait();
        }

        // Transfer the properties
//		StyleController styleController = getStyleController();
//		styleController.updateStyleUI(); // in order to avoid NullPointerException for styleEditor
//		styleController.unbindCriticalBindings();
        try {
            for (RegistrationProgram prg : Main.getFooterController().getPRGs()) {
                prg.transferEditorProperties();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error when transfer the properties from the editors to the chunk's structure.");
        }
//		styleController.rebindCriticalBindings();

        Merger a = new Merger(spffChunk);
        String newFileData = a.assemble();

        try {
            FileHandler.writeFile(myFile, newFileData);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the file:<br>
     * Extracts the hex code, parses it and sets up the PRG buttons.
     */
    private void loadStructure() {
        try {
            fileData = FileHandler.convertFileToHex(file);
        } catch (IOException e) {
            System.err.println("No file found by the system while trying to convert it to hex code.");
            e.printStackTrace();
            Alert alert = FXUtil.createAlert(Alert.AlertType.ERROR, 15, "The File could not be found by the system. " +
                    "Please try this again.");
            alert.showAndWait();
            alert.setOnCloseRequest(event -> loadFile());
        }
        try {
            spffChunk = RGTParser.parseFileData(fileData);
        } catch (Exception e) {
            System.err.println("Could not properly parse the file data.");
            e.printStackTrace();
        }
        try {
            Main.getFooterController().initPRG(spffChunk);
        } catch (Exception e) {
            System.err.println("Could not properly set up the PRG buttons.");
            e.printStackTrace();
        }
    }

    public JFXButton getRMGroupButton(RMGroup rmGroup) {
        switch (rmGroup) {
            case SONG:
                return songButton;
            case STYLE:
                return styleButton;
            case VOICE:
                return voiceButton;
            default:
                return null;
        }
    }

    public void loadRegistrationMemoryContentGroup(RMGroup rmGroup) {
        String rmPath = rmGroup.toString();
        rmPath = rmPath.charAt(0) + rmPath.substring(1).toLowerCase();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + rmPath + ".fxml"));
        AnchorPane loaderUI = null;
        try {
            loaderUI = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.getRoot().setCenter(loaderUI);
        ((EditorController) loader.getController()).updateUI();
//		changeButtonActive(rmGroupButtonToActivate);
        try {
            changeButtonActive(getNewButton(rmGroup));
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("Failed to get the button for the appropriate RMGroup. \n"
                    + "There is no button for this RMGroup.");
        }
        currentRMGroup = rmGroup;
    }

    private JFXButton getNewButton(RMGroup rmGroup) {
        switch (rmGroup) {
            case STYLE:
                return styleButton;
            case TITLE:
                return homeButton;
            default:
                return null;
        }
    }

    @FXML
    public void loadStyle() {
//		FXMLLoader styleLoader = new FXMLLoader(getClass().getResource("/view/Style.fxml"));
//		AnchorPane styleUI = null;
//		try {
//			styleUI = styleLoader.load();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Main.getRoot().setCenter(styleUI);
//		// initialize the data
//		((StyleController) styleLoader.getController()).updateUI();
//		changeButtonActive(styleButton);
//		currentRMGroup = RMGroup.STYLE;


        loadRegistrationMemoryContentGroup(RMGroup.STYLE);
    }

    public StyleController getStyleController() {
        FXMLLoader styleLoader = new FXMLLoader(getClass().getResource("/view/Style.fxml"));
        try {
            styleLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return styleLoader.getController();
    }

    @FXML
    public void loadVoice() {
        AnchorPane voice = null;
        try {
            voice = FXMLLoader.load(getClass().getResource("/view/Voice.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getRoot().setCenter(voice);
        // initialize
        changeButtonActive(voiceButton);
        currentRMGroup = RMGroup.VOICE;
    }

    @FXML
    public void loadHome() {
//		FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
//		AnchorPane home = null;
//		try {
//			home = homeLoader.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Main.getRoot().setCenter(home);
//		((HomeController) homeLoader.getController()).updateUI();
//		changeButtonActive(homeButton);
//		currentRMGroup = RMGroup.TITLE;
        loadRegistrationMemoryContentGroup(RMGroup.TITLE);
    }

    /**
     * Changes which button is focused/active.<br>
     * The button is given a special color.
     *
     * @param newButton the new active button
     */
    private void changeButtonActive(JFXButton newButton) {
        currentButton.setStyle("-fx-background-color: transparent");
        // set new Button
        currentButton = newButton;
        currentButton.setStyle("-fx-background-color: #053B82");
    }

    public RMGroup getCurrentRMGroup() {
        return currentRMGroup;
    }

}
