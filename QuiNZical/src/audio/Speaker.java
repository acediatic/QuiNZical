package audio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import database.Clue;
import database.CategoryExtractor;

public class Speaker {
	
	private static String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
	private static String [] relevantPath = fullPath.split(System.getProperty("path.separator"));
	private static String path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
	private static File festivalScheme = new File(path + "/.toSay.scm");
	private static File nz_voice = new File("/usr/share/festival/voices/english/akl_nz_jdt_diphone");
	private static Process _process;
	
	public static Boolean checkNZVoice () {
		if (nz_voice.exists()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean schemeExists() {
		if (festivalScheme.exists()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void question (Clue clue, double speed) {
		if (schemeExists()) {
			CategoryExtractor.deleteDir(festivalScheme);
		}
		speed = 1/speed;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(festivalScheme));
			writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
			writer.write("(SayText \"" + clue.showClue() + "\")" + System.getProperty("line.separator"));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String speakingCommand = "festival -b " + festivalScheme;
			ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
			try {
				_process = speak.start();
				_process.waitFor();
				_process.destroy();
				/*Process speakingProcess = speak.start();
				speakingProcess.waitFor();
				speakingProcess.destroy();*/
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		CategoryExtractor.deleteDir(festivalScheme);
	}
	
	public static void questionWithNZVoice (Clue clue, double speed) {
		if (checkNZVoice()) {
			if (schemeExists()) {
				CategoryExtractor.deleteDir(festivalScheme);
			}
			speed = 1/speed;
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(festivalScheme));
				writer.write("(voice_akl_nz_jdt_diphone)" + System.getProperty("line.separator"));
				writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
				writer.write("(SayText \"" + clue.showClue() + "\")" + System.getProperty("line.separator"));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String speakingCommand = "festival -b " + festivalScheme;
				ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
				try {
					_process = speak.start();
					_process.waitFor();
					_process.destroy();
					/*Process speakingProcess = speak.start();
					speakingProcess.waitFor();
					speakingProcess.destroy();*/
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			question(clue, speed);
		}
		CategoryExtractor.deleteDir(festivalScheme);
	}
	
	public static void correct (Clue clue, double speed) {
		if (schemeExists()) {
			CategoryExtractor.deleteDir(festivalScheme);
		}
		speed = 1/speed;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(festivalScheme));
			writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
			writer.write("(SayText \"You are correct. The answer is " + clue.showAnswer() + "!\")" + System.getProperty("line.separator"));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String speakingCommand = "festival -b " + festivalScheme;
			ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
			try {
				_process = speak.start();
				_process.waitFor();
				_process.destroy();
				/*Process speakingProcess = speak.start();
				speakingProcess.waitFor();
				speakingProcess.destroy();*/
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		CategoryExtractor.deleteDir(festivalScheme);
	}
	
	public static void correctWithNZVoice (Clue clue, double speed) {
		if (checkNZVoice()) {
			if (schemeExists()) {
				CategoryExtractor.deleteDir(festivalScheme);
			}
			speed = 1/speed;
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(festivalScheme));
				writer.write("(voice_akl_nz_jdt_diphone)" + System.getProperty("line.separator"));
				writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
				writer.write("(SayText \"You are correct. The answer is " + clue.showAnswer() + "!\")" + System.getProperty("line.separator"));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String speakingCommand = "festival -b " + festivalScheme;
				ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
				try {
					_process = speak.start();
					_process.waitFor();
					_process.destroy();
					/*Process speakingProcess = speak.start();
					speakingProcess.waitFor();
					speakingProcess.destroy();*/
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			correct(clue, speed);
		}
		CategoryExtractor.deleteDir(festivalScheme);
	}
	
	public static void incorrect (Clue clue, double speed) {
		if (schemeExists()) {
			CategoryExtractor.deleteDir(festivalScheme);
		}
		speed = 1/speed;
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(festivalScheme));
			writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
			writer.write("(SayText \"You are incorrect. The correct answer was " + clue.showAnswer() + ".\")" + System.getProperty("line.separator"));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String speakingCommand = "festival -b " + festivalScheme;
			ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
			try {
				_process = speak.start();
				_process.waitFor();
				_process.destroy();
				/*Process speakingProcess = speak.start();
				speakingProcess.waitFor();
				speakingProcess.destroy();*/
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		CategoryExtractor.deleteDir(festivalScheme);
	}
	
	public static void incorrectWithNZVoice (Clue clue, double speed) {
		if (checkNZVoice()) {
			if (schemeExists()) {
				CategoryExtractor.deleteDir(festivalScheme);
			}
			speed = 1/speed;
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(festivalScheme));
				writer.write("(voice_akl_nz_jdt_diphone)" + System.getProperty("line.separator"));
				writer.write("(Parameter.set 'Duration_Stretch " + speed + ")" + System.getProperty("line.separator"));
				writer.write("(SayText \"You are incorrect. The correct answer was " + clue.showAnswer() + ".\")" + System.getProperty("line.separator"));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String speakingCommand = "festival -b " + festivalScheme;
				ProcessBuilder speak = new ProcessBuilder("bash", "-c", speakingCommand);
				try {
					_process = speak.start();
					_process.waitFor();
					_process.destroy();
					/*Process speakingProcess = speak.start();
					speakingProcess.waitFor();
					speakingProcess.destroy();*/
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			incorrect(clue, speed);
		}
		CategoryExtractor.deleteDir(festivalScheme);
	}
	
	public static void stopSpeaking() {
		//try {
			/*String stopSpeakingCommand = "killall festival";
			ProcessBuilder stop = new ProcessBuilder("bash", "-c", stopSpeakingCommand);
			try {
				Process stoppingProcess = stop.start();
				stoppingProcess.waitFor();
				stoppingProcess.destroy();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			/*Stream<ProcessHandle> descendants = _process.descendants();
			descendants.filter(ProcessHandle::isAlive).forEach(ph -> {
				ph.destroy();
			});*/
		_process.destroy();
		/*} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
