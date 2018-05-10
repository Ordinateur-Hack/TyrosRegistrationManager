package com.yamaha.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class EditorController {

    /**
     * Refreshes the UI and rebinds the properties used in this EditorController.
     */
    public abstract void updateUI();

    /**
     * Adds a reset functionality to the specified node. When clicking on the node while holding down the control
     * key, this method invokes the perform()-method on the given Performer which should initialize the associated
     * property. This corresponds to resetting the value of this property.
     * <br>Note that the reset functionality does not reload the current RMGroup and does not refresh the UI since
     * the properties are bound bidirectional.
     *
     * @param node      the node to add the reset functionality to
     * @param performer the Performer (functional interface) providing the perform()-method called when clicking on
     *                  the node while holding down the control key
     */
    protected void addResetCtrlFunctionality(Node node, Performer performer) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.isControlDown()) {
                    performer.perform();
                }

            }
        });
    }

}