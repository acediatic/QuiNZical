package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import application.GameMainController;
import controller.Controller;
import database.DataExtractor;
import database.Memory_maker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

public class WinningsController implements Controller {
	
	@FXML 
	private Text usrWinnings;
	
	@FXML
	private Text yourWinningsTxt;
	
	@FXML
	private void initialize() {	
		usrWinnings.setFont(GameMainController.titleFont);
		
		String winnings = "$" + DataExtractor.winnings();
		System.out.println(winnings);
		
		usrWinnings.setText("$" + DataExtractor.winnings());
		usrWinnings.setFill(Color.GOLDENROD);
		yourWinningsTxt.setFont(GameMainController.titleFont);
		yourWinningsTxt.setFill(Color.WHITE);
	}
	
	@FXML
	private void back() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
			Scene scene = loader.load();			
			
			GameMainController.currentController = loader.getController();
			GameMainController.currentController.init();
			
			Platform.runLater(new Runnable() {
	            @Override public void run() {
	               GameMainController.app.setScene(scene); 
	            }
	        });
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void updateText(Number oldVal, Number newVal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}