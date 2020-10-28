package audio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

import controller.PrimaryController;
import database.Clue;

/**
 * Speaker is responsible for generating and executing the 
 * text to speech functionality. It has varying utility methods
 * to account for reading the question, correct and incorrect responses, 
 * as well as adjusting the playback speed, and ending playback.
 * @author Adam and Osama
 *
 */
public class Speaker {
	private static String path = PrimaryController.pathQuiNZical;
	private static File festivalScheme = new File(path + "/.toSay.scm");
	private static File nzVoice = new File("/usr/share/festival/voices/english/akl_nz_jdt_diphone");
	private static Process process;
	
	
	/**
	 * Plays the input question text at the specified speed
	 * Creates a festival scheme and plays it back at the speed.
	 * @param clue to be read out
	 * @param speed. speed to play back the audio at.
	 */
	public static void question (Clue clue, double speed) {
		makeScheme(clue, speed, false, false);
		runSpeech();
	}
	
	/**
	 * Plays the input question with the response being correct
	 * at the specified speed
	 * Creates a festival scheme and plays it back at the speed.
	 * @param clue to be read out
	 * @param speed. speed to play back the audio at.
	 */
	public static void correct(Clue clue, double speed) {
		makeScheme(clue, speed, true, true);
		runSpeech();
	}
	
	/**
	 * Plays the input question with the response being incorrect
	 * at the specified speed
	 * Creates a festival scheme and plays it back at the speed.
	 * @param clue to be read out
	 * @param speed. speed to play back the audio at.
	 */
	public static void incorrect(Clue clue, double speed) {
		makeScheme(clue, speed, true, false);
		runSpeech();
	}
	
	
	/**
	 * Plays the speech stored in the current scheme file.
	 */
	private static void runSpeech() {
		stopSpeaking();
		sayTextFromFestivalScheme();
	}
	
	/**
	 * Kills any audio currently playing. Invoked when new audio
	 * is played, or when the user navigates from the scene 
	 * currently playing the audio.
	 */
	public static void stopSpeaking() {
		// credit to Shrey Tailor on Piazza for this solution.
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (process != null) {
			Stream<ProcessHandle> descendents = process.descendants();
			descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
			    ph.destroy();
			});
		}
	}
	
	/**
	 * Generates TTS from the festival scheme file. 
	 */
	private static void sayTextFromFestivalScheme() {
		String speakingCommand = "festival -b " + festivalScheme;
		ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
		try {
			process = speak.start();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the scheme file which will then be read out.
	 * @param clue the clue information to add to the scheme file
	 * @param speed the speed to play the audio back at
	 * @param answered whether or not the clue is answered
	 * @param correct whether or not the clue was answered correctly.
	 */
	private static void makeScheme(Clue clue, double speed, boolean answered, boolean correct) {
		speed = 1/speed;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(festivalScheme));
			writer.write(getNZVoiceParam());
			writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
			if (!answered) {
				writer.write("(SayText \"" + clue.showClue() + "\")" + System.getProperty("line.separator"));
			} else {
				writer.write(getAnswerMsgParam(clue, correct));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Specifies the NZ voice parameter to add to the festiaval file,
	 * if it is installed.
	 * @return the text to append to the scheme file.
	 */
	private static String getNZVoiceParam () {
		String nzVoiceParam = "";
		if(nzVoice.exists()) {
			nzVoiceParam = "(voice_akl_nz_jdt_diphone)" + System.getProperty("line.separator");
		}
		return nzVoiceParam;
	}
	
	/**
	 * Gets the answer msg text to add, if the question has been answered.
	 * @param clue the clue that has been answered
	 * @param correct. whether the clue was answered correctly.
	 * @return. the answer message to be added to the scheme file.
	 */
	private static String getAnswerMsgParam(Clue clue, boolean correct) {
		String answerMsg = "";
		if (correct) {
			answerMsg = "(SayText \"You are correct. The answer is " + clue.showAnswer() + "!\")" + System.getProperty("line.separator");
		} else {
			answerMsg = "(SayText \"You are incorrect. The correct answer was " + clue.showAnswer() + ".\")" + System.getProperty("line.separator");
		}
		return answerMsg;
	}

	/**
	 * Called on game exit, removes the extraneous festival file
	 * and stops any audio currently playing.
	 */
	public static void cleanUp() {
		stopSpeaking();
		if (festivalScheme.exists()) {
			festivalScheme.delete();
		}
	}
}
