package controller.sceneControllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import controller.PrimaryController;
import database.Category;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.FXMLService;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

@SuppressWarnings("unused")
public class ChooseCategoryController extends Controller {
	@FXML 
	private JFXHamburger _hamMenu;
	
	@FXML
	private Text mainTxt;
	
	@FXML
	private ListView<CatCheckbox>  categoryChooser;
	
	private ArrayList<Category> gameCategories = new ArrayList<Category>();
	
	@FXML
	private void initialize() {	
        mainTxt.setFont(PrimaryController.titleFont);
        
        for (Category cat : PrimaryController.getInstance().getMasterCategories()) {
            CatCheckbox catCheckbox = new CatCheckbox(cat, false);

            // observe item's on property and display message if it changes:
            catCheckbox.onProperty().addListener((obs, wasOn, isNowOn) -> {
                if (gameCategories.size() < 4) {
                	gameCategories.add(catCheckbox.getCategory());
                } else {
                	gameCategories.add(catCheckbox.getCategory());
                	PrimaryController.getInstance().setCategories(gameCategories);
                	PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
                }
            });

            categoryChooser.getItems().add(catCheckbox);
        }

        categoryChooser.setCellFactory(CheckBoxListCell.forListView(new Callback<CatCheckbox, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CatCheckbox catCheckbox) {
                return catCheckbox.onProperty();
            }
        }));
    }

    public static class CatCheckbox {
    	private final ObjectProperty<Category> cat = new SimpleObjectProperty<Category>();
    	private final StringProperty name = new SimpleStringProperty();
        private final BooleanProperty on = new SimpleBooleanProperty();

        public CatCheckbox(Category cat, boolean on) {
            setCat(cat);
        	setName(cat.categoryName());
            setOn(on);
        }

        public final ObjectProperty<Category> catProperty() {
            return this.cat;
        }

        public final String getCat() {
            return this.nameProperty().get();
        }

        public final void setCat(final Category cat) {
            this.catProperty().set(cat);
        }
        
        public final Category getCategory() {
            return this.catProperty().get();
        }
        
        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        public final BooleanProperty onProperty() {
            return this.on;
        }

        public final boolean isOn() {
            return this.onProperty().get();
        }

        public final void setOn(final boolean on) {
            this.onProperty().set(on);
        }

        @Override
        public String toString() {
            return getName();
        }

    }
/*
        Button markAllCompleted = new Button("Mark All Completed");
        markAllCompleted.setOnAction(e -> {
            for (categoryOption cat : listView.getItems()) {
                cat.setCompleted(true);
            }
        }
        */
	
	public static class categoryOption {
        private final StringProperty name = new SimpleStringProperty();
        private final BooleanProperty on = new SimpleBooleanProperty();

        public categoryOption(String name, boolean on) {
            setName(name);
            setOn(on);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        public final BooleanProperty onProperty() {
            return this.on;
        }

        public final boolean isOn() {
            return this.onProperty().get();
        }

        public final void setOn(final boolean on) {
            this.onProperty().set(on);
        }

        @Override
        public String toString() {
            return getName();
        }
    }
	
	@FXML
	private void internationalStart() throws IOException {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.INTERNATIONALBOARD);	
	}
	
	public void init() {}
	
	
	@FXML
	private void startGame(ActionEvent e) {	
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);		
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}
}