package database;

import java.util.ArrayList;
import java.util.List;

public class ModuleModel {
	
	// static variable single_instance of type Singleton 
    private static ModuleModel moduleModel = null; 
  
    // variable of type String 
    private List<Category> inGameCategories = new ArrayList<Category>(); 
  
    // private constructor restricted to this class itself 
    private ModuleModel() 
    { 
        try {
			inGameCategories = DataExtractor.setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
    } 
  
    // static method to create instance of Singleton class 
    public static ModuleModel getInstance() 
    { 
        if (moduleModel == null) 
            moduleModel = new ModuleModel(); 
  
        return moduleModel; 
    }
    
    public List<Category> getGameCategories() {
    	return inGameCategories;
    }
    
    public void markClueAsAnswered (Category selectedCategory, Clue answeredClue) {
    	inGameCategories.get(inGameCategories.indexOf(selectedCategory)).getClue(inGameCategories.get(inGameCategories.indexOf(selectedCategory)).indexOf(answeredClue)).answered();
    }
} 