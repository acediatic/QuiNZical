package controller.sceneControllers;

import controller.PrimaryController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import service.UpdateTextService;

/**
 * A inherited abstract class which guarantees all classes have the ability to update
 * text, and initialise themselves if necessary after the controller has been loaded
 * by JavaFX.fxml
 * @author Adam and Osama
 *
 */
public abstract class Controller {
	public abstract void updateTextIndividual();
	
	/**
	 * Updates the text size of the screen when the window changes size.
	 * @param oldVal, the old update value in px
	 * @param newVal, the new update value in px
	 */
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
	
	/**
	 * A utility method which sets up the controller after the 
	 * @FXML variables have been initalised, implemented by each controller.
	 */
	public abstract void init();
}
