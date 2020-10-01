package application;

import javafx.stage.Stage;

public interface Controller {

	public void initData(Stage _stage);
	public Double updateText(Stage _stage, Double currentFontSize, Number oldVal, Number newVal);
}
