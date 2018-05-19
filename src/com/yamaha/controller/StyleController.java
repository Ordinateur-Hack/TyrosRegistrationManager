package com.yamaha.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.yamaha.application.Main;
import com.yamaha.model.FXUtil;
import com.yamaha.model.editor.FingeringType;
import com.yamaha.model.editor.StyleChannel;
import com.yamaha.model.editor.StyleEditor;
import com.yamaha.model.editor.StyleSection;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
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

    private ToggleGroup mainStyleSectionGroup;
    private ToggleGroup specialStyleSectionGroup;


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
    //</editor-fold

    //<editor-fold desc="FXML Animations">
    FadeTransition fillInTransition;
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

        mainStyleSectionGroup = new ToggleGroup();
        mainStyleSectionGroup.getToggles().addAll(mainAButton, mainBButton, mainCButton, mainDButton); // Do not change!
        FXUtil.addAlwaysOneSelectedSupport(mainStyleSectionGroup);

        specialStyleSectionGroup = new ToggleGroup();
        specialStyleSectionGroup.getToggles().addAll(intro1Button, intro2Button, intro3Button, breakFillButton,
                ending1Button, ending2Button, ending3Button); // Do not change!
    }

    public void updateUI() {
        styleEditor = Main.getFooterController().getCurrentPRG().getStyleEditor();
        addBindings();

        // Initialize elements which couldn't be set up using bidirectional Bindings

        StyleSection mainStyleSection = styleEditor.getMainStyleSection();
        mainStyleSectionGroup.selectToggle(mainStyleSectionGroup.getToggles().get(mainStyleSection
                .getRepresentationNumber() - 8));

        StyleSection specialStyleSection = styleEditor.getSpecialStyleSection();
        if (specialStyleSection.isFillIn()) {
            playFillInAnimation((ToggleButton) mainStyleSectionGroup.getToggles().get(specialStyleSection
                    .getRepresentationNumber() - 16), true);
        }
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
            JFXSlider volumeSlider = volumeSliders.get(i);
            volumeSlider.valueProperty().bindBidirectional(styleEditor.volumeProperty(styleChannel));
            addResetCtrlFunctionality(volumeSlider, () -> styleEditor.initVolumeProperty(styleChannel));

            TextField volumeTextField = volumeTextFields.get(i);
            volumeTextField.textProperty().bindBidirectional(volumeSliders.get(i).valueProperty(),
                    new NumberStringConverter(NumberFormat.getIntegerInstance()));
            volumeTextField.setTextFormatter(getTextFormatter());
            addNumberRangeLimitation(volumeTextField);
            addResetCtrlFunctionality(volumeTextField, () -> styleEditor.initVolumeProperty(styleChannel));
        }

        // Channels (Toggels)
        for (int i = 0; i < 8; i++) {
            JFXToggleButton styleChannelToggel = channelToggels.get(i);
            StyleChannel styleChannel = StyleChannel.getChannel(i + 1);
            styleChannelToggel.selectedProperty().bindBidirectional(styleEditor.isChannelEnabledProperty(styleChannel));
            addResetCtrlFunctionality(styleChannelToggel, () -> styleEditor.initIsChannelEnabledProperty(styleChannel));
        }

        // StyleSection
        for (Toggle mainToggleButton : mainStyleSectionGroup.getToggles()) {
            // EXAMPLE: MAIN_A is at index 0, representation number for MAIN_A is 8 (int),
            // representation number for A_FILL is 12 (int)
            int fillInNumber = mainStyleSectionGroup.getToggles().indexOf(mainToggleButton) + 16;

            ((ToggleButton) mainToggleButton).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (styleEditor.getSpecialStyleSection().isFillIn()) {
                        // stop FIll-In on the clicked button
                        playFillInAnimation(((ToggleButton) mainToggleButton), false);
                        styleEditor.setSpecialStyleSection(StyleSection.getStyleSection(fillInNumber - 8));

                    }

                    if (event.getClickCount() == 2) { // double click
                        styleEditor.setSpecialStyleSection(StyleSection.getStyleSection(fillInNumber));
                        playFillInAnimation(((ToggleButton) mainToggleButton), true);
                    }
                }
            });

            ((ToggleButton) mainToggleButton).selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (styleEditor.getSpecialStyleSection().isFillIn() && oldValue == true && newValue == false) {
                        // if this button was active and another main button is selected now
                        playFillInAnimation(((ToggleButton) mainToggleButton), false);
                        styleEditor.setSpecialStyleSection(StyleSection.getStyleSection(fillInNumber - 8));
                    }
                    // }
                }
            });
        }

        specialStyleSectionGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                styleEditor.setSpecialStyleSection(getSpecialStyleSection(((ToggleButton) newToggle)));
            }
        });


        mainStyleSectionGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                styleEditor.setMainStyleSection(StyleSection.getStyleSection(mainStyleSectionGroup.getToggles()
                        .indexOf(newToggle) + 8));
            }
        });

        /*//StyleSection
        styleSection.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                StyleSection myStyleSection = StyleSection.values[styleSection.getToggles().indexOf(newToggle)];
                styleEditor.setStyleSection(myStyleSection);
            }
        });

        styleEditor.styleSectionProperty().addListener(new ChangeListener<StyleSection>() {
            @Override
            public void changed(ObservableValue<? extends StyleSection> observable, StyleSection oldSection,
                                StyleSection newSection) {
                styleSection.selectToggle(styleSection.getToggles().get(newSection.ordinal()));
            }
        });*/

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

    /**
     *  Selects the section associated with the current Fill-In section, e. g. MAIN_A and FILL_A.
     * @param styleSection the StyleSection representing the Fill-In to remove.
     */
//    private void removeFillIn(StyleSection styleSection) {
//        if (styleSection.isFillIn()) {
//            styleEditor.setSpecialStyleSection(StyleSection.getStyleSection(styleSection.getRepresentationNumber() -
//                    8));
//        }
//    }


    /**
     * @param toggleButton the ToggleButton which opacity will be animated
     * @param enabled      true to start the animation, false to stop it
     */
    private void playFillInAnimation(ToggleButton toggleButton, boolean enabled) {
        if (enabled) {
            fillInTransition = new FadeTransition(Duration.seconds(0.5), toggleButton);
            fillInTransition.setFromValue(1.0);
            fillInTransition.setToValue(0.0);
            fillInTransition.setCycleCount(Animation.INDEFINITE);
            fillInTransition.setAutoReverse(true);
            fillInTransition.play();
        } else {
            if (fillInTransition == null)
                fillInTransition = new FadeTransition(Duration.seconds(0.5), toggleButton);
            fillInTransition.stop();
            toggleButton.setOpacity(1);
        }
    }

    /**
     * @param toggleButton the {@link ToggleButton} representing a special {@link StyleSection}
     * @return the {@link StyleSection} associated with the given {@link ToggleButton}
     * @throws IllegalArgumentException if the {@link ToggleButton} does not represent a special {@link StyleSection}
     */
    private StyleSection getSpecialStyleSection(ToggleButton toggleButton) throws IllegalArgumentException {
        int i = specialStyleSectionGroup.getToggles().indexOf(toggleButton);
        if (i == -1) {
            throw new IllegalArgumentException("The given ToggleButton must represent a special StyleSection");
        }
        else if (i == 3)
            i = StyleSection.BREAK_FILL.getRepresentationNumber();
        else if (i >= 4)
            i += StyleSection.ENDING_1.getRepresentationNumber() - i;
        return StyleSection.getStyleSection(i);
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