package controller.sceneControllers;

import controller.PrimaryController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import service.UpdateTextService;

public abstract class Controller {
	public abstract void updateTextIndividual();
	
	public final void updateText(Number oldVal, Number newVal) {;
		UpdateTextService service = new UpdateTextService();
	    service.setDoubles(oldVal, newVal);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	PrimaryController.getInstance().updateRoot();
	    			updateTextIndividual();
	            }
	        }); 
		service.start();	
	}
	
	public abstract void init();
}
