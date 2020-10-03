package service;

import application.GameMainController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;

public class AnswerQuestionService extends Service<Boolean> {
	private StringProperty _usrAns = new SimpleStringProperty();
	private StringProperty _realAns = new SimpleStringProperty();
	
	 public final void setAns(String usrAns, String realAns) {
		 _usrAns.set(usrAns);
		 _realAns.set(realAns);
     }

     public final String getUsrAns() {
         return _usrAns.get();
     }
     
     public final String getRealAns() {
         return _realAns.get();
     }

     protected Task<Boolean> createTask() {
         final String usrAns = getUsrAns();
         final String realAns = getRealAns();
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
     			GameMainController.app.addNewScene(nextSceneFXML);
     			
     			return correct;
         };
     };
     }
}