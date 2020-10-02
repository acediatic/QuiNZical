package application;

import database.Clue;
import database.ModuleModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class QuiNZical extends Application {
	@Override
	public void start(Stage primaryStage) {
		_currentStage = primaryStage;
		
		_currentStage.setTitle("My Cool Video Player");
		try {
			BorderPane root = new BorderPane();

			Scene scene = new Scene(root, 1000, 700);

			
			//Finds the FXML file and loads the scene from it.
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("askQuestionScene.fxml"));
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("questionBoard.fxml"));
			getClass().getResource("/unibo/lsb/res/dice.jpg");
			Scene askQuestionScene = loader.load();			
			setController(loader.getController());
			
			Controller controller = loader.getController();
			controller.initData(_currentStage);
			_currentStage.setScene(askQuestionScene);
			
			Clue clue = new Clue("500", "Is this working?", "Yes!");
			//controller.initData(clue);
			

			_currentStage.setMinHeight(600);
			_currentStage.setMinWidth(600);
			
			_currentStage.minHeightProperty().bind(_currentStage.widthProperty().multiply(1));
			_currentStage.maxHeightProperty().bind(_currentStage.widthProperty().multiply(1));
			
			
			
			_currentStage.show();
			
			

			
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
			_currentStage.setScene(scene);
			_currentStage.show();
		}

		public void setController(Controller controller) {
			_currentController = controller;
		}
}
