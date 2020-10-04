package application;


import controller.Controller;
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
		usrWinnings.setFont(GameMainController.titleFont);
		usrWinnings.setText("$" + GameMainController.getInstance().getWinnings());
		usrWinnings.setFill(Color.GOLDENROD);
		yourWinningsTxt.setFont(GameMainController.titleFont);
		yourWinningsTxt.setFill(Color.WHITE);
	}
	
	@FXML
	private void back() {
		GameMainController.app.addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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