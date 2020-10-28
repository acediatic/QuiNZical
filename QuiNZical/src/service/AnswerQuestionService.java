package service;

import controller.PrimaryController;
import database.Clue;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * A Service class responsible for handling checking a user's answer.
 * This determines if the user is correct, and sets up the next screen
 * accordingly.
 * @author Adam and Osama.
 *
 */
public class AnswerQuestionService extends Service<Boolean> {
	private StringProperty usrAns = new SimpleStringProperty();
	private SimpleObjectProperty<Clue> clue = new SimpleObjectProperty<Clue>();
	private BooleanProperty practiceMode = new SimpleBooleanProperty();
	private BooleanProperty internationalMode = new SimpleBooleanProperty();
	
	 /**
	 * Sets up the propertys for this service.
	 * @param usrAns, the users answer
	 * @param clue, the clue that was attempted
	 * @param practiceMode, if the user was in the practice section
	 * @param internationalMode, if the user was in the international section.
	 */
	public final void setAns(String usrAns, Clue clue, boolean practiceMode, boolean internationalMode) {
		 this.usrAns.set(usrAns);
		 this.clue.set(clue);
		 this.practiceMode.set(practiceMode);
		 this.internationalMode.set(internationalMode);
     }

     /**
     * @return, the user's answer as a string.
     */
    public final String getUsrAns() {
         return this.usrAns.get();
     }
     
     /**
     * @return the clue that was attempted.
     */
    public final Clue getClue() {
         return this.clue.get();
     }
     
     /**
     * @return if the user was in the practice section
     */
    public final Boolean getPracticeMode() {
         return this.practiceMode.get();
     }

     /**
     * @return if the user was in the international section
     */
    public final Boolean getInternationalMode() {
    	 return this.internationalMode.get();
     }
     
     /**
     * Creates the task to be executed concurrently with the GUI thread,
     * setting the new scene accordingly using an FXMLService.
     */
    protected Task<Boolean> createTask() {
         final String usrAns = getUsrAns();
         final Clue clue = getClue();
         final Boolean _practiceMode = getPracticeMode();
         final Boolean _internationalMode = getInternationalMode();
         return new Task<Boolean>() {
             protected Boolean call() {
     			FXMLService.FXMLNames nextSceneFXML;
     			boolean correct = clue.check(usrAns);
     			if (correct) {
     				if(!_internationalMode) {
     					nextSceneFXML = FXMLService.FXMLNames.CORRECT;	
     				} else {
     					nextSceneFXML = FXMLService.FXMLNames.INTERNATIONALCORRECT;
     				}
     			} else {
     				if(!_internationalMode) {
     					nextSceneFXML = FXMLService.FXMLNames.INCORRECT;
 					} else {
 						nextSceneFXML = FXMLService.FXMLNames.INTERNATIONALINCORRECT;
 					}
     			}
     			if (!_practiceMode) {
     				PrimaryController.getInstance().addNewScene(nextSceneFXML);
     			}
     			
     			return correct;
             };
         };
     }
}