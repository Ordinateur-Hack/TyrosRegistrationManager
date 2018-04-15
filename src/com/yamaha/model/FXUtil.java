package com.yamaha.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

// inspiration from https://stackoverflow.com/questions/35693180/javafx-wrap-content-text-in-alert-dialog
public abstract class FXUtil {

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

}