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

    //<editor-fold desc="FXML Labels">
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

    private List<JFXSlider> volumeStyleSliders;
    //</editor-fold>

    //<editor-fold desc="FXML Toggle Buttons">
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
    private JFXToggleButton ACMPToggle;
    @FXML
    private JFXToggleButton SyncStartToggle;
    @FXML
    private JFXToggleButton SyncStopToggle;
    //</editor-fold>

    //<editor-fold desc="FXML Combo Boxes">
    @FXML
    private JFXComboBox<FingeringType> fingeringTypeComboBox;
    //</editor-fold>

    //<editor-fold desc="FXML Buttons">
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
    //</editor-fold>

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
        volumeStyleTextField.setTextFormatter(getTextFormatter());
        addNumberRangeLimitation(volumeStyleTextField);

        for (int i = 0; i < 8; i++) {
            StyleChannel styleChannel = StyleChannel.getChannel(i + 1);
            volumeStyleSliders.get(i).valueProperty().bindBidirectional(styleEditor.volumeProperty(styleChannel));
            addResetCtrlFunctionality(volumeStyleSlider, () -> styleEditor.initVolumeProperty(styleChannel));

            TextField volumeStyleTextField = volumeStyleTextFields.get(i);
            volumeStyleTextField.textProperty().bindBidirectional(volumeStyleSliders.get(i).valueProperty(),
                    new NumberStringConverter(NumberFormat.getIntegerInstance()));
            volumeStyleTextField.setTextFormatter(getTextFormatter());
            addNumberRangeLimitation(volumeStyleTextField);
        }

        // ACMP
        ACMPToggle.selectedProperty().bindBidirectional(styleEditor.isACMPEnabledProperty());
        addResetCtrlFunctionality(ACMPToggle, () -> styleEditor.initIsACMPEnabledProperty());

        SyncStartToggle.selectedProperty().bindBidirectional(styleEditor.isSyncStartEnabledProperty());
        addResetCtrlFunctionality(SyncStartToggle, () -> styleEditor.initIsSyncStartEnabledProperty());

        SyncStopToggle.selectedProperty().bindBidirectional(styleEditor.isSyncStopEnabledProperty());
        addResetCtrlFunctionality(SyncStopToggle, () -> styleEditor.initIsSyncStopEnabledProperty());

        fingeringTypeComboBox.valueProperty().bindBidirectional(styleEditor.fingeringTypeProperty());
        addResetCtrlFunctionality(fingeringTypeComboBox, () -> styleEditor.initFingeringTypeProperty());
        fingeringTypeComboBox.disableProperty().bind(ACMPToggle.selectedProperty().not());

        for (int i = 0; i < 8; i++) {
            JFXToggleButton styleChannelToggel = styleChannelToggels.get(i);
            StyleChannel styleChannel = StyleChannel.getChannel(i + 1);
            styleChannelToggel.selectedProperty().bindBidirectional(styleEditor.isChannelEnabledProperty(styleChannel));
            addResetCtrlFunctionality(styleChannelToggel, () -> styleEditor.initIsChannelEnabledProperty(styleChannel));
        }

        // StylePart
        /// ...

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

    /**
     * Sets a value range (0-127) for numbers in the given textField.
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

    private TextFormatter<String> getTextFormatter() {
        // https://uwesander.de/the-textformatter-class-in-javafx-how-to-restrict-user-input-in-a-text-field.html
        UnaryOperator<TextFormatter.Change> filter = getFilter();
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }

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