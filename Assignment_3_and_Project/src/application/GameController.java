package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameController {
	
	Label category1 = new Label();
	Label category2 = new Label();
	Label category3 = new Label();
	Label category4 = new Label();
	Label category5 = new Label();
	Button category1clue1 = new Button();
	Button category1clue2 = new Button();
	Button category1clue3 = new Button();
	Button category1clue4 = new Button();
	Button category1clue5 = new Button();
	Button category2clue1 = new Button();
	Button category2clue2 = new Button();
	Button category2clue3 = new Button();
	Button category2clue4 = new Button();
	Button category2clue5 = new Button();
	Button category3clue1 = new Button();
	Button category3clue2 = new Button();
	Button category3clue3 = new Button();
	Button category3clue4 = new Button();
	Button category3clue5 = new Button();
	Button category4clue1 = new Button();
	Button category4clue2 = new Button();
	Button category4clue3 = new Button();
	Button category4clue4 = new Button();
	Button category4clue5 = new Button();
	Button category5clue1 = new Button();
	Button category5clue2 = new Button();
	Button category5clue3 = new Button();
	Button category5clue4 = new Button();
	Button category5clue5 = new Button();
	
	@FXML
	 public void selectClueButton(ActionEvent event) {
		 try {
				BorderPane game = (BorderPane)FXMLLoader.load(getClass().getResource("AnswerDialog.fxml"));
				Scene scene = new Scene(game,700,700);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Main.getStage().setScene(scene);
				Main.getStage().show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	 }

}
