package controller.sceneControllers;

import java.util.ArrayList;

import com.jfoenix.controls.JFXHamburger;

import controller.PrimaryController;
import database.Category;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.text.Text;
import javafx.util.Callback;

import service.FXMLService;

public class ChooseCategoryController extends Controller {
	@FXML 
	private JFXHamburger hamMenu;
	
	@FXML
	private Text mainTxt;
	
	@FXML
	private ListView<CatCheckbox>  categoryChooser;
	
	@FXML
	private Button startGameBtn;
	
	private ArrayList<Category> gameCategories = new ArrayList<Category>();
	
	/**
	 * Sets up the category chooser list view, with listeners to determine
	 * when the user has selected 5 categories.
	 */
	@FXML
	private void initialize() {	
        mainTxt.setFont(PrimaryController.titleFont);
        
        ArrayList<CatCheckbox> catCheckBoxes = new ArrayList<CatCheckbox>();
        
        for (Category cat : PrimaryController.getInstance().getMasterCategories()) {
            CatCheckbox catCheckbox = new CatCheckbox(cat, false);
            catCheckBoxes.add(catCheckbox);

            // observe item's on property and display message if it changes:
            catCheckbox.onProperty().addListener((obs, wasOn, isNowOn) -> {
                if (isNowOn && !wasOn) {
                	gameCategories.add(catCheckbox.getCategory());
                } else if (!isNowOn && wasOn) {
                	gameCategories.remove(catCheckbox.getCategory());
                }
                startGameBtn.setDisable((gameCategories.size() != 5));
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

	
	/* A static class that facilitates the adding of checkboxes to the listview. 
	 * Credit for solution: James_D at https://stackoverflow.com/questions/28843858/javafx-8-listview-with-checkboxes/28845460 
	 */
    public static class CatCheckbox extends ListCell<CatCheckbox> {
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
	
    /* A static class that facilitates the adding of checkboxes to the listview. 
	 * Credit for solution: James_D at https://stackoverflow.com/questions/28843858/javafx-8-listview-with-checkboxes/28845460 
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
	
	/**
	 * Starts the game with the chosen categories.
	 */
	@FXML
	private void startGame() {	
    	PrimaryController.getInstance().setCategories(gameCategories);
    	PrimaryController.getInstance().latch.countDown();
    	PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);	
	}
	
	/**
	 * Starts the game with random categories.
	 */
	@FXML
	private void random() {
		PrimaryController.getInstance().setRandomCategories();
		PrimaryController.getInstance().latch.countDown();
    	PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}

	
	/**
	 * An unused utility method from the superclass
	 */
	public void init() {}
	
	/**
	 * An unused utility method from the superclass
	 */
	@Override
	public void updateTextIndividual() {}
}