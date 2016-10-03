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
import javafx.scene.control.TextArea;
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
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 1000, 600);
        primaryStage.setScene(scene);
        
        
        Text scenetitle = new Text("Welcome to amr2fred");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 3, 1);

        Label amr = new Label("Type amr:");
        grid.add(amr, 0, 1);

        TextArea amrTextField = new TextArea();
        amrTextField.setPrefColumnCount(80);
        amrTextField.setPrefRowCount(6);
        grid.add(amrTextField, 1, 1);

        Label fredLabel = new Label("Click the button to get (something about) Fred tree: ");
        grid.add(fredLabel, 0, 2,2,1);

        TextArea fred = new TextArea();
        fred.setEditable(false);
        fred.setPrefRowCount(16);
        grid.add(fred, 0,4,3, 2);
        
        Text err = new Text();
        err.setFont(Font.font("Tahoma", FontWeight.BOLD, 10));
        grid.add(err, 0, 7, 3, 1);
        
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
                        if(result.getTreStatus()==1){
                            err.setText("Warning! Something went wrong: one node is not ok!");
                        } else if(result.getTreStatus()>0){
                            err.setText("Warning! Something went wrong: "+result.getTreStatus()+" nodes are not ok!");
                        } else {
                            err.setText(" ");
                        }
                    } else {
                        fred.setText("Sintax error");
                    }
                } else if(amrTextField.getLength()==0){ 
                    fred.setText("No text");
                } else {
                    fred.setText("Sintax error");      
                }
                
                
                
                
                
            }
        });
        
        grid.add(btn, 0, 3,3,1);
        
        primaryStage.show();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
