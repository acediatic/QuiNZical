package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import controller.PrimaryController;

public class WinningsExtractor {

	
	public String getWinnings() {
		File winnings = new File(PrimaryController.pathQuiNZical + "/.winnings");
		Boolean winningsExists = winnings.exists();
		
		if(!winningsExists) {
			try {
				makeWinnings();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		return readWinnings();
	}
	
	private void makeWinnings() {
		try {
			// The winnings file is created and stored with 0 initially.
			File winnings = new File(PrimaryController.pathQuiNZical + "/.winnings");
			winnings.createNewFile();
			if (winnings.exists()) {
				BufferedWriter initialWriter = new BufferedWriter(new FileWriter(winnings));
				initialWriter.write("0");
				initialWriter.close();
			}
			else {
				throw new Exception("Winnings not made.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The winnings static method finds the winnings file and reads it, to return the String storing the user's winnings.
	 * @return String winnings
	 */
	private String readWinnings() { 
		String currentWinnings = "0";
		//This is used to get the absolute path, regardless of the location of the code.
		String path = PrimaryController.pathQuiNZical;
		File winnings = new File(path + "/.winnings");
		BufferedReader winningReader;
		try {
			winningReader = new BufferedReader(new FileReader(winnings));
			currentWinnings = winningReader.readLine();
			winningReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 		
		return currentWinnings;
	}

	public void updateWinningFile(Clue clue)  {
		try {
			File winningsFile = new File(PrimaryController.pathQuiNZical + "/.winnings");
			// Winnings should be present when updating them, so if it's not, an exception is thrown.
			if (!winningsFile.exists()){
				throw new Exception("Winnings file not made yet.");
			}
			
			Integer currentWinnings = Integer.parseInt(readWinnings());
			Integer clueWinnings = Integer.parseInt(clue.showValue());
			Integer newWinnings = currentWinnings + clueWinnings;
			
			
			BufferedWriter winningWriter = new BufferedWriter(new FileWriter(winningsFile, false));
			winningWriter.write(newWinnings + System.getProperty("line.separator"));
			winningWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void resetWinnings() {
		File winnings = new File(PrimaryController.pathQuiNZical + "/.winnings");
		if (winnings.exists()) {
			winnings.delete();
		}
		makeWinnings();
	}
}
 