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
	/*public static List<Category> setup() throws Exception {
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
			//Memory_maker.historyStart();
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
	}*/
	
	public static List<Category> setup() throws Exception {
		List<Category> categoriesToReturn = new ArrayList<Category>();
		//This is used to get the absolute path, regardless of the location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File history = new File(path + "/.History");
		File winnings = new File(path + "/.winnings");
		Boolean historyExists = history.exists();
		Boolean winningsExists = winnings.exists();
		// Here it checks for pre-existing data and if there isn't, it throws an Exception. 
		List<Category> randomCategoriesWithRandomClues = new ArrayList<Category>();
		if (!historyExists || !winningsExists) {
			File categoriesFolder = new File(path + "/categories");
			if (categoriesFolder.exists()) {
				List<Category> categories = InitialDatabaseExtractor.extractAndSort();
				List<Category> randomCategories = new ArrayList<Category>();
				List<Integer> done = new ArrayList<Integer>(); 
				while (randomCategories.size() < 5) {
					int randomIndex = (int)(Math.random() * categories.size());
					if ((!done.contains(randomIndex))&&(!(categories.get(randomIndex).numberOfClues()<5))) {
						randomCategories.add(categories.get(randomIndex));
						done.add(randomIndex);
					}
				}
				for (Category category:randomCategories) {
					Category categoryToBeAdded = new Category(category.categoryName());
					int clueValue = 100;
					for (Clue clue: category.getRandomClues()) {
						clue.giveValue(String.valueOf(clueValue));
						clueValue += 100;
						categoryToBeAdded.addClue(clue);
					}
					randomCategoriesWithRandomClues.add(categoryToBeAdded);
				}
				Memory_maker.historyStart(randomCategoriesWithRandomClues);
				categoriesToReturn = historyLoader();
			}
			else {
				throw new FileNotFoundException("No data files found.");
			}
		}
		// Otherwise it extracts from the history directory and winnings file already made.
		else {
			categoriesToReturn = historyLoader();
		}
		return categoriesToReturn;
	}
	
	/**
	 * The method loads categories from the history files saved.
	 * @return List<Category> fiveRandomCategoriesWithFiveRandomClues
	 */
	public static List<Category> historyLoader () {
		List<Category> categoriesToReturn = new ArrayList<Category>();
		//This is used to get the absolute path, regardless of the location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File history = new File(path + "/.History");
		File [] categoryFiles = history.listFiles();
		for (File categoryFile: categoryFiles) {
			Category category = new Category(categoryFile.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] data=line.split("@");
			    	Clue clue = new Clue (category.categoryName(), data[0],data[1], data[2], data[3], data[4]);
			    	category.addClue(clue);			    	
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			categoriesToReturn.add(category);
		}
		return categoriesToReturn;
	}
	
	/**
	 * The winnings static method finds the winnings file and reads it, to return the String storing the user's winnings.
	 * @return String winnings
	 */
	public static String winnings() { 
		String win = "0";
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
					//Checking
					System.out.println(win);
					System.out.println(winningLine);
					//Checking done
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
