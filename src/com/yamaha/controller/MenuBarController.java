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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MenuBarController {

    private SpfF spffChunk; // the root element of the file, the topmost element of the logical hierarchy
    private File file;
    private String fileData;
    private RMGroup currentRMGroup;

    @FXML
    private Label fileName;
    @FXML
    private ToggleButton homeButton;
    @FXML
    // Load button calls 'loadFile' on action
    private JFXButton saveButton;

    // Registration Memory Content Groups:
    @FXML
    private ToggleButton songButton;
    @FXML
    private ToggleButton styleButton;
    @FXML
    private ToggleButton voiceButton;
    @FXML
    private List<ToggleButton> rmGroupButtons;
    private ToggleGroup buttonsToggleGroup = new ToggleGroup();
    private HashMap<RMGroup, ToggleButton> rmGroupToggleButtonMap;

    /**
     * Initializes the program by setting up the Registration Memory Content Group buttons.
     */
    @FXML
    public void initialize() {
        rmGroupToggleButtonMap = new HashMap<>();
        rmGroupToggleButtonMap.put(RMGroup.TITLE, homeButton);
        rmGroupToggleButtonMap.put(RMGroup.SONG, songButton);
        rmGroupToggleButtonMap.put(RMGroup.STYLE, styleButton);
        rmGroupToggleButtonMap.put(RMGroup.VOICE, voiceButton);

        rmGroupButtons = Arrays.asList(songButton, styleButton, voiceButton); // extend later!
        buttonsToggleGroup = new ToggleGroup();
        buttonsToggleGroup.getToggles().addAll(rmGroupButtons);
        buttonsToggleGroup.getToggles().add(homeButton);
        FXUtil.addAlwaysOneSelectedSupport(buttonsToggleGroup);

        // Set Home button as starting point
        currentRMGroup = RMGroup.TITLE;

        // Disable most buttons
        for (ToggleButton rmGroupButton : rmGroupButtons)
            rmGroupButton.setDisable(true);
        homeButton.setDisable(true);
        saveButton.setDisable(true);

        Main.loadEmptyEditorsPane();
    }

    /**
     * Loads a new .RGT file.
     */
    @FXML
    public void loadFile() {
        initialize(); // manual initialization needed when second file is loaded
        System.out.println("<<< MenuBarController: Load a file ...");

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
            alert.setTitle("Warning");
            alert.setHeaderText("No File Selected");
            alert.showAndWait();
        }
        file = selectedFile;

        loadStructure();

        // Show File Name
        String fullFileName = selectedFile.getName();
        // remove file ending .RGT for the displayed file name
        fileName.setText(fullFileName.substring(0, fullFileName.length() - 4));
        for (ToggleButton rmGroupButton : rmGroupButtons)
            rmGroupButton.setDisable(true);
        saveButton.setDisable(false); // from now on: saveButton stays active all the time

        System.out.println("Loaded file successfully >>>");
        System.out.println();
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
            Alert alert = FXUtil.createAlert(Alert.AlertType.ERROR, 15, "The selected file could not properly be " +
                    "parsed. Please check if it is a valid .RGT-file.");
            alert.showAndWait();
            alert.setOnCloseRequest(event -> loadFile());
        }
        try {
            Main.getFooterController().initPRGs(spffChunk);
        } catch (Exception e) {
            System.err.println("Could not properly set up the PRG buttons.");
            e.printStackTrace();
        }
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
            System.err.println("Error while transferring the properties from the editors to the chunk's structure.");
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

//    public JFXButton getRMGroupButton(RMGroup rmGroup) {
//        switch (rmGroup) {
//            case SONG:
//                return songButton;
//            case STYLE:
//                return styleButton;
//            case VOICE:
//                return voiceButton;
//            default:
//                return null;
//        }
//    }

    public void loadRMGroup(RMGroup rmGroup) {
        String rmPath = rmGroup.toString();
        rmPath = rmPath.charAt(0) + rmPath.substring(1).toLowerCase(); // not necessarily needed
        if (rmGroup == RMGroup.TITLE)
            rmPath = "Home"; // This has to be done in order to guarantee consistency: the linked RMGroup is called
        // TITLE and not HOME. The keyboard doesn't has something comparable to 'Home', this is only to provide the
        // user with first information and an overview.

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/yamaha/view/" + rmPath + ".fxml"));
        AnchorPane loaderUI = null;
        try {
            loaderUI = loader.load();
        } catch (Exception e) {
            System.err.println("Could not properly load the UI linked to the requested Registration Memory Content " +
                    "Group");
            e.printStackTrace();
        }
        Main.getRightPane().setCenter(loaderUI);
        ((EditorController) loader.getController()).updateUI(); // it has to be ensured that all Controllers linked
        // to the Views extend EditorController!

        selectRMGroup(rmGroup);
        currentRMGroup = rmGroup;
    }

    /**
     * Enables or disables the button associated with the given rmGroup.
     *
     * @param rmGroup the Registration Memory Content group
     * @param enabled indicated whether the button should be enabled or disabled
     */
    public void enableRMGroupButton(RMGroup rmGroup, boolean enabled) {
        rmGroupToggleButtonMap.get(rmGroup).setDisable(!enabled);
    }

    @FXML
    public void loadHome() {
        loadRMGroup(RMGroup.TITLE);
    }

    @FXML
    public void loadStyle() {
        loadRMGroup(RMGroup.STYLE);
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
        /*AnchorPane voice = null;
        try {
            voice = FXMLLoader.load(getClass().getResource("/view/Voice.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getRightPane().setCenter(voice);
        // initialize
        currentRMGroup = RMGroup.VOICE;*/
    }

    private void selectRMGroup(RMGroup rmGroup) {
        buttonsToggleGroup.selectToggle(rmGroupToggleButtonMap.get(rmGroup));
        currentRMGroup = rmGroup;
    }

    public RMGroup getCurrentRMGroup() {
        return currentRMGroup;
    }

}