package controller.sceneControllers;

import java.util.Arrays;

import controller.PrimaryController;
import database.ScoreboardExtractor;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.FXMLService;
public class LeaderboardController extends Controller {
	
	@FXML
	private TableView<User> leaderboard;
	
	@FXML
	private Label title;
	
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {	
		title.setFont(PrimaryController.titleFont);
		
		TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
		TableColumn<User, String> scoreCol = new TableColumn<User, String>("Score");
		
		for(TableColumn<User, String> tc : Arrays.asList(nameCol, scoreCol)) {
			tc.setCellValueFactory(new PropertyValueFactory<>(tc.getText()));
			tc.setMaxWidth(Double.MAX_VALUE);
			tc.setResizable(false);
			tc.prefWidthProperty().bind(leaderboard.widthProperty().multiply(0.48));
		}
		
		leaderboard.getColumns().addAll(nameCol, scoreCol);		
		leaderboard.getItems().addAll(ScoreboardExtractor.extractScoreBoard());
	}
	
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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