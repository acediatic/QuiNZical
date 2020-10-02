package application;

import static java.lang.Math.pow;

import java.util.List;

import database.Category;
import database.Clue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class QuestionBoardController implements Controller {
	private Stage _stage;
	private List<Category> _categories = QuiNZical.getData().getGameCategories();
	
	private void initalise() {	}
	
	public void initData(Stage stage) {
		_stage = stage;
	}
	
	@FXML
	private void buttonPressed(ActionEvent e) {
		Node node = (Node) e.getSource();
		GridPane gp = (GridPane) node.getParent();
		int nodeRow = gp.getRowIndex(node);
		int nodeCol = gp.getColumnIndex(node);
		
		Category chosenCat = _categories.get(nodeRow);
		Clue chosenClue = chosenCat.getClue(nodeCol);
		
		System.out.println("beginning question: " + chosenClue.showClue());
		
		/*
		Platform.runLater(new Runnable() {
            @Override public void run() {
                QuiNZical.setScene(homeScene);
                
            }
        });
        */
	}

	
	
	
	
	public Double updateText(Stage stage, Double currentFontSize, Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			currentFontSize = currentFontSize * ratio;
			
			GridPane gp = (GridPane) stage.getScene().getRoot();
			
			gp.setStyle("-fx-font-size: " + currentFontSize + "em; -fx-padding: "+ currentFontSize*10);
			gp.setVgap(pow(2,currentFontSize));
			gp.setHgap(pow(2,currentFontSize));
		
		}
		return currentFontSize;
	}
	
}