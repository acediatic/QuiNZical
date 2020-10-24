package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.PrimaryController;

/**
 * ScoreboardExtractor is used to make the scoreboard to record results and rank them, and also to read rankings from file.
 * @author Osama Kashif (Team 22)
 * @author Adam Sinclair (Team 22)
 *
 */
public class ScoreboardExtractor {

	/**
	 * makeScoreBoard is used to make the score board to save the user names and scores.
	 */
	public static void makeScoreBoard() {
		try {
			// The scoreBoard file is created.
			File scoreBoard = new File(PrimaryController.pathQuiNZical + "/.scoreBoard");
			if (!scoreBoard.exists()) {
				if (scoreBoard.createNewFile()) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(scoreBoard));
					writer.append("Osama`9000");
					writer.append("Adam`100");
					writer.close();
				}
				else {
					throw new Exception("ScoreBoard not made.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * extractScoreBoard is used to read the user names and scores from the saved scoreboard file.
	 * It then uses that to saves users in a list of users, which is then returned so that 
	 * user names and scores can be displayed. 
	 * @return list of users
	 */
	public static List<User> extractScoreBoard() {
		//Making a list of users to return for the scoreboard.
		List<User> ranks = new ArrayList<User>();
		try {
			// The scoreBoard file is checked and created if not present.
			File scoreBoard = new File(PrimaryController.pathQuiNZical + "/.scoreBoard");
			if (!scoreBoard.exists()) {
				ScoreboardExtractor.makeScoreBoard();
			}
			try (BufferedReader br = new BufferedReader(new FileReader(scoreBoard))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] data=line.split("`");
			    	User userOnBoard = new User(data[0], data[1]);
			    	ranks.add(userOnBoard);
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(ranks); // sorts ranks from lowest to highest scores.
		Collections.reverse(ranks); // reverse to go from highest to lowest.
		return ranks;
	}
	
	/**
	 * updateScoreBoard is used to update the scoreboard file at the end of the game when the user is done with the game.
	 * It takes a list of users which will be the list of previous users from the saved file, with the user who just 
	 * finished the game added to it. This list is then sorted with the highest scorer on top and saved to file.
	 * @param usersOnBoard
	 */
	public static void updateScoreBoard(List<User> usersOnBoard) {
		try {
			File scoreBoard = new File(PrimaryController.pathQuiNZical + "/.scoreBoard");
			if (!scoreBoard.exists()) {
				if (scoreBoard.createNewFile()) {
				}
				else {
					throw new Exception("ScoreBoard not made.");
				}
			}
			Collections.sort(usersOnBoard); // sorts user from lowest to highest scores.
			Collections.reverse(usersOnBoard); // reverse to go from highest to lowest.
			File inputFile = scoreBoard;
			BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
			for (User user: usersOnBoard) {
				String lineToAdd = user.getName()+"`"+user.reportScore();
				writer.write(lineToAdd + System.getProperty("line.separator"));
			}
			writer.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
