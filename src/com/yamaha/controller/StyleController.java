package com.yamaha.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;
import com.yamaha.application.Main;
import com.yamaha.model.FXUtil;
import com.yamaha.model.editor.FingeringType;
import com.yamaha.model.editor.StyleChannel;
import com.yamaha.model.editor.StyleEditor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.converter.NumberStringConverter;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class StyleController extends EditorController {

    private StyleEditor styleEditor;

    //<editor-fold desc="FXML Labels">
    @FXML
    private TextField volumeGeneralTextField;
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

    private List<TextField> volumeTextFields;
    //</editor-fold>

    //<editor-fold desc="FXML Sliders">
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

    private List<JFXSlider> volumeSliders;
    //</editor-fold>

    //<editor-fold desc="FXML Toggle Buttons">
    @FXML
    private JFXToggleButton channelRHY1Toggle;
    @FXML
    private JFXToggleButton channelRHY2Toggle;
    @FXML
    private JFXToggleButton channelBASSToggle;
    @FXML
    private JFXToggleButton channelCHD1Toggle;
    @FXML
    private JFXToggleButton channelCHD2Toggle;
    @FXML
    private JFXToggleButton channelPADToggle;
    @FXML
    private JFXToggleButton channelPHR1Toggle;
    @FXML
    private JFXToggleButton channelPHR2Toggle;

    private List<JFXToggleButton> channelToggels;

    private ToggleGroup styleSection;

    @FXML
    private JFXToggleButton controlACMPToggle;
    @FXML
    private JFXToggleButton controlSyncStartToggle;
    @FXML
    private JFXToggleButton controlSyncStopToggle;
    //</editor-fold>

    //<editor-fold desc="FXML Combo Boxes">
    @FXML
    private JFXComboBox<FingeringType> fingeringTypeComboBox;
    //</editor-fold>

    //<editor-fold desc="FXML Buttons">
    @FXML
    private ToggleButton intro1Button;
    @FXML
    private ToggleButton intro2Button;
    @FXML
    private ToggleButton intro3Button;
    @FXML
    private ToggleButton mainAButton;
    @FXML
    private ToggleButton mainBButton;
    @FXML
    private ToggleButton mainCButton;
    @FXML
    private ToggleButton mainDButton;
    @FXML
    private ToggleButton breakFillButton;
    @FXML
    private ToggleButton ending1Button;
    @FXML
    private ToggleButton ending2Button;
    @FXML
    private ToggleButton ending3Button;
    //</editor-fold>

    @FXML
    public void initialize() {
        volumeTextFields = Arrays.asList(volumeRHY1TextField, volumeRHY2TextField, volumeBASSTextField,
                volumeCHD1TextField, volumeCHD2TextField, volumePADTextField, volumePHR1TextField, volumePHR2TextField);
        volumeSliders = Arrays.asList(volumeRHY1Slider, volumeRHY2Slider, volumeBASSSlider, volumeCHD1Slider,
                volumeCHD2Slider, volumePADSlider, volumePHR1Slider, volumePHR2Slider);
        channelToggels = Arrays.asList(channelRHY1Toggle, channelRHY2Toggle, channelBASSToggle, channelCHD1Toggle,
                channelCHD2Toggle, channelPADToggle, channelPHR1Toggle, channelPHR2Toggle);
        fingeringTypeComboBox.getItems().addAll(FingeringType.values());

        styleSection = new ToggleGroup();
        styleSection.getToggles().addAll(intro1Button, intro2Button, intro3Button, mainAButton, mainBButton,
                mainCButton, mainDButton, breakFillButton, ending1Button, ending2Button, ending3Button);
        FXUtil.addAlwaysOneSelectedSupport(styleSection);
    }

    public void updateUI() {
        styleEditor = Main.getFooterController().getCurrentPRG().getStyleEditor();
        addBindings();
    }

    private void addBindings() {
        // Volume (Sliders and TextFields)
        volumeStyleSlider.valueProperty().bindBidirectional(styleEditor.volumeStyleProperty());
        addResetCtrlFunctionality(volumeStyleSlider, () -> styleEditor.initVolumeStyleProperty());
        volumeGeneralTextField.textProperty().bindBidirectional(volumeStyleSlider.valueProperty(), new
                NumberStringConverter(NumberFormat.getIntegerInstance()));
        volumeGeneralTextField.setTextFormatter(getTextFormatter());
        addNumberRangeLimitation(volumeGeneralTextField);

        for (int i = 0; i < 8; i++) {
            StyleChannel styleChannel = StyleChannel.getChannel(i + 1);
            volumeSliders.get(i).valueProperty().bindBidirectional(styleEditor.volumeProperty(styleChannel));
            addResetCtrlFunctionality(volumeStyleSlider, () -> styleEditor.initVolumeProperty(styleChannel));

            TextField volumeStyleTextField = volumeTextFields.get(i);
            volumeStyleTextField.textProperty().bindBidirectional(volumeSliders.get(i).valueProperty(),
                    new NumberStringConverter(NumberFormat.getIntegerInstance()));
            volumeStyleTextField.setTextFormatter(getTextFormatter());
            addNumberRangeLimitation(volumeStyleTextField);
        }

        // Channels (Toggels)
        for (int i = 0; i < 8; i++) {
            JFXToggleButton styleChannelToggel = channelToggels.get(i);
            StyleChannel styleChannel = StyleChannel.getChannel(i + 1);
            styleChannelToggel.selectedProperty().bindBidirectional(styleEditor.isChannelEnabledProperty(styleChannel));
            addResetCtrlFunctionality(styleChannelToggel, () -> styleEditor.initIsChannelEnabledProperty(styleChannel));
        }

        // StyleSection

        // Other controls
        controlACMPToggle.selectedProperty().bindBidirectional(styleEditor.isACMPEnabledProperty());
        addResetCtrlFunctionality(controlACMPToggle, () -> styleEditor.initIsACMPEnabledProperty());

        controlSyncStartToggle.selectedProperty().bindBidirectional(styleEditor.isSyncStartEnabledProperty());
        addResetCtrlFunctionality(controlSyncStartToggle, () -> styleEditor.initIsSyncStartEnabledProperty());

        controlSyncStopToggle.selectedProperty().bindBidirectional(styleEditor.isSyncStopEnabledProperty());
        addResetCtrlFunctionality(controlSyncStopToggle, () -> styleEditor.initIsSyncStopEnabledProperty());

        fingeringTypeComboBox.valueProperty().bindBidirectional(styleEditor.fingeringTypeProperty());
        addResetCtrlFunctionality(fingeringTypeComboBox, () -> styleEditor.initFingeringTypeProperty());
        // disable fingeringTypeComboBox if controlACMPToggle is disabled
        fingeringTypeComboBox.disableProperty().bind(controlACMPToggle.selectedProperty().not());
    }

    @FXML
    private void changeStyleSection(ActionEvent event) {
        ToggleGroup stylePartGroup = new ToggleGroup();
//		stylePartGroup.getToggles().addAll(intro1Button, intro2Button);
    }

    //<editor-fold desc="Check this out later">
    // unbind the bindings before initializing some values in the saveFile-process
    // in order to avoid flickering buttons
    public final void unbindCriticalBindings() {
        int i = 1;
        for (JFXToggleButton styleChannelToggel : channelToggels) {
            final int j = i;
            styleChannelToggel.selectedProperty().unbindBidirectional(styleEditor.isChannelEnabledProperty
                    (StyleChannel.getChannel(j)));
            i++;
        }
    }

    // only testing
    public final void rebindCriticalBindings() {
        int i = 1;
        for (JFXToggleButton styleChannelToggel : channelToggels) {
            final int j = i;
            styleChannelToggel.selectedProperty().bindBidirectional(styleEditor.isChannelEnabledProperty(StyleChannel
                    .getChannel(j)));
            i++;
        }
    }
    //</editor-fold>

    /**
     * Sets a value range (0-127) for numbers in the given textField.
     *
     * @param textField the textField to which the restriction should be applied
     */
    private void addNumberRangeLimitation(TextField textField) {
        textField.textProperty().addListener(change -> {
            try {
                String text = textField.getText();
                if (!text.equals("")) {
                    if (text.matches("0\\d"))
                        textField.setText(String.valueOf(text.charAt(1)));

                    int value = Integer.parseInt(textField.getText());
                    if (value < 0)
                        textField.setText("0");
                    else if (value > 127)
                        textField.setText("127");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.err.println("No int number in the TextField.");
            }
        });
    }

    /**
     * This TextFormatter is created by using the filter from the getFilter()-method.
     *
     * @return the TextFormatter used to keep the text in the volumeTextFields in the desired format
     */
    private TextFormatter<String> getTextFormatter() {
        // https://uwesander.de/the-textformatter-class-in-javafx-how-to-restrict-user-input-in-a-text-field.html
        UnaryOperator<TextFormatter.Change> filter = getFilter();
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }

    /**
     * @return the filter used to keep the text in the volumeTextFields in the desired format
     */
    private UnaryOperator<TextFormatter.Change> getFilter() {
        return new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                String text = change.getText();
                if (change.getText().matches("\\d+") || text.isEmpty()) {
                    return change;
                }
                return null;
            }
        };
    }

}

// https://github.com/jfoenixadmin/JFoenix/issues/431
class ToggleButtonRestricted extends ToggleButton {

    /**
     * Toggles the state of the toggle button if and only if the toggle button
     * has not been already selected or is not part of a {@link ToggleGroup}.
     */
    @Override
    public void fire() {
        // don't toggle from selected to not selected if part of a group
        if (getToggleGroup() == null || !isSelected()) // fire always if not selected
            super.fire();
    }

}

class MyButton extends JFXToggleNode {



}