package controller.sceneControllers;

import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import service.FXMLService;

public class AnswerController extends Controller {
	
	@FXML
	private Text winningsMsg;
	
	@FXML
	private void initialize() {
		winningsMsg.setText("Your Current Winnings: $" + PrimaryController.getInstance().getWinnings());
	}
	
	@FXML
	private void goHome() {
		killAudio();
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}
	
	@FXML 
	private void goQuestionBoard() {
		killAudio();
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}	

	@Override
	public void init() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
	}
	
	private void killAudio() {
		PrimaryController.getInstance().stopAudio();
	}
}