package audio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

import controller.PrimaryController;
import database.Clue;

public class Speaker {
	private static String path = PrimaryController.pathQuiNZical;
	private static File festivalScheme = new File(path + "/.toSay.scm");
	private static File nz_voice = new File("/usr/share/festival/voices/english/akl_nz_jdt_diphone");
	private static Process _process;
	
	public static void question (Clue clue, double speed) {
		makeScheme(clue, speed, false, false);
		runSpeech();
	}
	
	public static void correct(Clue clue, double speed) {
		makeScheme(clue, speed, true, true);
		runSpeech();
	}
	
	public static void incorrect(Clue clue, double speed) {
		makeScheme(clue, speed, true, false);
		runSpeech();
	}
	
	public static void runSpeech() {
		stopSpeaking();
		sayTextFromFestivalScheme();
	}
	
	public static void stopSpeaking() {
		// credit to Shrey Tailor on Piazza for this solution.
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (_process != null) {
			Stream<ProcessHandle> descendents = _process.descendants();
			descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
			    ph.destroy();
			});
		}
	}
	
	public static void sayTextFromFestivalScheme() {
		String speakingCommand = "festival -b " + festivalScheme;
		ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
		try {
			_process = speak.start();
			_process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String getNZVoiceParam (Clue clue) {
		String nzVoiceParam = "";
		if(nz_voice.exists()) {
			nzVoiceParam = "(voice_akl_nz_jdt_diphone)" + System.getProperty("line.separator");
		}
		return nzVoiceParam;
	}
	
	public static String getAnswerMsgParam(Clue clue, boolean correct) {
		String answerMsg = "";
		if (correct) {
			answerMsg = "(SayText \"You are correct. The answer is " + clue.showAnswer() + "!\")" + System.getProperty("line.separator");
		} else {
			answerMsg = "(SayText \"You are incorrect. The correct answer was " + clue.showAnswer() + ".\")" + System.getProperty("line.separator");
		}
		return answerMsg;
	}
	
	public static void makeScheme(Clue clue, double speed, boolean answered, boolean correct) {
		speed = 1/speed;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(festivalScheme));
			writer.write(getNZVoiceParam(clue));
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

	public static void cleanUp() {
		stopSpeaking();
		if (festivalScheme.exists()) {
			festivalScheme.delete();
		}
	}
}
