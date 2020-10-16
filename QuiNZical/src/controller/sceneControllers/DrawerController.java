package controller.sceneControllers;

import java.io.IOException;

import controller.PrimaryController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import service.FXMLService;

public class DrawerController extends Controller {
	@FXML
	private void initialize() {	}
	
	@FXML
	private void startGame(ActionEvent e) {	
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}
	
	@FXML
	private void viewScore() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.WINNINGS);	
	}
	
	@FXML
	private void practice() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);	
	}
	
	@FXML
	public void reset() {
		HomeController hmCtrl = (HomeController) PrimaryController.getInstance().currentController;
		hmCtrl.closeMenu();
		Alert resetAlert = new Alert(AlertType.CONFIRMATION);
		resetAlert.setTitle("WARNING!");
		resetAlert.setHeaderText("Reset?");
		resetAlert.setContentText("All progress to this point, including clues "
				+ "answered and current winnings will be reset. Are you sure you want to continue?");
		
		resetAlert.showAndWait().ifPresent(response -> {
		    if (response == ButtonType.OK) {
		    	Thread th = new Thread(new Task<Void>() {
		        	protected Void call() throws IOException {
		        		try {
							PrimaryController.getInstance().reset();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
		            }
	        	});
	            th.start();
		    }
		});
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