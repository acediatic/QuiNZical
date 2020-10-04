package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.PrimaryController;

public class CategoryExtractor {
	
	public ArrayList<Category> getCategories() throws Exception {
		ArrayList<Category> gameCategories = new ArrayList<Category>();
		//This is used to get the absolute path, regardless of the location of the code.
		File history = new File(PrimaryController.path + "/.History");
		
		Boolean historyExists = history.exists();
		// Here it checks for pre-existing data and if there isn't, it throws an Exception. 

		if (!historyExists) {
			gameCategories = pickCategories();
			makeHistory(gameCategories);
		}
		// Otherwise it extracts from the history directory and winnings file already made.
		else {
			gameCategories = loadCategories();
		}
		return gameCategories;
	}
	
	public ArrayList<Category> getMasterCategories() throws Exception {
		return extractMasterCategories();
	}
	
	private ArrayList<Category> extractMasterCategories() throws Exception{
		String pathToWD = PrimaryController.path;
		ArrayList<Category> allCategories = new ArrayList<Category>();
		File categoriesDirectory = new File(pathToWD+"/categories");
		if (!categoriesDirectory.exists()) {
			throw new Exception("Categories folder not found.");
		}
		// For each file in categories a category is made to be stored in the list, each category has its own questions.
		for (File categoryFile: categoriesDirectory.listFiles()) {
			Category category = new Category(categoryFile.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] data=line.split("@");
			    	Clue clue = new Clue (category, data[0], data[1], data[2]);
			    	category.addClue(clue);			    	
			    }
			} catch (IOException e) {
				e.printStackTrace();
			} 
			allCategories.add(category);
		}
		return allCategories;
	}
		

	
	private ArrayList<Category> pickCategories() throws Exception {
		ArrayList<Category> randomCategoriesWithRandomClues = new ArrayList<Category>();
		File categoriesFolder = new File(PrimaryController.path + "/categories");
		if (categoriesFolder.exists()) {
			ArrayList<Category> categories = extractMasterCategories();
			ArrayList<Category> randomCategories = new ArrayList<Category>();
			ArrayList<Integer> done = new ArrayList<Integer>(); 
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
		}
		else {
			throw new FileNotFoundException("No data files found.");
		}
		return randomCategoriesWithRandomClues;
	}
	
	
	private void makeHistory(List<Category> categories) throws Exception {
		//The absolute path is found regardless of location of the code.
		String path = PrimaryController.path;
					
		// The history file is created and stored for updating.
		File history = new File(path + "/.History");
		Boolean successfullyMade = false;
		if (!history.exists()) {
			successfullyMade = history.mkdir();
		}
		
		// If it is, it copies all the files in categories into it.
		if (successfullyMade) {
			for (Category category:categories) {
				File newCategory = new File(path+"/.History/"+category.categoryName());
				if (!newCategory.exists()) {
					try {
						newCategory.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//Add clue stuff here
				File inputFile = new File(path+"/.History/"+category.categoryName());
				BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
				for (Clue clue: category.getAllClues()) {
					String lineToAdd = clue.showClue() +"@"+clue.showClueType()+"@"+clue.showAnswer()+"@"+clue.showValue()+"@false";
					writer.write(lineToAdd + System.getProperty("line.separator"));
				}
				writer.close(); 
			}
		}
		else if (!history.exists()) {
			throw new Exception("History could not be made.");
		}	
	}
	
	
	/**
	 * The method loads categories from the history files saved.
	 * @return List<Category> fiveRandomCategoriesWithFiveRandomClues
	 */
	public ArrayList<Category> loadCategories () {
		ArrayList<Category> categoriesToReturn = new ArrayList<Category>();
		//This is used to get the absolute path, regardless of the location of the code.
		String path = PrimaryController.path;
		File history = new File(path + "/.History");
		File [] categoryFiles = history.listFiles();
		for (File categoryFile: categoryFiles) {
			Category category = new Category(categoryFile.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] data=line.split("@");	    	
			    	Clue clue = new Clue (category, data[0],data[1], data[2], data[3], data[4]);
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
	
	public void markQuestionAnswered(Clue clue) throws Exception {
		File history = new File(PrimaryController.path + "/.History");
		if (!history.exists()) {
			throw new Exception("History file not made yet.");
		}
		
		// The clue answered has its file located in history, and the file is copied line by line, excluding
		// the one of the clue answered, and then the new file gets renamed to the old one, replacing it.
		File categoryFile = new File(PrimaryController.path+"/.History/"+clue.getCategory().categoryName());

		BufferedReader reader = new BufferedReader(new FileReader(categoryFile));

		String lineToRemovePrefix = clue.showClue();
		String currentLine;

		ArrayList<String> updatedText = new ArrayList<String>();
		
		while((currentLine = reader.readLine()) != null) {
		    if(!currentLine.startsWith(lineToRemovePrefix)) {
		    	updatedText.add(currentLine + System.getProperty("line.separator"));
		    }
		    else {
		    	updatedText.add(currentLine.substring(0, currentLine.length()-5) + "true"+ System.getProperty("line.separator"));
		    }
		}
		reader.close();
		//false here says to overwrite, rather than append.
		BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile, false));
		
		for (String line : updatedText) {
			writer.write(line);
		}
		writer.close(); 	
	}
	
	public void resetCategories() throws Exception {
		File history = new File(PrimaryController.path + "/.History");
		if (history.exists()) {
			deleteDir(history);
		}
	}
	
	/**
	 * deleteDir is basically a helper function.
	 * It deletes files recursively, hence used to delete directories.
	 * It's major use is to be used to delete the history directory when the game is reset.
	 * @param fileOrDir
	 */
	private static void deleteDir(File fileOrDir) {
	    File[] files = fileOrDir.listFiles();
	    if(files != null) {
	        for (final File file : files) {
	            deleteDir(file);
	        }
	    }
	    fileOrDir.delete();
	}
}


