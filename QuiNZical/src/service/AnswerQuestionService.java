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
	private BooleanProperty _internationalMode = new SimpleBooleanProperty();
	
	 public final void setAns(String usrAns, String realAns, boolean practiceMode, boolean internationalMode) {
		 _usrAns.set(usrAns);
		 _realAns.set(realAns);
		 _practiceMode.set(practiceMode);
		 _internationalMode.set(internationalMode);
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

     public final Boolean getInternationalMode() {
    	 return _internationalMode.get();
     }
     
     protected Task<Boolean> createTask() {
         final String usrAns = getUsrAns();
         final String realAns = getRealAns();
         final Boolean _practiceMode = getPracticeMode();
         final Boolean _internationalMode = getInternationalMode();
         return new Task<Boolean>() {
             protected Boolean call() {
            	String usrAnsStripped = usrAns.strip();
     			String actualAns = realAns.strip();
     			boolean correct = false;
     			FXMLService.FXMLNames nextSceneFXML;
     			if (usrAnsStripped.equalsIgnoreCase(actualAns)) {
     				correct = true;
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