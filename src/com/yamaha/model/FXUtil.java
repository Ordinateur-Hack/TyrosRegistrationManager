package com.yamaha.model;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

// inspiration from https://stackoverflow.com/questions/35693180/javafx-wrap-content-text-in-alert-dialog
public abstract class FXUtil {

    /**
     * Creates an alert with the given alert type and automatically breaks lines.
     * @param alertType the alert type
     * @param numberOfWordsInOneLine the number of words in one line (the next word is wrapped)
     * @param message dialog's content
     * @return the specified alert
     */
    public static Alert createAlert(Alert.AlertType alertType, int numberOfWordsInOneLine, String message) {
        Alert alert = new Alert(alertType);

        // insert a line separator after every numberOfWordsInOneLine words
        StringBuilder sb = new StringBuilder();
        String[] words = message.split("\\s+"); // one or many whitespaces

        int wordNumber = 1;
        for (String word : words) {
            sb.append(word + " ");
            if(numberOfWordsInOneLine == wordNumber) {
                sb.append(System.lineSeparator());
                wordNumber = 0;
            }
            wordNumber++;
        }

        // store the message in a label and add this to the dialog pane of the alert
        Label label = new Label(sb.toString());
        alert.getDialogPane().setContent(label);

        return alert;
    }

    // Toggle Group - Use ToggleButtons as RadioButtons (always one button selected)
    // inspiration from http://www.jensd.de/wordpress/?p=2381
    /**
     * Adds a "AlwaysOneSelectedSupport" to the specified {@link ToggleGroup}.
     * <br>This means that the ToggleButtons of the given ToggleGroup will behave like {@link RadioButton}s, that
     * is, always only one button can be selected and there is no case where no button is selected.
     * @param toggleGroup a ToggleGroup consisting only of ToggleButtons
     * @throws IllegalArgumentException if the toggleGroup does not fully consist of ToggleButtons
     */
    public static void addAlwaysOneSelectedSupport(final ToggleGroup toggleGroup) throws IllegalArgumentException {
        for (Toggle toggle : toggleGroup.getToggles()) {
            if (!(toggle instanceof ToggleButton))
                throw new IllegalArgumentException("All elements in the ToggleGroup must be instances of " +
                        "ToggleButtons.");
            ((ToggleButton) toggle).addEventFilter(MouseEvent.MOUSE_PRESSED, consumeMouseEventFilter);
        }
    }

    private static EventHandler<MouseEvent> consumeMouseEventFilter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (((Toggle) event.getSource()).isSelected())
                event.consume();
        }
    };
}