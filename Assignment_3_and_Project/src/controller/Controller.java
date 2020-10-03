package controller;

import application.GameMainController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import service.UpdateTextService;

public abstract class Controller {
	public abstract void updateTextIndividual();
	
	public final void updateText(Number oldVal, Number newVal) {;
		UpdateTextService service = new UpdateTextService();
	    service.setDoubles(oldVal, newVal);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	
	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	Parent root = GameMainController.app.getStage().getScene().getRoot();
	    			root.setStyle("-fx-font-size: " + GameMainController._currentFontSize + "em");
	    			updateTextIndividual();
	            }
	        }); 
		service.start();	
	}
	
	public abstract void init();
}
