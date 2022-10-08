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
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main Class. (Start the GUI)
 *
 * @author anto
 */
public class Amr2Fred extends Application {

    /**
     * Get Predmatrix table singleton instance loaded in memory
     */
    // pred = PredMatrix.getPredMatrix();
    RdfWriter rdfWriter = RdfWriter.getWriter();

    /**
     * Jena's writer output mode
     */
    private int writerMode = 0;

    private int check = 0;

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

        //Controlla se attivare la rimozione degli errori e l'invio dell'output al writer Jena
        CheckBox cb = new CheckBox("Remove incorrect nodes & get " + Glossary.RDF_MODE[0]);
        grid.add(cb, 4, 3);
        cb.setSelected(true);
        cb.setOpacity(0);

        //Controlla il metodo di inserimento dell'elemento object negli statement in Jena
        //CheckBox cb1 = new CheckBox("Statement Objects as Resources");
        //grid.add(cb1, 4, 7);

        //Controlla la scelta del metodo di output del writer Jena. Se variato aggiorna automaticamente il campo writerMode
        ChoiceBox chBox = new javafx.scene.control.ChoiceBox<>(FXCollections.observableArrayList(Glossary.RdfWriteMode.RDF_XML,
                Glossary.RdfWriteMode.RDF_XML_ABBREV, Glossary.RdfWriteMode.N_TRIPLES, Glossary.RdfWriteMode.TURTLE));
        chBox.getSelectionModel().select(0);
        chBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            this.writerMode = new_val.intValue();
            cb.setText("Remove incorrect nodes & get " + Glossary.RDF_MODE[new_val.intValue()]);
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
                //cancella il contenuto del riquadro di output
                fred.setText("");

                /*
                verifica che il testo in input abbia le caratteristiche minime per essere processato
                ed avvia il parser, memorizzando in un Node il risultato
                 */
                if (amrTextField.getLength() > 3) {
                    String amr = amrTextField.getText();
                    Parser instance = Parser.getInstance();
                    while (amr.startsWith(" ")) {
                        amr = amr.substring(1);
                    }
                    if (!amr.startsWith("(")) {
                        amr = "(" + amr + ")";
                    }
                    Node result = instance.parse(amr);

                    //cancella il contenuto rel riquadro a dx in alto
                    removed.setText("");

                    /*
                    se il risultato dell'elaborazione del parser non è nullo avvia la visualizzazione dei risultati
                    a seconda dello stato delle chebox, in particolare se cb è selezionata manderà il risultato dell'elaborazione
                    del parser al controllo e rimozione degli errori.
                    Se è selezionata anche cb1 il writer inserirà gli elementi object (terzo elemento delle triple) come resource
                    e non come literal
                    Se cb non è selezionata per l'output verrà usato il metodo toString() implementato su Node, che prevede 
                    un output testuale non standard realizzato ad hoc per evidenziare la struttura dell'albero reso dall'eleborazione
                     */
                    if (result != null) {

                        if (result.getTreStatus() > 0) {
                            cb.setOpacity(100);
                            if (check == 0) {
                                cb.setSelected(false);
                                check=1;
                            }
                        } else {
                            cb.setOpacity(0);
                            cb.setSelected(true);
                            check=0;
                        }

                        if (cb.isSelected()) {
                            result = instance.check(result);
                            RdfWriter writer = RdfWriter.getWriter();
                            writer.setMode(Glossary.RdfWriteMode.values()[writerMode]);
                            fred.setText(writer.writeRdf(result));
                        } else {
                            fred.setText(result.toString());
                        }

                        /*
                        Quando sono stati rimossi dei nodi dall'albero, sono elencati nel riquadro in alto a destra
                         */
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

                        /*
                        Verifica della presenza di errori e generazione di un messagio personalizzato in base al numero degli stessi
                         */
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
