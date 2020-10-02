package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class QuiNZical extends Application {
	private Stage _currentStage;
	private GameMainController gmc = GameMainController.getInstance(); //initialises gmc.
	
	/**
	 * getStage returns the stage to other classes, to allow them to set the stage.
	 * @return currentStage
	 */
	public Stage getStage() {
        return _currentStage;
    }
	
	@Override
	public void start(Stage stage) {
		_currentStage = stage;
		_currentStage.setTitle("QuiNZical");
		try {
			GameMainController.app = this;
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
			Scene scene = loader.load();			
			
			GameMainController.currentController = loader.getController();
			GameMainController.currentController.init();
			
			_currentStage.setScene(scene);
			
			_currentStage.setMinHeight(800);
			_currentStage.setMinWidth(800);
			
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
			Scene currentScene = _currentStage.getScene();
			_currentStage.setScene(scene);
			_currentStage.setWidth(currentScene.getWidth());
			_currentStage.setHeight(currentScene.getHeight());
			_currentStage.show();
		}
}
