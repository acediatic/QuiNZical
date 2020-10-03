package application;

import java.io.IOException;

import controller.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

public class AnswerController implements Controller {
	
	public void updateText(Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			GameMainController._currentFontSize = GameMainController._currentFontSize * ratio;
			
			Node node = GameMainController.app.getStage().getScene().getRoot();
			
			node.setStyle("-fx-font-size: " + GameMainController._currentFontSize + "em");
		}
	}
	
	@FXML
	private void goHome() {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
		Scene scene = loader.load();
		GameMainController.currentController = (loader.getController());
		GameMainController.currentController.init();
		
		Platform.runLater(new Runnable() {
            @Override public void run() {
               GameMainController.app.setScene(scene); 
            }
        });
	
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}
	
	@FXML 
	private void goQuestionBoard() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("questionBoard.fxml"));
			Scene scene = loader.load();
			GameMainController.currentController = (loader.getController());
			
			Platform.runLater(new Runnable() {
	            @Override public void run() {
	               GameMainController.app.setScene(scene); 
	            }
	        });
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}
	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}