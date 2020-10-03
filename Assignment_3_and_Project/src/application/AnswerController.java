package application;


import controller.Controller;
import javafx.fxml.FXML;
import service.FXMLService;

public class AnswerController extends Controller {
	
	@FXML
	private void goHome() {
		GameMainController.app.addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}
	
	@FXML 
	private void goQuestionBoard() {
		GameMainController.app.addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
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