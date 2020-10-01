package controller;

import application.Quizical;
import database.Clue;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class AskClue {
	private final Clue _clue;
	private int _speed;
	private Quizical _app; 
	
	public AskClue(Quizical app, Clue clue) {
		_clue = clue;
		_app = app;
	}

	public void ask() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("askQuestionScene.fxml"));
		Scene askQuestionScene = loader.load();			
		_app.setController(loader.getController());
		
		
		
		
		String readQuestion = "chmod +x ./ttsGen.sh &&  ./ttsGen.sh " + _clue.showClue();
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", readQuestion);
		
		Platform.runLater(_app.setScene(scene);
		
		
		update(_clue, _clue.showCategory(), correct)
		
		
		
	}
	
	
    /**
     * Initializes the root layout.
     */
    }
}
