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

public class IncorrectClueExtractor {
	
	/**
	 * makeIncorrectDir makes the incorrect directory to store questions the user got wrong.
	 */
	public static void makeIncorrectDir() {
		try {
			// The incorrect Directory is created.
			File incorrectDir = new File(PrimaryController.path + "/.incorrect");
			if (!incorrectDir.exists()) {
				if (incorrectDir.mkdir()) {
				}
				else {
					throw new Exception("Incorrect Collection not made.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * addIncorrect is used to store a clue a user got incorrect.
	 * @param clue
	 * @throws Exception 
	 */
	public static void addIncorrect(Clue clue) throws Exception {
		File history = new File(PrimaryController.path + "/.incorrect");
		if (!history.exists()) {
			throw new Exception("Directory for incorrect clue not made yet.");
		}
		
		// The clue answered has its file located in history, and the file is copied line by line, excluding
		// the one of the clue answered, and then the new file gets renamed to the old one, replacing it.
		File categoryFile = new File(PrimaryController.path+"/.History/"+clue.getCategory().categoryName());
		String lineToAdd = clue.showClue() +"@"+clue.showClueType()+"@"+clue.showAnswer()+"@"+clue.showValue()+"@false";
		if (!categoryFile.exists()) {
			categoryFile.createNewFile();
			// False here says to overwrite, rather than append.
			BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile, false));
			writer.write(lineToAdd);
			writer.close();
		}
		else {
			BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile));
			writer.append(lineToAdd);
			writer.close();
		}
	}
	
	/**
	 * getIncorrect reads the files to find get all the categories the user got incorrect and the clues 
	 * within each category they got wrong. It is then stored into a Category list, which is returned.
	 * @return list of categories with incorrect clues
	 */
	public static List<Category> getIncorrect() {
		List<Category> incorrectCategories = new ArrayList<Category>();
		File incorrect = new File(PrimaryController.path + "/.incorrect");
		File [] categoryFiles = incorrect.listFiles();
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
			incorrectCategories.add(category);
		}
		return incorrectCategories;
	}
	
	/**
	 * resetIncorrect is used to remove the incorrect directory, hence resetting it.
	 */
	public static void resetIncorrect() {
		CategoryExtractor.deleteDir(new File(PrimaryController.path + "/.incorrect"));
	}

}
