package application;

import java.io.IOException;

import controller.PrimaryController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.progressFiles.RingProgressIndicator;
import service.FXMLService;
import service.LoadControllerAndModelService;


public class QuiNZical extends Application {
	private Stage _currentStage;
	private PrimaryController gmc;
	
	/**
	 * The main method for running the app.
	 * @param args
	 */
		public static void main(String[] args) {
			launch(args);
		}
	
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
		_currentStage.setResizable(false); // As per Nasser's recommendations
		
		setLoadScreen();
		
		LoadControllerAndModelService service = new LoadControllerAndModelService();
		service.setApp(this);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override 
            public void handle(WorkerStateEvent t) {
            	try {
            		gmc = (PrimaryController) t.getSource().getValue();
            			setLoadScreen();
            			Thread th = new Thread(
            					new Task<Void>() {
            				         @Override protected Void call() throws Exception {
            				        	 PrimaryController.getInstance().setupGame();
            							return null;
            				         }

            				         @Override protected void succeeded() {
            				        	 PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);	
            				         }
            					});
            			th.start();
            	} catch(Exception e) {
        			e.printStackTrace();
        		}
            }
		});	
		service.start();
	}
	
	@Override
	public void stop(){
		if (gmc != null) {
			gmc.stopAudio();
		}

	}

		public void setScene(Scene scene) {
			_currentStage.setScene(scene);
			
        	_currentStage.setMinHeight(700);
			_currentStage.setMinWidth(700);
			
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
}
