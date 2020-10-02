package database;

import java.util.ArrayList;
import java.util.List;

public class PracticeModuleModel {
	
	// static variable single_instance of type Singleton 
    private static PracticeModuleModel practiceModuleModel = null;
    private Clue clueToAsk;
    private Boolean clueAssigned = false;
    private int attempts;
  
    // variable of type String 
    private List<Category> allGameCategories = new ArrayList<Category>(); 
  
    // private constructor restricted to this class itself 
    private PracticeModuleModel() 
    { 
        try {
			allGameCategories = InitialDatabaseExtractor.extractAndSort();
		} catch (Exception e) {
			e.printStackTrace();
		}
    } 
  
    // static method to create instance of Singleton class 
    public static PracticeModuleModel getInstance() 
    { 
        if (practiceModuleModel == null) 
            practiceModuleModel = new PracticeModuleModel(); 
  
        return practiceModuleModel; 
    }
    
    public List<Category> getGameCategories() {
    	return allGameCategories;
    }
    
    public void markClueAsAnswered (Category selectedCategory, Clue answeredClue) {
    	allGameCategories.get(allGameCategories.indexOf(selectedCategory)).getClue(allGameCategories.get(allGameCategories.indexOf(selectedCategory)).indexOf(answeredClue)).answered();
    }
    
    public void assignRandomClueToAskFromCategory (Category category) {
    	clueToAsk = category.getOneRandomClue();
    	clueAssigned = true;
    }
    
    public Boolean checkAnswerWithinAttempts(String ans) {
    	if (clueAssigned) {
    		if (attempts <= 3) {
    			if (ans.equals(clueToAsk.showAnswer())) {
    				return true;
    			}
    			else {
    				attempts++;
    			}
    		}
    	}
    	return false;
    }
    
    public int returnAttempts(){
    	return attempts;
    }
    
    public String revealAnswer() {
    	return clueToAsk.showAnswer();
    }

}
