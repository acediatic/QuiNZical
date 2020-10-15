package service;

import controller.PrimaryController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AnswerQuestionService extends Service<Boolean> {
	private StringProperty _usrAns = new SimpleStringProperty();
	private StringProperty _realAns = new SimpleStringProperty();
	private BooleanProperty _practiceMode = new SimpleBooleanProperty();
	
	 public final void setAns(String usrAns, String realAns, boolean practiceMode) {
		 _usrAns.set(usrAns);
		 _realAns.set(realAns);
		 _practiceMode.set(practiceMode);
     }

     public final String getUsrAns() {
         return _usrAns.get();
     }
     
     public final String getRealAns() {
         return _realAns.get();
     }
     
     public final Boolean getPracticeMode() {
         return _practiceMode.get();
     }

     protected Task<Boolean> createTask() {
         final String usrAns = getUsrAns();
         final String realAns = getRealAns();
         final Boolean _practiceMode = getPracticeMode();
         return new Task<Boolean>() {
             protected Boolean call() {
            	String usrAnsStripped = usrAns.strip();
     			String actualAns = realAns.strip();
     			boolean correct = false;
     			FXMLService.FXMLNames nextSceneFXML;
     			if (usrAnsStripped.equalsIgnoreCase(actualAns)) {
     				correct = true;
     				nextSceneFXML = FXMLService.FXMLNames.CORRECT;				
     			} else {
     				nextSceneFXML = FXMLService.FXMLNames.INCORRECT;
     			}
     			if (!_practiceMode) {
     				PrimaryController.app.addNewScene(nextSceneFXML);
     			}
     			
     			return correct;
             };
         };
     }
}