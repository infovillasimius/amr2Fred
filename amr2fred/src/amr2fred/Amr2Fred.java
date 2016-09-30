/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("amr2fred");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 1000, 400);
        primaryStage.setScene(scene);
        
        
        Text scenetitle = new Text("Welcome to amr2fred");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 3, 1);

        Label amr = new Label("Type amr:");
        grid.add(amr, 0, 1);

        TextField amrTextField = new TextField();
        amrTextField.setPrefColumnCount(80);
        grid.add(amrTextField, 1, 1);

        Label fredLabel = new Label("get (something about) Fred tree: ");
        grid.add(fredLabel, 0, 2,2,1);

        Text fred = new Text();
        grid.add(fred, 0,3,3, 2);
        
        
        Button btn = new Button();
        btn.setText("AMR to Fred");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 
                if(amrTextField.getLength()>3){
                    String amr = amrTextField.getText();
                    Parser instance = Parser.getInstance();
                    Node result = instance.parse(amr);
                    if (result!=null){
                        //fred.setText(result.toString());
                        fred.setText(result.toString());
                    } else {
                        fred.setText("Sintassi non corretta");
                    }
                } else if(amrTextField.getLength()==0){ 
                    fred.setText("Nessun testo inserito");
                } else {
                    fred.setText("Sintassi non corretta");      
                }
            }
        });
        
        grid.add(btn, 0, 6,3,1);
        
        primaryStage.show();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
