package application;

import java.net.URL;

import database.Clue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import static java.lang.Math.pow;

public class tempTester extends Application {

	private Stage _primaryStage;
	private double _currentFontSize = 1.8;
	private QuestionBoardController qbc;
	
	
	@Override
	public void start(Stage primaryStage) {
		_primaryStage = primaryStage;
		
		_primaryStage.setTitle("My Cool Video Player");
		try {
			BorderPane root = new BorderPane();

			Scene scene = new Scene(root, 1000, 700);

			
			//Finds the FXML file and loads the scene from it.
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("askQuestionScene.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("questionBoard.fxml"));
			Scene incorrectAnswerScene = loader.load();			
			qbc = loader.getController();
			
			
			//IncorrectAnswerController controller = loader.getController();
			//controller.initData(_primaryStage);
			_primaryStage.setScene(incorrectAnswerScene);
			
			Clue clue = new Clue("500", "Is this working?", "Yes!");
			//controller.initData(clue);
			

			_primaryStage.setMinHeight(600);
			_primaryStage.setMinWidth(600);
			
			_primaryStage.minHeightProperty().bind(_primaryStage.widthProperty().multiply(1));
			_primaryStage.maxHeightProperty().bind(_primaryStage.widthProperty().multiply(1));
			
			_primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
				if(_primaryStage.getScene() != null) {
					qbc.updateText(_primaryStage, oldVal, newVal);
				}
			});
			
			_primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * The main method for running the app.
	 * @param args
	 */
		public static void main(String[] args) {
			launch(args);
		}

		public void setScene(Scene scene) {
			_primaryStage.setScene(scene);
			_primaryStage.show();
		}

}
