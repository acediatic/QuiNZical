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

public class AnswerQuestionService extends Service<Boolean> {
	private StringProperty _usrAns = new SimpleStringProperty();
	private SimpleObjectProperty<Clue> _clue = new SimpleObjectProperty<Clue>();
	private BooleanProperty _practiceMode = new SimpleBooleanProperty();
	private BooleanProperty _internationalMode = new SimpleBooleanProperty();
	
	 public final void setAns(String usrAns, Clue clue, boolean practiceMode, boolean internationalMode) {
		 _usrAns.set(usrAns);
		 _clue.set(clue);
		 _practiceMode.set(practiceMode);
		 _internationalMode.set(internationalMode);
     }

     public final String getUsrAns() {
         return _usrAns.get();
     }
     
     public final Clue getClue() {
         return _clue.get();
     }
     
     public final Boolean getPracticeMode() {
         return _practiceMode.get();
     }

     public final Boolean getInternationalMode() {
    	 return _internationalMode.get();
     }
     
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