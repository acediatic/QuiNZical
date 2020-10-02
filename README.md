---QuiNZical---
Welcome to QuiNZical!

The quiz app that asks you questions about New Zealand.
So whether you're here to play to learn about New Zealand trivia, or to test your veteran knowledge, this is the app for you!


---To play the game:---

Make sure the categories folder and the quinzical.jar file are in the same directory.
DO NOT DELETE ANY OF THE FILES IN THE 'categories' FOLDER, NOR DELETE ANY LINES ALREADY EXISTING IN THE FILES.
Open the terminal in that diectory and copy paste and run the following command in it to run QuiNZical:
java --module-path  /home/se2062020/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -jar quinzical.jar
##MAY WANNA MAKE A SH FILE INSTEAD


---To add new categories:---

Go to the categories folder and make a new file, naming it with the name of the categroy you want.
Add questions to it from the Text Editor - how to do this is shown below.
Your category file will need to have at least 5 questions for it to come into the game.


---To add new clues to existing categories---

Go to the categories folder and right click the categroy file you want to add clues to.
Click "Open in Text Editor".
Add a new line at the bottom and enter your clue in the following format:
Clue_Question@Clue_Type@Clue_Answer
The Clue Question, Clue Type, and Clue Answer must contain no @ symbols, and there should be no spaces after the @ between them.


---Answering questions with multiple answers:---

If the clue you have has multiple answers, for e.g. "The colour of the All Blacks Jersey", then just answer "Black White".
The clue might be marked incorrect because of the order so just try again next time.
If the clue can have multiple potential answers, for e.g "One colour in the All Blacks Jersey", then just answer "Black/ White".
The clue might be marked incorrect because of the order so just try again next time.