package controller.sceneControllers;

import java.io.IOException;
import java.util.Optional;

import controller.PracticeModuleController;
import controller.PrimaryController;
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
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}
	
	@FXML
	private void viewScore() {
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.WINNINGS);	
	}
	
	@FXML
	private void practice() {
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);	
	}
	
	@FXML
	public void reset() {
		HomeController hmCtrl = (HomeController) PrimaryController.getInstance().currentController;
		hmCtrl.closeMenu();
		PrimaryController.app.showResetAlert();
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