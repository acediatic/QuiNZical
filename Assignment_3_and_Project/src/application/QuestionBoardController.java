package application;

import static java.lang.Math.pow;

import java.util.List;

import database.Category;
import database.Clue;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class QuestionBoardController implements Controller {
	private List<Category> _categories = GameMainController.getData().getGameCategories();
	
	private void initalise() {	}
	
	public void init() {}
	
	@FXML
	private void buttonPressed(ActionEvent e) {
		Node node = (Node) e.getSource();
		int nodeRow = GridPane.getRowIndex(node);
		int nodeCol = GridPane.getColumnIndex(node);
		
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