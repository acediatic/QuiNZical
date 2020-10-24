package service;

import application.QuiNZical;
import controller.PrimaryController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoadControllerAndModelService extends Service<PrimaryController> {
	 private SimpleObjectProperty<QuiNZical> _app = new SimpleObjectProperty<QuiNZical>();
	
	 public final void setApp(QuiNZical app) {
		 _app.set(app);
     }

     public final QuiNZical getApp() {
         return _app.get();
     }

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