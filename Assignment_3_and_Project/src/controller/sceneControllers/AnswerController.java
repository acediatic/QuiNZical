package controller.sceneControllers;


import controller.PrimaryController;
import javafx.fxml.FXML;
import service.FXMLService;

public class AnswerController extends Controller {
	
	@FXML
	private void goHome() {
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}
	
	@FXML 
	private void goQuestionBoard() {
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}	

	@Override
	public void init() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
	}
}