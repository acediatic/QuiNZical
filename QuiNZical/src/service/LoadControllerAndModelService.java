package service;

import application.QuiNZical;
import controller.PrimaryController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Loads the Primary Controller and then the 
 * model from Memory
 * @author Adam and Osama.
 *
 */
public class LoadControllerAndModelService extends Service<PrimaryController> {
	 private SimpleObjectProperty<QuiNZical> app = new SimpleObjectProperty<QuiNZical>();
	
	 /**
	 * Sets the JavaFX app used.
	 * @param app, the javafx app
	 */
	public final void setApp(QuiNZical app) {
		 this.app.set(app);
     }
	
	 /**
	 * Gets the JavaFX app used.
	 * @return the javafx app
	 */
     public final QuiNZical getApp() {
         return this.app.get();
     }

     /**
     * Creates the task to be run. This creates an instance of the
     * primary controller, sets the app for this controller, the 
     * stage listener for resizing, and the app icon.
     */
    protected Task<PrimaryController> createTask() {
         final QuiNZical app = getApp();
         return new Task<PrimaryController>() {
             protected PrimaryController call() {
         		PrimaryController pc = PrimaryController.getInstance(); //initialises gmc.
         		pc.setApp(app);
         		pc.setStageListener();
         		pc.setImage();
            	return pc;
	         };
	     };
     }
}