package application;

import java.io.IOException;
import java.util.Optional;
import controller.Controller;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import service.FXMLService;

public class DrawerController extends Controller {
	@FXML
	private void initialize() {	}
	
	@FXML
	private void startGame(ActionEvent e) {	
		GameMainController.app.addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}
	
	@FXML
	private void viewScore() {
		GameMainController.app.addNewScene(FXMLService.FXMLNames.WINNINGS);	
	}
	
	@FXML
	public void reset() {
		Alert resetAlert = new Alert(AlertType.CONFIRMATION);
		resetAlert.setTitle("WARNING!");
		resetAlert.setHeaderText("Reset?");
		resetAlert.setContentText("You are about to reset the game. All attempted questions and winnings will be reset. Are you sure you want to continue?");

		Optional<ButtonType> result = resetAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	Thread th = new Thread(new Task<Void>() {
	        	protected Void call() throws IOException {
	        		try {
						GameMainController.getInstance().reset();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
	            }
        	});
            th.start();
        }
	}
	
	@FXML 
	private void quit() {
		Platform.exit();
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}
	
}