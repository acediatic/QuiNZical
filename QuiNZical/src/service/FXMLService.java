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

/**
 * A service to load new scenes from FXML files.
 * Contains an enum of the possible scenes for convenience.
 * @author Adam and Osama
 *
 */
public class FXMLService extends Service<Scene> {
	 private StringProperty fxml = new SimpleStringProperty();
	
	 /**
	 * Sets the fxml to be added to the app, 
	 * stored as a string representation
	 * @param value, the fxml to be added to the app
	 */
	public final void setFXML(FXMLNames value) {
		 fxml.set(value.toString());
     }

     /**
      * Gets the fxml to be added to the app
     * @return the string representation of the fxml to be added to the app
     */
    public final String getFXML() {
         return fxml.get();
     }

     /**
     * Loads the FXML from memory, and returns the scene when finished.
     */
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
     
     /**
     * An Enum class representing all the possible scenes, with their fxml file names.
     * Also provides some utility methods for converting these fxml to the right form
     * including fetching their url and string location.
     * @author Adam and Osama
     */
    public enum FXMLNames {
    		HOMESCREEN("homeScreen.fxml"), HOMEDRAWER("homeDrawer.fxml"), ASKQUESTION("askQuestionScene.fxml"), CORRECT("correctAnswerScene.fxml"),
    		INCORRECT("incorrectAnswerScene.fxml"), QUESTIONBOARD("questionBoard.fxml"), WINNINGS("winningsScene.fxml"), GAMECOMPLETE("rewardScene.fxml"),
    		PRACTICESELECTOR("practiceCatSelector.fxml"), INTERNATIONALBOARD("internationalBoard.fxml"), INTERNATIONALQUESTION("internationalAskQuestion.fxml"), 
    		INTERNATIONALFINISHED("internationalRewardScene.fxml"), INTERNATIONALCORRECT("correctAnswerSceneInternational.fxml"), INTERNATIONALINCORRECT("incorrectAnswerSceneInternational.fxml"),
    		LEADERBOARD("leaderboard.fxml"), CHOOSECATEGORIES("chooseCats.fxml");

    		private final String _location;
    		
    		/**
    		 * Initalises the FXML file at the specified location
    		 * @param location, the name (location) of the fxml file.
    		 */
    		private FXMLNames(String location) {
    			_location = location;
    		}
    		
    		/**
    		 * A string representation of the object's location in the FXML package.
    		 */
    		@Override
    		public String toString() {
    			return _location;
    		}
    		
    		/**
    		 * @return the java url to the specified fxml.
    		 */
    		public URL toURL() {
    			return getClass().getResource("/application/fxml/"+ this.toString());
    		}
    	}
 }