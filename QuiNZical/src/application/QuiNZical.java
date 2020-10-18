package application;

import java.io.IOException;

import controller.PrimaryController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.progressFiles.RingProgressIndicator;
import service.FXMLService;
import service.LoadControllerAndModelService;


public class QuiNZical extends Application {
	private Stage _currentStage;
	private PrimaryController gmc;
	private int y = 0;
	private int x = 0;
	
	/**
	 * getStage returns the stage to other classes, to allow them to set the stage.
	 * @return currentStage
	 */
	public Stage getStage() {
        return _currentStage;
    }
	
	@Override
	public void start(Stage stage) throws IOException {
		_currentStage = stage;
		_currentStage.setTitle("QuiNZical");
		setLoadScreen();
		
		LoadControllerAndModelService service = new LoadControllerAndModelService();
		service.setApp(this);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override 
            public void handle(WorkerStateEvent t) {
            	try {
            		gmc = (PrimaryController) t.getSource().getValue();
            		addNewScene(FXMLService.FXMLNames.HOMESCREEN);	
        			
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
            }
		});	
		service.start();
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
		
		public void setLoadScreen() {
			RingProgressIndicator timerVisual = new RingProgressIndicator();
			timerVisual.setRingWidth(200);
			timerVisual.makeIndeterminate();
			
			Scene loadScene = new Scene(timerVisual, 700, 700);
			setScene(loadScene);
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
		    			
		    			_currentStage.setResizable(false);
		    			
		            	_currentStage.show();
		            }
		        });
			 
			 service.start();
		}
}
