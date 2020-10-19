package service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import controller.PrimaryController;
import controller.sceneControllers.AskingController;
import database.Category;
import database.Clue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class AskPracticeQuestionService extends Service<Void> {
	private ObjectProperty<Category> chosenCat = new SimpleObjectProperty<Category>();
	private IntegerProperty attempts = new SimpleIntegerProperty();
	
	 public final void setCategory(Category cat) {
		chosenCat.set(cat);
	 }
	 
	 public final Category getCategory() {
			return chosenCat.get();
		 }
	 
	 public final void setAttempts(Integer noAttempts) {
			attempts.set(noAttempts);
		 }
		 
		 public final Integer getAttempts() {
				return attempts.get();
			 }
	
	@Override
	protected Task<Void> createTask() {
		
		return new Task<Void>() {
			protected Void call() throws IOException {			
				Category chosenCat = getCategory();
				List<Clue> possibleClues = chosenCat.getAllUnasnweredClues();
				Clue randomClue = possibleClues.get(new Random().nextInt(possibleClues.size()));
							
				FXMLService service = new FXMLService();
		         service.setFXML(FXMLService.FXMLNames.ASKQUESTION);
				 service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			            @Override 
			            public void handle(WorkerStateEvent t) {
			            	AskingController ac = (AskingController) PrimaryController.getInstance().currentController;
			            	ac.initClue(chosenCat, randomClue, true, false);
			            	
			            	Scene scene = (Scene) t.getSource().getValue();
			            	PrimaryController.getInstance().setScene(scene);
			            }
			        });
				 service.start();
				 return null;
			};
		};
	}
}