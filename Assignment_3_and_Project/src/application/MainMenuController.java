package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenuController {
	
	Button quitButton = new Button();
	Button gameButton = new Button();
	Button practiceButton = new Button();
	
	 @FXML
	 public void quitButtonAction(ActionEvent event) {
		 System.exit(0);
	 }
	 
	 @FXML
	 public void gameButtonAction(ActionEvent event) {
		 try {
				BorderPane game = (BorderPane)FXMLLoader.load(getClass().getResource("Game.fxml"));
				Scene scene = new Scene(game,700,700);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Main.getStage().setScene(scene);
				Main.getStage().show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	 }
}
