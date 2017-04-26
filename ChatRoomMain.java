package assignment7;

import java.io.IOException;

import assignment7.loginView.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatRoomMain extends Application {
	private Stage primaryStage, secondStage;
	private BorderPane rootLayout;
	private ObservableList<Conversation> conversations = FXCollections.observableArrayList();
	public ChatRoomMain(){
		
	}
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ChatRoom");
		initRootLayout();
		showMainView();
	}
	public ObservableList<Conversation> getConversations(){
		return conversations;
	}
	
	
	 /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ChatRoomMain.class.getResource("loginView/RootLayout.fxml"));
            System.out.println(loader.getLocation());
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private void showMainView(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ChatRoomMain.class.getResource("loginView/LoginScreen.fxml"));
			System.out.println(loader.getLocation());
			AnchorPane logIn = (AnchorPane) loader.load();
			
			rootLayout.setCenter(logIn);
			LoginScreenController controller = loader.getController();
			controller.setStage(primaryStage);
			ChatClient client = new ChatClient();
			controller.setClient(client);
			
			secondStage = new Stage();
			secondStage.setTitle("Chat Room");
			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(ChatRoomMain.class.getResource("loginView/ChatClient.fxml"));
			System.out.println(loader2.getLocation());
			AnchorPane chatClient = (AnchorPane) loader2.load();
			ChatClientController controller2 = loader2.getController();
			controller2.setStage(secondStage);
			Scene scene2 = new Scene(chatClient);
			controller2.setClient(client);
			secondStage.setScene(scene2);
			secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              client.logOff();
		          }
		      }); 
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              client.logOff();
		          }
		      }); 
			controller.setSecondStage(secondStage);
			client.setController(controller2);
			
			controller2.setThirdStage(primaryStage);
			new Thread(client).start();
			
		}
		catch(IOException e) {
            e.printStackTrace();
        }
	}
	public static void main(String[] args) {
		launch(args);
		
		
	}
}
