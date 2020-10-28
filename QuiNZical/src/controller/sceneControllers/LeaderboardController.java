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
	
	@SuppressWarnings("unchecked") // due to a known bug as listed here:
	// https://stackoverflow.com/questions/53258264/type-safety-a-generic-array-is-created-for-varargs-parameter-is-this-a-good
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
	
	/**
	 * Takes the user back to the homescreen
	 */
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}

	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void init() {}

	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void updateTextIndividual() {}
}