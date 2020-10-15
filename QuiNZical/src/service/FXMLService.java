package service;

import java.io.IOException;
import java.net.URL;

import controller.PrimaryController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class FXMLService extends Service<Scene> {
	 private StringProperty fxml = new SimpleStringProperty();
	
	 public final void setFXML(FXMLNames value) {
		 fxml.set(value.toString());
     }

     public final String getFXML() {
         return fxml.get();
     }

     public final StringProperty fxmlProperty() {
        return fxml;
     }

     protected Task<Scene> createTask() {
         final String _fxml = getFXML();
         return new Task<Scene>() {
             protected Scene call() throws IOException {
            	 FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/"+_fxml));
     			 Scene scene = loader.load();		
     			 PrimaryController.getInstance().currentController = loader.getController();
     			 PrimaryController.getInstance().currentController.init();
     			 return scene;
             }
         };
     }
     
     public enum FXMLNames {
    		HOMESCREEN("homeScreen.fxml"), HOMEDRAWER("homeDrawer.fxml"), ASKQUESTION("askQuestionScene.fxml"), CORRECT("correctAnswerScene.fxml"),
    		INCORRECT("incorrectAnswerScene.fxml"), QUESTIONBOARD("questionBoard.fxml"), WINNINGS("winningsScene.fxml"), GAMECOMPLETE("rewardScene.fxml"),
    		PRACTICESELECTOR("practiceCatSelector.fxml");

    		private final String _location;
    		
    		private FXMLNames(String location) {
    			_location = location;
    		}
    		
    		@Override
    		public String toString() {
    			return _location;
    		}
    		
    		public URL toURL() {
    			return getClass().getResource("/application/fxml/"+ this.toString());
    		}
    	}
 }