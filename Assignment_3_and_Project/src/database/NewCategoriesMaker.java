package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewCategoriesMaker {
	
	public static void formCustomCategories() throws Exception {
		//The absolute path is found regardless of location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File categoriesDirectory = new File(path+"/initial_categories");
		if (!categoriesDirectory.exists()) {
			throw new Exception("Categories folder not found.");
		}
		// It initially makes the directory, and checks if t is made.
		File custom = new File(path + "/.custom_categories");
		Boolean successfullyMade = false;
		if (!custom.exists()) {
			successfullyMade = custom.mkdir();
		}
		
		// If it is, it copies all the files in categories into it.
		if (successfullyMade) {
			File [] categoryFiles = categoriesDirectory.listFiles();
			for (File categoryFile: categoryFiles) {
				try {
					Files.copy(Paths.get(path+"/initial_categories/"+categoryFile.getName()), Paths.get(path+"/.custom_categories/"+categoryFile.getName()));
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
		else {
			throw new Exception("Customer categories could not be made.");
		}

	}
	
	public static void addUserGivenClue(Clue clue) {
		//The absolute path is found regardless of location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File category = new File(path+"/.custom_categories/"+clue.showCategory());
		if (category.exists()) {
			try (FileWriter f = new FileWriter(category);
					BufferedWriter b = new BufferedWriter(f);
					PrintWriter p = new PrintWriter(b);) {
				p.println(clue.showClue()+",("+clue.showClueType()+")"+clue.showAnswer());
				}
			catch (IOException i) {
					i.printStackTrace();
				}

			//Read more: https://www.java67.com/2015/07/how-to-append-text-to-existing-file-in-java-example.html#ixzz6YMSK188C
		}
		else {
			try {
				category.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try (FileWriter f = new FileWriter(category);
					BufferedWriter b = new BufferedWriter(f);
					PrintWriter p = new PrintWriter(b);) {
				p.println(clue.showClue()+",("+clue.showClueType()+")"+clue.showAnswer());
				}
			catch (IOException i) {
					i.printStackTrace();
				}
		}
	}
	
	public static void newCategory(Category category) {
		
		//The absolute path is found regardless of location of the code.
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(":");
		String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		File newCategory = new File(path+"/.custom_categories/"+category.categoryName());
		if (!newCategory.exists()) {
			try {
				newCategory.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
		
	
}
