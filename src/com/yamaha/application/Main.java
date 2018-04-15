package com.yamaha.application;

import com.yamaha.controller.FooterController;
import com.yamaha.controller.MenuBarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static Stage primaryStage;

    // Create a static root node to pass to the controllers
    private static BorderPane root = new BorderPane();

    private static FXMLLoader menuBarLoader;
    private static FXMLLoader footerLoader;

    public static void main(String[] args) {
         launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /* Basic layout
        ----------------------------------
        |   ||                           |
        |   ||                           |4
        |   ||                           |
        |   ||                           |
        |   ||                           |
        |   ||                           |
        |   ||---------------------------|
        |   ||                           |
        ----------------------------------
         */
        try {
            // Set up the menu bar
            menuBarLoader = new FXMLLoader(getClass().getResource("/com/yamaha/view/MenuBar.fxml"));
            AnchorPane menuBar = menuBarLoader.load();
            root.setLeft(menuBar);

            BorderPane right = new BorderPane();

            // Set up the footer
            footerLoader = new FXMLLoader(getClass().getResource("/com/yamaha/view/Footer.fxml"));
            AnchorPane footer = footerLoader.load();
            right.setBottom(footer);

            // Set up the center
            AnchorPane center = FXMLLoader.load(getClass().getResource("/com/yamaha/view/Title.fxml"));
            right.setCenter(center);

            root.setRight(right);

            // Set up the scene
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            double width = bounds.getWidth() * 18/32;
            double height = bounds.getHeight() * 20/32;
            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Set up the stage
            primaryStage.setMinWidth(1200); primaryStage.setMinHeight(700);
            // instead of primaryStage.centerOnScreen();
            // see discussion: https://stackoverflow.com/questions/29558449/javafx-center-stage-on-screen
            primaryStage.setX((bounds.getWidth() - width) / 2);
            primaryStage.setY((bounds.getHeight() - height) / 2);
            primaryStage.setTitle("Tyros3 Registration Manager");
            primaryStage.setScene(scene);

            Main.primaryStage = primaryStage;
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Could not properly set up the window.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the root node for controllers to use.
     * @return the root node
     */
    public static BorderPane getRoot() {
        return root;
    }

    /**
     * Returns the primaryStage passed into the start() method.
     * @return the primaryStage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @return the controller for the menu bar
     */
    public static MenuBarController getMenuBarController() {
        return menuBarLoader.getController();
    }

    /**
     * @return the footer controller
     */
    public static FooterController getFooterController() {
        return footerLoader.getController();
    }

//    public static void showResetUI(AnchorPane root) {
//        Scene scene = new Scene(root, 520, 260);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Reset Registration Button");
//        stage.centerOnScreen();
//        stage.setResizable(false);
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UTILITY);
//        stage.showAndWait(); // nochmal anschauen
//    }

}