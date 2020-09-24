package controller;

import database.Clue;
import javafx.fxml.FXMLLoader;

public class AskClue {
	private final Clue _clue;
	private int _speed;
	
	public AskClue(Clue clue, int speed) {
		_clue = clue;
		_speed = speed;
	}

	public ask() {
		
		
		
		
		
		String readQuestion = "chmod +x ./ttsGen.sh &&  ./ttsGen.sh " + _clue.showClue() + " " + _speed;
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", readQuestion);
		
		
		
		update(_clue, _clue.showCategory, )
		
		
		
	}
	
	
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
