package com.yamaha.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.yamaha.application.Main;
import com.yamaha.model.editor.FingeringType;
import com.yamaha.model.editor.StyleChannel;
import com.yamaha.model.editor.StyleEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class StyleController extends EditorController {

    private StyleEditor styleEditor;

    // Labels
    @FXML
    private TextField volumeStyleTextField;
    @FXML
    private TextField volumeRHY1TextField;
    @FXML
    private TextField volumeRHY2TextField;
    @FXML
    private TextField volumeBASSTextField;
    @FXML
    private TextField volumeCHD1TextField;
    @FXML
    private TextField volumeCHD2TextField;
    @FXML
    private TextField volumePADTextField;
    @FXML
    private TextField volumePHR1TextField;
    @FXML
    private TextField volumePHR2TextField;

    private List<TextField> volumeStyleTextFields;


    // Sliders
    @FXML
    private JFXSlider volumeStyleSlider;
    @FXML
    private JFXSlider volumeRHY1Slider;
    @FXML
    private JFXSlider volumeRHY2Slider;
    @FXML
    private JFXSlider volumeBASSSlider;
    @FXML
    private JFXSlider volumeCHD1Slider;
    @FXML
    private JFXSlider volumeCHD2Slider;
    @FXML
    private JFXSlider volumePADSlider;
    @FXML
    private JFXSlider volumePHR1Slider;
    @FXML
    private JFXSlider volumePHR2Slider;

    private List<JFXSlider> volumeStyleSliders;


    // Toggle Buttons
    @FXML
    private JFXToggleButton RHY1Toggle;
    @FXML
    private JFXToggleButton RHY2Toggle;
    @FXML
    private JFXToggleButton BASSToggle;
    @FXML
    private JFXToggleButton CHD1Toggle;
    @FXML
    private JFXToggleButton CHD2Toggle;
    @FXML
    private JFXToggleButton PADToggle;
    @FXML
    private JFXToggleButton PHR1Toggle;
    @FXML
    private JFXToggleButton PHR2Toggle;

    private List<JFXToggleButton> styleChannelToggels;

    @FXML
    private JFXToggleButton toggleACMP;
    @FXML
    private JFXToggleButton toggleSyncStart;
    @FXML
    private JFXToggleButton toggleSyncStop;

    // ComboBoxes
    @FXML
    private JFXComboBox<FingeringType> fingeringTypeComboBox;

    // Buttons
    @FXML
    private JFXButton intro1Button;
    @FXML
    private JFXButton intro2Button;
    @FXML
    private JFXButton intro3Button;
    @FXML
    private JFXButton mainAButton;
    @FXML
    private JFXButton mainBButton;
    @FXML
    private JFXButton mainCButton;
    @FXML
    private JFXButton mainDButton;
    @FXML
    private JFXButton breakFillButton;
    @FXML
    private JFXButton ending1Button;
    @FXML
    private JFXButton ending2Button;
    @FXML
    private JFXButton ending3Button;


    @FXML
    public void initialize() {
        // Volume
        volumeStyleTextFields = Arrays.asList(volumeRHY1TextField, volumeRHY2TextField, volumeBASSTextField,
                volumeCHD1TextField, volumeCHD2TextField, volumePADTextField, volumePHR1TextField, volumePHR2TextField);
        volumeStyleSliders = Arrays.asList(volumeRHY1Slider, volumeRHY2Slider, volumeBASSSlider, volumeCHD1Slider,
                volumeCHD2Slider, volumePADSlider, volumePHR1Slider, volumePHR2Slider);
        styleChannelToggels = Arrays.asList(RHY1Toggle, RHY2Toggle, BASSToggle, CHD1Toggle, CHD2Toggle, PADToggle,
                PHR1Toggle, PHR2Toggle);

        fingeringTypeComboBox.getItems().addAll(FingeringType.values());

    }

    public void updateUI() {
        styleEditor = Main.getFooterController().getCurrentPRG().getStyleEditor();
        addBindings();
    }

    private void addBindings() {
        // Volume
        volumeStyleSlider.valueProperty().bindBidirectional(styleEditor.volumeStyleProperty());
        addResetCtrlFunctionality(volumeStyleSlider, () -> styleEditor.initVolumeStyleProperty());
        volumeStyleTextField.textProperty().bindBidirectional(volumeStyleSlider.valueProperty(), new
                NumberStringConverter(NumberFormat.getIntegerInstance()));
        addNumberRangeLimitation(volumeStyleTextField);

        int i = 1;
        for (JFXSlider volumeStyleSlider : volumeStyleSliders) {
            final int j = i;
            volumeStyleSlider.valueProperty().bindBidirectional(styleEditor.volumeProperty(StyleChannel.getChannel(j)));
            addResetCtrlFunctionality(volumeStyleSlider, () -> styleEditor.initVolumeProperty(StyleChannel.getChannel
                    (j)));
            i++;
        }

        i = 0;
        for (TextField volumeTextField : volumeStyleTextFields) {
            final int j = i;
            volumeTextField.textProperty().bindBidirectional(volumeStyleSliders.get(j).valueProperty(), new
                    NumberStringConverter(NumberFormat.getIntegerInstance()));
            volumeTextField.setTextFormatter(getTextFormatter());
            addNumberRangeLimitation(volumeTextField);
            i++;
        }


        // ACMP
        toggleACMP.selectedProperty().bindBidirectional(styleEditor.isACMPEnabledProperty());
        addResetCtrlFunctionality(toggleACMP, () -> styleEditor.initIsACMPEnabledProperty());

        toggleSyncStart.selectedProperty().bindBidirectional(styleEditor.isSyncStartEnabledProperty());
        addResetCtrlFunctionality(toggleSyncStart, () -> styleEditor.initIsSyncStartEnabledProperty());

        toggleSyncStop.selectedProperty().bindBidirectional(styleEditor.isSyncStopEnabledProperty());
        addResetCtrlFunctionality(toggleSyncStop, () -> styleEditor.initIsSyncStopEnabledProperty());

        fingeringTypeComboBox.valueProperty().bindBidirectional(styleEditor.fingeringTypeProperty());
        addResetCtrlFunctionality(fingeringTypeComboBox, () -> styleEditor.initFingeringTypeProperty());
        fingeringTypeComboBox.disableProperty().bind(toggleACMP.selectedProperty().not());

        i = 1;
        for (JFXToggleButton styleChannelToggel : styleChannelToggels) {
            final int j = i;
            styleChannelToggel.selectedProperty().bindBidirectional(styleEditor.isChannelEnabledProperty(StyleChannel
                    .getChannel(j)));
            addResetCtrlFunctionality(styleChannelToggel, () -> styleEditor.initIsChannelEnabledProperty(StyleChannel
                    .getChannel(j)));
            i++;
        }

        // StylePart

    }

    @FXML
    private void changeStylePart(ActionEvent event) {
        ToggleGroup stylePartGroup = new ToggleGroup();
//		stylePartGroup.getToggles().addAll(intro1Button, intro2Button);
    }

    // unbind the bindings before initializing some values in the saveFile-process
    // in order to avoid flickering buttons
    public final void unbindCriticalBindings() {
        int i = 1;
        for (JFXToggleButton styleChannelToggel : styleChannelToggels) {
            final int j = i;
            styleChannelToggel.selectedProperty().unbindBidirectional(styleEditor.isChannelEnabledProperty
                    (StyleChannel.getChannel(j)));
            i++;
        }
    }

    // only testing
    public final void rebindCriticalBindings() {
        int i = 1;
        for (JFXToggleButton styleChannelToggel : styleChannelToggels) {
            final int j = i;
            styleChannelToggel.selectedProperty().bindBidirectional(styleEditor.isChannelEnabledProperty(StyleChannel
                    .getChannel(j)));
            i++;
        }
    }

    // Text Labels
    private void addNumberRangeLimitation(TextField textField) {
        textField.textProperty().addListener(change -> {
            try {
                if (!textField.getText().equals("")) {
                    int value = Integer.parseInt(textField.getText());
                    if (value < 0)
                        textField.setText("0");
                    if (value > 127)
                        textField.setText("127");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("No int number in the TextField.");
            }
        });
    }

    private TextFormatter<String> getTextFormatter() {
        // https://uwesander.de/the-textformatter-class-in-javafx-how-to-restrict-user-input-in-a-text-field.html
        UnaryOperator<TextFormatter.Change> filter = getFilter();
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }

    private UnaryOperator<TextFormatter.Change> getFilter() {
        return change -> {
            String text = change.getText();
            if (text.matches("\\d{1,3}")) {
                return change;
            }
            if (text.isEmpty())
                return change;
            return null;
        };
    }

    private void addResetCtrlFunctionality(Node node, Performer performer) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.isControlDown()) {
                    performer.perform();
                    Main.getMenuBarController().loadStyle();
                }

            }
        });
    }

}

interface Performer {
    void perform();
}