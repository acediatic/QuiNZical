package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitialDatabaseExtractor {
	
	/**
	 * extractAndSort() takes all the data from the categories folder and uses it to form the 
	 * initial set of categories and clues, storing them as a Category list, which is returned.
	 * @return List<Category> categories
	 * @throws Exception
	 */
	/*public static List<Category> extractAndSort(String filename) throws Exception {
		// The absolute path regardless of position of the file is used for ease.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		List<Category> categories = new ArrayList<Category>();
		File categoriesDirectory = new File(path+"/"+filename);
		if (!categoriesDirectory.exists()) {
			throw new Exception(filename + " folder not found.");
		}
		// For each file in categories a category is made to be stored in the list, each category has its own clues.
		File [] categoryFiles = categoriesDirectory.listFiles();
		for (File categoryFile: categoryFiles) {
			Category category = new Category(categoryFile.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] data=line.split(",");
			    	String[] typeAndClue = data[1].split(") ");  
			    	Clue clue = new Clue (data[0].trim(),typeAndClue[0].replaceAll("(", "").trim(), typeAndClue[1].trim());
			    	category.addClue(clue);			    	
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			categories.add(category);
		}
		return categories;
	}*/
	
	/**
	 * extractAndSort() takes all the data from the categories folder and uses it to form the 
	 * initial set of categories and questions, storing them as a Category list, which is returned.
	 * @return List<Category> categories
	 * @throws Exception
	 */
	public static List<Category> extractAndSort() throws Exception {
		// The absolute path regardless of position of the file is used for ease.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		//String [] relevantPath = fullPath.split(":");
		String [] relevantPath = fullPath.split(";");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		List<Category> categories = new ArrayList<Category>();
		File categoriesDirectory = new File(path+"/categories");
		if (!categoriesDirectory.exists()) {
			throw new Exception("Categories folder not found.");
		}
		// For each file in categories a category is made to be stored in the list, each category has its own questions.
		File [] categoryFiles = categoriesDirectory.listFiles();
		for (File categoryFile: categoryFiles) {
			Category category = new Category(categoryFile.getName());
			try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] data=line.split("@");
			    	Clue clue = new Clue (category.categoryName(), data[0], data[1], data[2]);
			    	category.addClue(clue);			    	
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			categories.add(category);
		}
		return categories;
	}

}
