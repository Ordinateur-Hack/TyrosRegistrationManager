package com.yamaha.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StyleChooserController {



    @FXML
    private Label cancelButton;

    public static void openStyleChooser() {
        try {
            AnchorPane anchorPane = FXMLLoader.load(StyleChooserController.class
                    .getResource("/com/yamaha/view/StyleChooser.fxml"));
            // no longer needed since new approach is with one AnchorPane nested into the root AnchorPane
            /*Rectangle rect = new Rectangle(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
            rect.setArcHeight(10.0);
            rect.setArcWidth(10.0);
            anchorPane.setClip(rect);*/

            Scene scene = new Scene(anchorPane, anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Style Chooser");
            stage.setScene(scene);

            // see https://www.quickprogrammingtips.com/java/accessing-outer-class-local-variables-from-inner-class-methods.html
            // bad style to bypass restriction
            // so that passed local variables can be modified from the inner class method?
            double xOffset[] = {0.0};
            double yOffset[] = {0.0};

            anchorPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset[0] = event.getSceneX();
                    yOffset[0] = event.getSceneY();
                }
            });
            anchorPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset[0]);
                    stage.setY(event.getScreenY() - yOffset[0]);
                }
            });

            stage.show();
        } catch (IOException e) {
            System.err.println("Could not properly load the StyleChooser");
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });
    }
}
