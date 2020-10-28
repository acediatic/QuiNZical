package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import controller.PrimaryController;

/**
 * Winning Extractor is responsible for loading winnings from memory,
 * and manipulation of these winnings during the game.
 * @author Adam and Osama
 */
public class WinningsExtractor {

	/**
	 * Retrieves the winnings from memory, if they have been created.
	 * Otherwise, it creates them.
	 * @return the user's current winnings.
 	 */
	public String getWinnings() {
		File winnings = new File(PrimaryController.pathQuiNZical + "/.winnings");
		Boolean winningsExists = winnings.exists();
		
		if(!winningsExists) {
			try {
				makeWinnings();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return readWinnings();
	}
	
	/**
	 * Makes a winnings file for the user, intialised to zero.
	 */
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

	/**
	 * Updates the winning's file for the given clue, given
	 * the answer was correct. This increases the user's score
	 * by the value of the clue.
	 * @param clue, the clue which was answered correctly
	 * @return the users updated score.
	 */
	public Integer updateWinningFileCorrect(Clue clue)  {
		Integer winnings = 0;
		try {
			File winningsFile = new File(PrimaryController.pathQuiNZical + "/.winnings");
			// Winnings should be present when updating them, so if it's not, an exception is thrown.
			if (!winningsFile.exists()){
				throw new Exception("Winnings file not made yet.");
			}
			
			Integer currentWinnings = Integer.parseInt(readWinnings());
			Integer clueWinnings = Integer.parseInt(clue.showValue());
			winnings = currentWinnings + clueWinnings;
			
			BufferedWriter winningWriter = new BufferedWriter(new FileWriter(winningsFile, false));
			winningWriter.write(winnings.toString() + System.getProperty("line.separator"));
			winningWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return winnings;
	}

	/**
	 * Resets the user's winnings back to zero.
	 */
	public void resetWinnings() {
		File winnings = new File(PrimaryController.pathQuiNZical + "/.winnings");
		if (winnings.exists()) {
			winnings.delete();
		}
		makeWinnings();
	}
}
 