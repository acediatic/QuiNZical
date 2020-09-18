package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataExtractor {

	/**
	 * The setup method is used to set up the data for the app, loading the data 
	 * from the files into the app by return a Category list to be used by it.
	 * If there is no history or winning file present, a new one is made using
	 * the Memory_maker class and its method, and the initial set of data comes 
	 * from the catgories directory using the extractAndSort() method.
	 * @return List<Category> categories
	 * @throws Exception
	 */
	public static List<Category> setup() throws Exception {
		List<Category> categories = new ArrayList<Category>();
		//This is used to get the absolute path, regardless of the location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File history = new File(path + "/.History");
		File winnings = new File(path + "/.winnings");
		Boolean historyExists = history.exists();
		Boolean winningsExists = winnings.exists();
		// Here it checks for pre-existing data and if there isn't, it makes new data .
		if (!historyExists || !winningsExists) {
			File custom = new File(path + "/.custom_categories");
			File initial = new File(path + "/initial_categories");
			if (custom.exists()) {
				categories = InitialDatabaseExtractor.extractAndSort(".custom_categories");
			}
			else if (initial.exists()) {
				categories = InitialDatabaseExtractor.extractAndSort("initials_categories");
				NewCategoriesMaker.formCustomCategories();
			}
			else {
				throw new FileNotFoundException("No data files found.");
			}
			Memory_maker.historyStart();
		}
		// Otherwise it extracts from the history directory and winnings file already made.
		else {
			File [] categoryFiles = history.listFiles();
			for (File categoryFile: categoryFiles) {
				Category category = new Category(categoryFile.getName());
				try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				    	String[] data=line.split(",");
				    	Clue clue = new Clue (data[0],data[1], data[2]);
				    	category.addClue(clue);			    	
				    }
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				categories.add(category);
			}
		}
		return categories;
	}
	
	/**
	 * The winnings static method finds the winnings file and reads it, to return the String storing the user's winnings.
	 * @return String winnings
	 */
	public static String winnings() { 
		String win = "0.0";
		//This is used to get the absolute path, regardless of the location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File winnings = new File(path + "/.winnings");
		BufferedReader winningReader;
		try {
			winningReader = new BufferedReader(new FileReader(winnings));
			String winningLine;

			try {
				while((winningLine = winningReader.readLine()) != null) {
					win = winningLine;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				winningReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		
		return win;
	}
	
}
