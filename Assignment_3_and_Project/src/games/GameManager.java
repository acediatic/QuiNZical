package games;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import database.Category;
import database.DataExtractor;
import database.InitialDatabaseExtractor;
import database.Memory_maker;

public class GameManager {

	public static List<Category> gameStartCategories () {
		List<Category> categoriesToChooseFrom = new ArrayList<Category>();
		//The absolute path is found regardless of location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File custom = new File(path + "/.custom_categories");
		if (custom.exists()) {
			try {
				categoriesToChooseFrom = InitialDatabaseExtractor.extractAndSort(".custom_categories");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return categoriesToChooseFrom;
	}
	
	public static List<Category> initialCategoryChooser (List<Category> categoriesSelected) {
		List<Category> historyCategories = new ArrayList<Category>();
		try {
			Memory_maker.historyStart(categoriesSelected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyCategories;
	}
	
	public static List<Category> gameLoader () {
		List<Category> historyCategories = new ArrayList<Category>();
		try {
			historyCategories = DataExtractor.setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historyCategories;
	}
}
