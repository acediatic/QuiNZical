package controller.sceneControllers;


import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.FXMLService;
public class WinningsController extends Controller {
	
	@FXML 
	private Text usrWinnings;
	
	@FXML
	private Text yourWinningsTxt;
	
	@FXML
	private void initialize() {	
		usrWinnings.setFont(PrimaryController.titleFont);
		usrWinnings.setText("$" + PrimaryController.getInstance().getWinnings());
		usrWinnings.setFill(Color.GOLDENROD);
		yourWinningsTxt.setFont(PrimaryController.titleFont);
		yourWinningsTxt.setFill(Color.WHITE);
	}
	
	@FXML
	private void back() {
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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