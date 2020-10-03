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
import service.FXMLService;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

public class WinningsController extends Controller {
	
	@FXML 
	private Text usrWinnings;
	
	@FXML
	private Text yourWinningsTxt;
	
	@FXML
	private void initialize() {	
		usrWinnings.setFont(GameMainController.titleFont);
		usrWinnings.setText("$" + DataExtractor.winnings());
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