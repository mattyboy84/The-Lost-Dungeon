package com.company;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Main extends Application implements EventHandler<ActionEvent> {


    Button castSpell;
    Button btnAdd;
    // Button btnSubtract;
    Label lbl;
    int iCounter = 0;
    final TextField testfield = new TestField();
    final Spells[] SpellBook = new Spells[6];
    String nextPlayer = "b";
    int P1H = 10;
    int P2H = 10;
    int P1M = 10;
    int P2M = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        SpellBook[0] = new Spells("Fire blast", "Fire", 6, 3);
        SpellBook[1] = new Spells("Icy Mist", "Ice", 3, 2);
        SpellBook[2] = new Spells("Tornado", "Air", 4, 2);
        SpellBook[3] = new Spells("lighting zappy doo", "Electricity", 4, 3);
        SpellBook[4] = new Spells("OOF", "God", 900, 1);
        SpellBook[5] = new Spells("Rock throw", "sad", 1, 0);
        //primaryStage.setFullScreen(true);
        castSpell = new Button();
        castSpell.setText("Cast Spell");
//This now just calls the method btnAdd_Click method
        castSpell.setOnAction(e -> CastSpell_Click());
        // btnSubtract = new Button();
        // btnSubtract.setText("Subtract");
        Label label1 = new Label("Enter Spell name:");
//This now just calls the method btnSubract_Click method
        // btnSubtract.setOnAction(e -> btnSubtract_Click());
        //BackgroundFill Background_fill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
        // Background background= new Background(Background_fill);
        label1.setTranslateX(200);
        label1.setTranslateY(200);
        testfield.setTranslateY(200);
        testfield.setTranslateX(200);
        castSpell.setTranslateX(200);
        castSpell.setTranslateY(200);
        lbl = new Label();
        lbl.setTranslateY(300);
        lbl.setText(Integer.toString(iCounter));
        HBox pane = new HBox(10);
        //pane.setBackground(background);
        pane.getChildren().addAll(label1, testfield, castSpell, lbl);
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add/Sub");
        primaryStage.show();
    }

    private void CastSpell_Click() {

        nextPlayer = swapPlayer(nextPlayer);
        SpellControl(nextPlayer);
        String enteredSpell = testfield.getText();
        int damage = 0;
        for (int i = 0; i < 6; i++) {
            if (SpellBook[i].getSpellName().equalsIgnoreCase(enteredSpell)) {
                lbl.setText(nextPlayer + " Used " + enteredSpell + " it did " + SpellBook[i].getDamage() + " Damage");
            }

        }

    }

    String swapPlayer(String nextPlayer) {
        if (nextPlayer.equals("b")) {
            return "a";
        } else {
            return "b";
        }
    }

    private void btnSubtract_Click() {
        iCounter--;
        lbl.setText(Integer.toString(iCounter));
    }

    private void SpellControl(String nextPlayer) {
        if (nextPlayer.equals("a")) {

        } else if (nextPlayer.equals("b")) {

        }
    }

    @Override
    public void handle(ActionEvent event) {

    }

    private class TestField extends TextField {
    }
}