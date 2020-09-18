package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class tempTester extends Application {

	//private File fileURL = new File("big_buck_bunny_1_minute.mp4");

	private Button btnMute = new Button("Mute");
	private Button btnForward = new Button(">>");
	private Button btnBackward = new Button("<<");
	private Button btnPause = new Button("Pause/Play");
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("My Cool Video Player");
		try {
			BorderPane root = new BorderPane();
			
			//Media video = new Media(fileURL.toURI().toString());
			//MediaPlayer player = new MediaPlayer(video);
			//player.setAutoPlay(true);
			//MediaView mediaView = new MediaView(player);
			
			btnMute.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
				}
			});
			
			btnForward.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				}
			});
			
			btnBackward.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
				}
			});
			
			btnPause.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {}
			});
			
			
			root.setTop(btnMute);
			root.setLeft(btnBackward);
			root.setRight(btnForward);
			root.setBottom(btnPause);
			Scene scene = new Scene(root, 1000, 700);
			btnMute.setPrefWidth(scene.getWidth());
			btnPause.setPrefWidth(scene.getWidth());
			btnBackward.setPrefHeight(scene.getHeight());
			btnForward.setPrefHeight(scene.getHeight());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * The main method for running the app.
	 * @param args
	 */
		public static void main(String[] args) {
			launch(args);
		}


}
