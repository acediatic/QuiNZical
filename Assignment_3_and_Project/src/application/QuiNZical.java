package application;

import java.io.IOException;

import controller.PrimaryController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import service.FXMLService;


public class QuiNZical extends Application {
	private Stage _currentStage;
	@SuppressWarnings("unused")
	private PrimaryController gmc = PrimaryController.getInstance(); //initialises gmc.
	
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
			PrimaryController.app = this;
			addNewScene(FXMLService.FXMLNames.HOMESCREEN);			
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
			//Scene currentScene = _currentStage.getScene();
			_currentStage.setScene(scene);
			_currentStage.setWidth(700);
			_currentStage.setHeight(700);
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
		            	
		            	_currentStage.setMinHeight(700);
		    			_currentStage.setMinWidth(700);
		    			
		    			_currentStage.setWidth(700);
		    			_currentStage.setHeight(700);
		    			
		            	_currentStage.show();
		            }
		        });
			 
			 service.start();
		}
		
		public void showResetAlert() {
			Alert resetAlert = new Alert(AlertType.CONFIRMATION);
			resetAlert.setTitle("WARNING!");
			resetAlert.setHeaderText("Reset?");
			
			resetAlert.showAndWait().ifPresent(response -> {
			    if (response == ButtonType.OK) {
			    	Thread th = new Thread(new Task<Void>() {
			        	protected Void call() throws IOException {
			        		try {
								PrimaryController.getInstance().reset();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
			            }
		        	});
		            th.start();
			    }
			});
		}
}
