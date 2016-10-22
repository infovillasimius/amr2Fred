/*
 * Copyright (C) 2016 anto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package amr2fred;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author anto
 */
public class Amr2Fred extends Application {

    Stage secondStage = new Stage();
    PredMatrix pred = PredMatrix.getPredMatrix();
    private int rdfMode=0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("amr2fred");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 1000, 600);
        primaryStage.setScene(scene);

        Text scenetitle = new Text("Welcome to amr2fred");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 5, 1);

        Label amr = new Label("Type amr:");
        grid.add(amr, 0, 1);

        TextArea amrTextField = new TextArea();
        grid.add(amrTextField, 0, 2);

        Label fredLabel = new Label("Click the button to get AMR to Fred");
        grid.add(fredLabel, 0, 4);

        CheckBox cb = new CheckBox("Remove incorrect nodes & get...");
        grid.add(cb, 4, 3);
        
        ChoiceBox chBox = new ChoiceBox(FXCollections.observableArrayList(Glossary.rdfWriteMode.RDF_XML,
                Glossary.rdfWriteMode.RDF_XML_ABBREV,Glossary.rdfWriteMode.N_TRIPLE,
                Glossary.rdfWriteMode.TURTLE,Glossary.rdfWriteMode.N3));
        chBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,Number old_val,Number new_val)->{
                    this.rdfMode=new_val.intValue();
                    cb.setText("Remove incorrect nodes & get "+Glossary.rdfMode[new_val.intValue()]);
                });
        grid.add(chBox, 4, 4);
        
        Label removedLabel = new Label("AMR tree, Errors & Removed Nodes");
        grid.add(removedLabel, 4, 1);

        TextArea fred = new TextArea();
        fred.setEditable(false);
        fred.setPrefRowCount(16);
        fred.setPrefColumnCount(80);
        grid.add(fred, 0, 5, 5, 1);

        TextArea removed = new TextArea();
        removed.setEditable(false);
        //removed.setPrefRowCount(16);
        removed.setPrefColumnCount(4);
        removed.setText("");
        grid.add(removed, 4, 2);

        Text err = new Text();
        err.setFont(Font.font("Tahoma", FontWeight.BOLD, 10));
        grid.add(err, 0, 7);

        Button btn = new Button();
        btn.setText("AMR to Fred");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                fred.setText("");
                if (amrTextField.getLength() > 3) {
                    String amr = amrTextField.getText();
                    Parser instance = Parser.getInstance();
                    Node result = instance.parse(amr);
                    removed.setText("");

                    if (result != null) {
                        if (cb.isSelected()) {
                            result = instance.check(result);

                            RdfWriter writer = RdfWriter.getWriter();
                            writer.setMode(rdfMode);
                            fred.setText(writer.writeRdf(result));
                        } else {
                            fred.setText(result.toString());
                        }

                        if (!instance.getRemoved().isEmpty()) {
                            String removedNodes = "Removed nodes:\n";
                            for (Node n : instance.getRemoved()) {
                                removedNodes += n.toString2() + "\n";
                            }
                            removed.setText(removedNodes);
                        }

                        if (instance.getRootCopy() != null) {
                            removed.setText("AMR tree:\n" + instance.getRootCopy().toString2() + "\n\n" + removed.getText());
                        }

                        if (result.getTreStatus() == 1) {
                            err.setText("Warning! Something went wrong: one node is not ok!");
                        } else if (result.getTreStatus() > 999999) {
                            err.setText("Warning! Something went wrong: there is a recursive error!");
                        } else if (result.getTreStatus() > 0) {
                            err.setText("Warning! Something went wrong: " + result.getTreStatus() + " nodes are not ok!");
                        } else {
                            err.setText(" ");
                        }
                    } else {
                        fred.setText("Sintax error");
                    }
                } else if (amrTextField.getLength() == 0) {
                    fred.setText("No text");
                } else {
                    fred.setText("Sintax error");
                }

            }
        });

        grid.add(btn, 0, 3);

        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
