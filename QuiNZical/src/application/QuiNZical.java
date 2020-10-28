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

/**
 * The QuiNZical application main class, responsible for running the game
 * and generating the stage. Changes to the views are also processed here.
 * @author Adam and Osama
 *
 */
public class QuiNZical extends Application {
	private Stage currentStage;
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
        return currentStage;
    }
	
	@Override
	public void start(Stage stage) throws IOException {
		currentStage = stage;
		currentStage.setTitle("QuiNZical");
		currentStage.setResizable(false); // As per Nasser's recommendations
		
		setLoadScreen();
		
		LoadControllerAndModelService service = new LoadControllerAndModelService();
		service.setApp(this);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override 
            public void handle(WorkerStateEvent t) {
            	try {
            		gmc = (PrimaryController) t.getSource().getValue();
            			setLoadScreen(); // sets the load screen while the game is setup
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
	
	
	/**
	 * Utility method to stop audio if it's still playing on game exit.
	 */
	@Override
	public void stop(){
		if (gmc != null) {
			gmc.stopAudio();
		}

	}

	/**
	 * Sets the input scene on the stage
	 * @param scene to be added to the stage.
	 */
	public void setScene(Scene scene) {
		currentStage.setScene(scene);
		
    	currentStage.setMinHeight(700);
		currentStage.setMinWidth(700);
		
		currentStage.setWidth(700);
		currentStage.setHeight(700);
		
		currentStage.show();
	}
	
	
	/**
	 * Adds the load screen to the display while game elements are loading.
	 */
	public void setLoadScreen() {
		RingProgressIndicator timerVisual = new RingProgressIndicator();
		timerVisual.setRingWidth(200);
		timerVisual.makeIndeterminate();
		
		Scene loadScene = new Scene(timerVisual, 700, 700);
		setScene(loadScene);
	}
}
