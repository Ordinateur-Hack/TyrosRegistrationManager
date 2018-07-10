package com.yamaha.controller;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.yamaha.application.Main;
import com.yamaha.model.BiMap;
import com.yamaha.model.FXUtil;
import com.yamaha.model.editor.FingeringType;
import com.yamaha.model.editor.Style.StyleChannel;
import com.yamaha.model.editor.Style.StyleEditor;
import com.yamaha.model.editor.Style.StyleName;
import com.yamaha.model.editor.Style.StyleSection;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
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
    @FXML
    private ToggleButton autoFillIn;

    private ToggleGroup mainStyleSectionGroup;
    private ToggleGroup specialStyleSectionGroup;
    private BiMap<StyleSection, ToggleButton> biMapStyleSection = new BiMap<>();
    private BiMap<StyleSection, ToggleButton> biMapStyleSectionFillIn = new BiMap<>();

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
    @FXML
    private JFXComboBox<String> styleNameComboBox;
    private TextField styleNameComboBoxEditor;
    //</editor-fold

    //<editor-fold desc="FXML Animations">
    FadeTransition fillInTransition = new FadeTransition();
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

        String[] styleNamesAlphabetical = StyleName.styleNameValues();
        Arrays.sort(styleNamesAlphabetical, String.CASE_INSENSITIVE_ORDER);
        styleNameComboBox.getItems().addAll(styleNamesAlphabetical);

        // https://github.com/jfoenixadmin/JFoenix/issues/220
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getStyleClass().add("jfx-combo-box");
        autoCompletePopup.getSuggestions().addAll(styleNamesAlphabetical);

        //SelectionHandler sets the value of the comboBox
        autoCompletePopup.setSelectionHandler(event -> {
            /*styleNameComboBox.setValue(event.getObject());*/
            styleNameComboBox.getSelectionModel().select(event.getObject());
        });
        autoCompletePopup.setSuggestionsCellFactory(param -> {
            return new JFXListCell<>();
        });

        styleNameComboBoxEditor = styleNameComboBox.getEditor();
        styleNameComboBoxEditor.addEventHandler(KeyEvent.ANY, event -> {
            // or: editor.textProperty().addListener(observable -> {...});
            if (!event.getCode().isNavigationKey() && !event.getCode().isWhitespaceKey()) {
                //The filter method uses the Predicate to filter the Suggestions defined above
                //I choose to use the contains method while ignoring cases
                autoCompletePopup.filter(stringItem -> stringItem.toLowerCase().contains(
                        styleNameComboBoxEditor.getText().toLowerCase()));
                //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
                if (autoCompletePopup.getFilteredSuggestions().isEmpty() || styleNameComboBox.isShowing() ||
                        event.getCode().isModifierKey()) {
                    autoCompletePopup.hide();
                } else {
                    styleNameComboBox.getSelectionModel().clearSelection();
                    autoCompletePopup.show(styleNameComboBoxEditor);
                }
            } else {
                event.consume();
            }
        });
        styleNameComboBoxEditor.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getClickCount() == 2) {
                styleNameComboBoxEditor.setText("");
            }
        });
        styleNameComboBoxEditor.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue && styleNameComboBox.getSelectionModel().isEmpty()) {
                styleEditor.initStyleNameProperty();
            }
        }));

        mainStyleSectionGroup = new ToggleGroup();
        mainStyleSectionGroup.getToggles().addAll(mainAButton, mainBButton, mainCButton, mainDButton); // Do not change!
        FXUtil.addAlwaysOneSelectedSupport(mainStyleSectionGroup);

        specialStyleSectionGroup = new ToggleGroup();
        specialStyleSectionGroup.getToggles().addAll(intro1Button, intro2Button, intro3Button, breakFillButton,
                ending1Button, ending2Button, ending3Button); // Do not change!

        biMapStyleSection.put(StyleSection.INTRO_1, intro1Button);
        biMapStyleSection.put(StyleSection.INTRO_2, intro2Button);
        biMapStyleSection.put(StyleSection.INTRO_3, intro3Button);
        biMapStyleSection.put(StyleSection.MAIN_A, mainAButton);
        biMapStyleSection.put(StyleSection.MAIN_B, mainBButton);
        biMapStyleSection.put(StyleSection.MAIN_C, mainCButton);
        biMapStyleSection.put(StyleSection.MAIN_D, mainDButton);
        biMapStyleSection.put(StyleSection.BREAK_FILL, breakFillButton);
        biMapStyleSection.put(StyleSection.ENDING_1, ending1Button);
        biMapStyleSection.put(StyleSection.ENDING_2, ending2Button);
        biMapStyleSection.put(StyleSection.ENDING_3, ending3Button);

        biMapStyleSectionFillIn.put(StyleSection.A_FILL, mainAButton);
        biMapStyleSectionFillIn.put(StyleSection.B_FILL, mainBButton);
        biMapStyleSectionFillIn.put(StyleSection.C_FILL, mainCButton);
        biMapStyleSectionFillIn.put(StyleSection.D_FILL, mainDButton);
    }

    public void updateUI() {
        styleEditor = Main.getFooterController().getCurrentPRG().getStyleEditor();

        // Initialize elements which couldn't be set up using bidirectional Bindings
        updateStyleSectionUI();

        addBindings();
    }

    private void updateStyleSectionUI() {
        StyleSection mainStyleSection = styleEditor.getMainStyleSection();
        mainStyleSectionGroup.selectToggle(biMapStyleSection.get(mainStyleSection));

        StyleSection specialStyleSection = styleEditor.getSpecialStyleSection();
        if (!specialStyleSection.isMainVariation()) {
            playFillInAnimation(biMapStyleSection.get(mainStyleSection), true);
            if (!specialStyleSection.isFillIn()) {
                specialStyleSectionGroup.selectToggle(biMapStyleSection.get(specialStyleSection));
            }
        }
        autoFillIn.setSelected(true);
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

        //<editor-fold desc="StyleSection">
        //////////////////
        // StyleSection///
        //////////////////
        for (Toggle mainButton : mainStyleSectionGroup.getToggles()) {
            ToggleButton mainToggleButton = ((ToggleButton) mainButton);

            // Click on the same button (MAIN)
            // Do not use MOUSE_CLICKED since the mainToggleButton.isSelected() condition will then always be true
            mainToggleButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    StyleSection specialStyleSection = styleEditor.getSpecialStyleSection();
                    if (mainToggleButton.isSelected()) { // if this button was selected when clicked
                        // or: if (mainToggleButton == biMapStyleSection.get(styleEditor.getMainStyleSection())) {
                        if (autoFillIn.isSelected() && specialStyleSection.isIntroOrEnding()
                                || specialStyleSection.isMainVariation()) {
                            styleEditor.setSpecialStyleSection(biMapStyleSectionFillIn.getKey(mainToggleButton));
                            if (specialStyleSection.isMainVariation()) {
                                playFillInAnimation(mainToggleButton, true);
                            } // if Intro or Ending the animation is already playing
                        } else {
                            styleEditor.setSpecialStyleSection(biMapStyleSection.getKey(mainToggleButton));
                            playFillInAnimation(mainToggleButton, false);
                        }
                        specialStyleSectionGroup.selectToggle(null); // deselect all special StyleSection toggles
                    }
                }
            });
        }

        // Click on another button (MAIN)
        mainStyleSectionGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                if (oldToggle != null) { // when we reset StyleSection, then all ToggleButtons will be deselected in
                    // order to prevent that the button we switched to is blinking when "Auto Fill In" is enabled
                    ToggleButton newToggleButton = ((ToggleButton) newToggle);
                    playFillInAnimation((ToggleButton) oldToggle, false); // disable animation on the previously
                    // selected toggle
                    styleEditor.setMainStyleSection(biMapStyleSection.getKey(newToggleButton));
                    if (autoFillIn.isSelected() || styleEditor.getSpecialStyleSection() == StyleSection.BREAK_FILL) {
                        styleEditor.setSpecialStyleSection(biMapStyleSectionFillIn.getKey(newToggleButton));
                        playFillInAnimation(newToggleButton, true);
                    } else {
                        styleEditor.setSpecialStyleSection(biMapStyleSection.getKey(newToggleButton));
                    }
                    specialStyleSectionGroup.selectToggle(null); // deselect all special StyleSection toggles
                }
            }
        });

        // Click on the same or another button (SPECIAL)
        for (Toggle specialButton : specialStyleSectionGroup.getToggles()) {
            ToggleButton specialToggleButton = ((ToggleButton) specialButton);

            specialToggleButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    StyleSection specialStyleSection = styleEditor.getSpecialStyleSection();
                    StyleSection mainStyleSection = styleEditor.getMainStyleSection();
                    if (specialToggleButton.isSelected()) { // if this button was selected when clicked
                        styleEditor.setSpecialStyleSection(mainStyleSection);
                        playFillInAnimation(biMapStyleSection.get(mainStyleSection), false);
                    } else { // if clicked on another button (i. e. this button was not selected when clicked)
                        styleEditor.setSpecialStyleSection(biMapStyleSection.getKey(specialToggleButton));
                        if (specialStyleSection.isMainVariation()) {
                            playFillInAnimation(biMapStyleSection.get(mainStyleSection), true);
                        }
                    }
                }
            });
        }

        for (Toggle toggle : mainStyleSectionGroup.getToggles()) {
            addResetCtrlFunctionality((Node) toggle, () -> resetStyleSection());
        }
        for (Toggle toggle : specialStyleSectionGroup.getToggles()) {
            addResetCtrlFunctionality((Node) toggle, () -> resetStyleSection());
        }
        //</editor-fold>

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

        styleNameComboBox.valueProperty().bindBidirectional(styleEditor.styleNameProperty());
        addResetCtrlFunctionality(styleNameComboBox, () -> styleEditor.initStyleNameProperty());
        addResetCtrlFunctionality(styleNameComboBoxEditor, () -> styleEditor.initStyleNameProperty());
    }

    /**
     * Adds a FillInTransition to a given toggleButton and plays it or stops the playing animation.
     *
     * @param toggleButton the ToggleButton which opacity will be animated
     * @param enabled      true to start the animation, false to stop it
     */
    private void playFillInAnimation(ToggleButton toggleButton, boolean enabled) {
        if (enabled) {
            fillInTransition = new FadeTransition(Duration.millis(160), toggleButton);
            fillInTransition.setFromValue(1.0);
            fillInTransition.setToValue(0.0);
            fillInTransition.setCycleCount(Animation.INDEFINITE);
            fillInTransition.setAutoReverse(true);
            fillInTransition.play();
        } else {
            fillInTransition.stop();
            toggleButton.setOpacity(1);
        }
    }

    private void resetStyleSection() {
        for (Toggle toggle : mainStyleSectionGroup.getToggles()) {
            playFillInAnimation((ToggleButton) toggle, false);
        }
        for (Toggle toggle : specialStyleSectionGroup.getToggles()) {
            playFillInAnimation((ToggleButton) toggle, false);
        }
        mainStyleSectionGroup.selectToggle(null);
        specialStyleSectionGroup.selectToggle(null);
        styleEditor.initMainStyleSectionProperty();
        styleEditor.initSpecialStyleSectionProperty();
        updateStyleSectionUI();
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

    @FXML
    private void openStyleChooser() {
        StyleChooserController.openStyleChooser();
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