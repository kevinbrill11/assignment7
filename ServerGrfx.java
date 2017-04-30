package assignment7;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ServerGrfx extends Application{
	public static void initiate(String[] args){
		ServerGrfx.launch(args);
	}
	public void start(Stage primaryStage) throws Exception {
		GridPane gridpane = new GridPane();
		
    	Button btn = new Button();    
        gridpane.add(btn, 0, 0);
       
        primaryStage.setTitle("DankBook Server");
        btn.setText("End Server");
 
        btn.setOnAction(new EventHandler<ActionEvent>() {	 
        	@Override
            public void handle(ActionEvent event) {
        		System.exit(0);
            }
       });
            
       primaryStage.setScene(new Scene(gridpane, 100, 100));
       primaryStage.show();
	}
}
