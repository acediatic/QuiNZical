package application;

import static java.lang.Math.pow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import controller.Controller;
import database.Category;
import database.Clue;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import service.AskQuestionService;
import service.FXMLService;
import service.FXMLService.FXMLNames;

public class QuestionBoardController extends Controller {
	private List<Category> _categories = GameMainController.getModel().getGameCategories();
	
	public void init() {}
	
	@FXML 
	private GridPane gp;
	
	@FXML
	private Label cat1;
	
	@FXML
	private Label cat2;
	
	@FXML
	private Label cat3;
	
	@FXML
	private Label cat4;
	
	@FXML
	private Label cat5;
	
	private List<Label> lblList;
	
	public void initialize() {
		lblList = Arrays.asList(cat1,cat2,cat3,cat4,cat5);
		int catCounter = 0;
		for (Label cat : lblList) {
			cat.setText(_categories.get(catCounter).toString());
			cat.setWrapText(true);
			catCounter++;
		}
		
		int catIndex = -1;
		for (Category cat : _categories) {
			catIndex++;
			int clueIndex = 0; //starts at zero as in gridpane q's begin at row (index) 1
			for (Clue clue : cat.getAllClues()) {
				clueIndex++;
				if (!clue.isAnswered()) {
				    ObservableList<Node> childrens = gp.getChildren();
					for (Node node : childrens) {
						Integer colIndex = gp.getColumnIndex(node);
						Integer rowIndex = gp.getRowIndex(node);
						if (colIndex == null) {
							colIndex = 0;
						} 
						
						if (rowIndex == null) {
							rowIndex = 0;
						} 
						
						if (rowIndex == clueIndex && colIndex == catIndex) {
				            node.setDisable(false);
				            break;
				        }
				    }
					break;
				}
			}
		}
	}

	
	@FXML
	private void buttonPressed(ActionEvent e) {

		Node node = (Node) e.getSource();
		Integer nodeCol = GridPane.getColumnIndex(node);
		Integer nodeRow = GridPane.getRowIndex(node)-1;
		
		AskQuestionService askQuestion = new AskQuestionService();
			askQuestion.setCatAndClue(nodeCol, nodeRow);
			askQuestion.start();
		}
	
	public void updateTextIndividual() {
		Double currentFontSize = GameMainController._currentFontSize;
		gp.setStyle("-fx-font-size: " + currentFontSize + "em; -fx-padding: "+ currentFontSize*10);
		gp.setVgap(pow(2,currentFontSize));
		gp.setHgap(pow(2,currentFontSize));
	}
}