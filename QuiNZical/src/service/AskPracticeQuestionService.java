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

/**
 * A Service class responsible for preparing the clue to be displayed to
 * the user, in the practice section.
 * @author Adam and Osama.
 *
 */
public class AskPracticeQuestionService extends Service<Void> {
	private ObjectProperty<Category> chosenCat = new SimpleObjectProperty<Category>();
	private IntegerProperty attempts = new SimpleIntegerProperty();
	
	 /**
	 * @param cat, sets the category which the clue is from.
	 */
	public final void setCategory(Category cat) {
		chosenCat.set(cat);
	 }
	 
	 /**
	 * @return, retrieves the category the clue is from
	 */
	public final Category getCategory() {
			return chosenCat.get();
		 }
	 
	 /**
	 * @param noAttempts, sets the number of attempts the user has had
	 * at the question. 
	 */
	public final void setAttempts(Integer noAttempts) {
			attempts.set(noAttempts);
		 }
		 
	/**
	 * @return the number of attempts that the user has had at the question
	 */
	public final Integer getAttempts() {
		return attempts.get();
	}
	
	/**
	 * Creates the task to be run concurrent to the GUI thread, which
	 * sets the question.
	 */
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
			            	ac.initClue(chosenCat, randomClue, true, false); //true for practice section, false for not international
			            	
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