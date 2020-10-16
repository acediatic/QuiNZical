package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import controller.PrimaryController;

public class ScoreboardExtractor {

	
	public static void makeScoreBoard() {
		try {
			// The winnings file is created and stored with 0 initially.
			File scoreBoard = new File(PrimaryController.path + "/.scoreBoard");
			if (!scoreBoard.exists()) {
				scoreBoard.createNewFile();
			}
			else {
				throw new Exception("Winnings not made.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateScoreBoard(User user) {
		try {
			File scoreBoard = new File(PrimaryController.path + "/.scoreBoard");
			// Winnings should be present when updating them, so if it's not, an exception is thrown.
			if (!scoreBoard.exists()){
				throw new Exception("Scoreboard file not made yet.");
			}
			try (FileWriter f = new FileWriter(scoreBoard);
					BufferedWriter b = new BufferedWriter(f);
					PrintWriter p = new PrintWriter(b);) {
				p.println(user.getName()+user.reportScore());
				}
			catch (IOException i) {
					i.printStackTrace();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
