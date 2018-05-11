package com.yamaha.application;

import com.yamaha.controller.FooterController;
import com.yamaha.controller.MenuBarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    // Create a static root node to pass to the controllers
    private static BorderPane rootPane = new BorderPane();

    // Create a static root (right side of the view) to pass to the controllers
    private static BorderPane rightPane = new BorderPane();

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
        |   ||                           |
        |   ||                           |
        |   ||                           |
        |   ||                           |
        |   ||                           |
        |   ||---------------------------|
        |   ||                           |
        ----------------------------------
         */
        try {
            System.out.println("<<<< Set up layout ...");

            // Set up the menu bar
            menuBarLoader = new FXMLLoader(getClass().getResource("/com/yamaha/view/MenuBar.fxml"));
            AnchorPane menuBar = menuBarLoader.load();
            rootPane.setLeft(menuBar);

            rightPane = new BorderPane();

            // Set up the footer
            footerLoader = new FXMLLoader(getClass().getResource("/com/yamaha/view/Footer.fxml"));
            AnchorPane footer = footerLoader.load();
            rightPane.setBottom(footer);

            loadEmptyEditorsPane();

            rootPane.setRight(rightPane);

            // Set up the scene
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            double width = bounds.getWidth() * 18 / 32;
            double height = bounds.getHeight() * 20 / 32;
            Scene scene = new Scene(rootPane, width, height);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            scene.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2)
                        primaryStage.setFullScreen(true);
                }
            });

            // Set up the stage
//            primaryStage.getIcons().add(new Image("/com/yamaha/images/icon2_big.jpg"));
            primaryStage.getIcons().add(new Image("/com/yamaha/images/icon2_small.jpg"));
//            primaryStage.getIcons().add(new Image("/com/yamaha/images/icon2_medium.jpg"));
//            primaryStage.getIcons().add(new Image("/com/yamaha/images/icon2_verySmall.jpg"));
            primaryStage.setMinWidth(1100);
            primaryStage.setMinHeight(700);
            // instead of primaryStage.centerOnScreen();
            // see discussion: https://stackoverflow.com/questions/29558449/javafx-center-stage-on-screen
            primaryStage.setX((bounds.getWidth() - width) / 2);
            primaryStage.setY((bounds.getHeight() - height) / 2);
            primaryStage.setTitle("Tyros3 Registration Manager");
            primaryStage.setScene(scene);

            Main.primaryStage = primaryStage;
            primaryStage.show();

            System.out.println("Set up layout successfully >>>>");
            System.out.println();


        } catch (Exception e) {
            System.err.println("Could not properly set up the window.");
            e.printStackTrace();
        }
    }

    /**
     * @return the root pane for controllers to use
     */
    public static BorderPane getRootPane() {
        return rootPane;
    }

    /**
     * @return the pane containing the editor for controllers to use
     */
    public static BorderPane getRightPane() {
        return rightPane;
    }

    /**
     * @return the primaryStage passed into the start() method
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
     * @return the controller for the footer
     */
    public static FooterController getFooterController() {
        return footerLoader.getController();
    }

    /**
     * Loads an empty pane for editors.
     */
    public static void loadEmptyEditorsPane() {
        // Set up the center
        AnchorPane center = new AnchorPane();
        center.setStyle("-fx-background-color: white");
        rightPane.setCenter(center);
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