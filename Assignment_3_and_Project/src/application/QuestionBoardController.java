package application;

import static java.lang.Math.pow;

import java.io.IOException;
import java.util.List;

import controller.Controller;
import database.Category;
import database.Clue;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class QuestionBoardController implements Controller {
	private List<Category> _categories = GameMainController.getModel().getGameCategories();
	
	public void init() {}
	
	@FXML 
	private GridPane gp;
	
	public void initialize() {
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
						} if (rowIndex == null) {
							rowIndex = 0;
						}
						
				        if(rowIndex == clueIndex && colIndex == catIndex) {
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
		
		// null means child has not been changed from default
		// col, which is col 0.
		if (nodeCol == null) {
			nodeCol = 0;
		}
		
		Category chosenCat = _categories.get(nodeCol);
		Clue chosenClue = chosenCat.getClue(nodeRow);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("askQuestionScene.fxml"));
			Scene scene = loader.load();
			AskingController ac = (loader.getController());
			ac.initClue(chosenClue);
			
			GameMainController.currentController = ac;
			Platform.runLater(new Runnable() {
	            @Override public void run() {
	               GameMainController.app.setScene(scene); 
	            }
	        });
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}			
	}
	
	public void updateText(Number oldVal, Number newVal) {
		Double currentFontSize = GameMainController._currentFontSize;
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			GameMainController._currentFontSize = currentFontSize * ratio;
			
			GridPane gp = (GridPane) GameMainController.app.getStage().getScene().getRoot();
			
			gp.setStyle("-fx-font-size: " + currentFontSize + "em; -fx-padding: "+ currentFontSize*10);
			gp.setVgap(pow(2,currentFontSize));
			gp.setHgap(pow(2,currentFontSize));
		
		}
	}
}