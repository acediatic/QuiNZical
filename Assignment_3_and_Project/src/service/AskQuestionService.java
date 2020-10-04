package service;

import java.io.IOException;

import controller.PrimaryController;
import controller.sceneControllers.AskingController;
import database.Category;
import database.Clue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class AskQuestionService extends Service<Void> {
	private IntegerProperty nodeCol = new SimpleIntegerProperty();
	private IntegerProperty nodeRow = new SimpleIntegerProperty();
	
	 public final void setCatAndClue(Integer col, Integer row) {
		// null from gridpane get child means child has not been changed from default
		// col, which is col 0.
		 if (col == null) {
				col = 0;
			}
		 nodeCol.set(col);
		 nodeRow.set(row);
	 }
	
	 public final Integer getCol() {
	     return nodeCol.get();
	 }
	 
	 public final Integer getRow() {
	     return nodeRow.get();
	 }
	
	@Override
	protected Task<Void> createTask() {
		final Integer row = getRow();
		final Integer col = getCol(); 
		
		return new Task<Void>() {
			protected Void call() throws IOException {			
				Category chosenCat = PrimaryController.getInstance().getCategory(col);
				Clue chosenClue = chosenCat.getClue(row);
				
				FXMLService service = new FXMLService();
		         service.setFXML(FXMLService.FXMLNames.ASKQUESTION);
				 service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			            @Override 
			            public void handle(WorkerStateEvent t) {
			            	AskingController ac = (AskingController) PrimaryController.currentController;
			            	ac.initClue(chosenCat, chosenClue, false);
			            	
			            	Scene scene = (Scene) t.getSource().getValue();
			            	PrimaryController.app.setScene(scene);
			            }
			        });
				 service.start();
				 return null;
			};
		};
	}


}
