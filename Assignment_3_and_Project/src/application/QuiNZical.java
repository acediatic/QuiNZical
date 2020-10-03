package application;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.stage.Stage;
import service.FXMLService;


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
			addNewScene(FXMLService.FXMLNames.HOMESCREEN);
			
			_currentStage.setMinHeight(700);
			_currentStage.setMinWidth(700);
			
			_currentStage.minHeightProperty().bind(_currentStage.widthProperty().multiply(1));
			_currentStage.maxHeightProperty().bind(_currentStage.widthProperty().multiply(1));
			
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
		
		public void addNewScene(FXMLService.FXMLNames fxml) {
			 FXMLService service = new FXMLService();
	         service.setFXML(fxml);
			 service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

		            @Override 
		            public void handle(WorkerStateEvent t) {
		            	Scene scene = (Scene) t.getSource().getValue();
		            	_currentStage.setScene(scene);
		            	_currentStage.show();
		            }
		        });
			 
			 service.start();
		}
}
