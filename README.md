
# QuiNZical


## Welcome to QuiNZical!

The quiz app that asks you questions about New Zealand.
So whether you're here to play to learn about New Zealand trivia, culture, and history, or to test your veteran knowledge, this is the app for you!


### To play the game:

Make sure the `categories` folder and the `quiNZical.jar` file are in the same directory, as well as `quiNZical.sh`.
DO NOT DELETE ANY OF THE FILES IN THE `categories` FOLDER, IT MUST HAVE AT LEAST 5 FILES, NOR DELETE ANY LINES ALREADY EXISTING IN THE FILES, THERE MUST BE AT LEAST 5 FILES.

Navigate to "Assignment_3_and_project", where you'll find `quiNZical.sh`.

### To run:
Open the terminal in that directory and copy paste and run the following command in it to run QuiNZical:

`./quiNZical.sh`

(In case it doesn't work you will need to give it executable rights and then run by entering the following command instead:
 `chmod +x quiNZical.sh; ./quiNZical.sh`)


### Games module:

By pressing the `Begin` button, or going to the hamburger menu and selecting `NZ Questions` you will be able to begin the game. You will then choose 5 categories, from which the questions will be asked. Then the game will begin and take you to the question board.

#### Question Board:
In the games module you will have a table of questions. There will be 5 columns, one for each category you selected. The first row will have the name of the category, after which the following 5 rows will be questions  randomly selected from the category.
You will be able to answer questions from any category, but you will go in the order of low to high value questions, i.e. questions will range from $100 to $500 - you will only be able to do the $100 one initially; then the $200 after you answer the $100 one for the category, and so on. As you answer each question it is removed from the question board.

![Image of Question Board](README_Images/question_board.png)

#### Answering questions:

Once you select a category you will be taken to the answering screen. The clue/question will play out and you can answer it. 
![Image of Asking](README_Images/asking.png)
If you want to play the clue/question at a different speed, just adjust the speed bar and click the play button again. 
![Image of Play](README_Images/play.png)
You can toggle to see the clue/question as well. 
![Image of Mouse On Toggle](README_Images/toggle_mouse.png)
![Image of Toggled](README_Images/clue_toggled.png)
There will be a timer counting down - this shows the time you have to answer the question. To answer the question just type the answer in the text box and click submit.

![Image of Type and Submit](README_Images/submit.png)

##### Answering questions with multiple answers/various possible answers:

If the clue you have has multiple answers, for e.g. "The colour of the All Blacks Jersey", you can answer "Black, White", or, "White, Black"; both will be marked correct - i.e. the order does not matter, just separate each of the options by a comma.

![Image of Comma Answer](README_Images/comma_answer.png)

If the clue can have various potential answers, for e.g. "One colour in the All Blacks Jersey", you can answer "Black/ White", "White/Black", "White", or "Black"; they will all be marked correct.

![Image of Slash Answer](README_Images/slash_answer.png)

In case your clue has multiple answers, but has variations, for e.g. "Two colours in the New Zealand flag", then any of the answers "Blue, Red", "White, Red", or "White, Blue" will be correct - and again order does not matter.

![Image of Comma and Slash Answer](README_Images/comma_and_slash_answer.png)

#### Correct:

If your answer is correct, the correct screen is shown.

![Image of Correct](README_Images/correct.png)

#### Incorrect:

If your answer is incorrect, the incorrect screen is shown.

![Image of Incorrect](README_Images/incorrect.png)

#### Hamburger Menu:

The hamburger menu can be toggled using the icon at the top right corner of the screen. It gives the options for `NZ QUESTIONS`, `VIEW SCORE`,  `PRACTICE`, `LEADERBOARD`, and `RESET`.

![Image of Hamburger Menu Toggle](README_Images/hamburger_menu_toggle.png)

![Image of Hamburger Menu](README_Images/menu.png)

#### Begin Game:

This one does what you think - begin/continue the QuiNZical game!

![Image of Begin](README_Images/begin.png)

### Practice:

The practice module is where you can hone your New Zealand skills. Navigate to the hamburger icon on the home screen, then select "Practice Module". On the next screen, choose a category, and you'll be taken to a random question. From here, you can attempt it. On your 3rd attempt, you'll be given the first letter as a prompt in the text box. On the fourth, you'll be given the answer, both in text and audio. You still have to type it in for practice though!

![Image of Practice](README_Images/practice.png)

![Image of Practice Category Selection](README_Images/practice_category_Selection.png)

![Image of Practice Third Try](README_Images/practice_third_try.png)

![Image of Practice Fourth Try](README_Images/practice_fourth_try.png)

If you get clues/questions wrong in the games module, they are recorded for you to attempt in the practice module.

![Image of Recorded Incorrect](README_Images/recorded_incorrect.png)

#### View Winnings:

From the Hamburger menu on the home screen, you can select the tab to view your current winnings. The next screen will show you how many QuiNZical dollars you've earnt for your efforts. Nice!

![Image of Winnings Option](README_Images/winnings_option.png)

![Image of Winnings](README_Images/winnings.png)

#### Leaderboard:

At the end of each game you can save your score by entering your name. Then your name and score are recorded on the leaderboard.

![Image of Leaderboard Option](README_Images/leaderboard_option.png)

![Image of Leaderboard](README_Images/leaderboard.png)

#### Reset:

Here, you can reset the game. This will clear your winnings, and the current questions, resetting with new categories for you to keep learning! QuiNZical will ask you a confirmation just to be sure!

![Image of Reset Option](README_Images/reset_option.png)

![Image of Reset Confirmation](README_Images/reset_confirmation.png)

### International Section:

An International section gets unlocked once you have completed two categories in the NZ Questions Section. Besides the new bank of questions available, everything else is the same.

![Image of International Option](README_Images/international_option.png)

![Image of International](README_Images/international.png)

### Exiting the Game
Just click the x at the top right. It closes the application. Fear not though, all your hard work is preserved!

![Image of Closing](README_Images/closing.png)

### To add new categories of your own:

Go to the `categories` folder and make a new file, naming it with the name of the category you want.
Add questions to it from the Text Editor - how to do this is shown below.
Your category file will need to have at least 5 questions for it to come into the game.

![Image of Categories](README_Images/categories.png)

![Image of Category Files](README_Images/within_categories.png)

### To add new clues to existing (or new) categories

Go to the categories folder and right click the category file you want to add clues to.
Click "Open in Text Editor".
Add a new line at the bottom and enter your clue in the following format:

`Clue_Question@Clue_Type@Clue_Answer`

Separate the Clue_Question, Clue_Type, and Clue_Answer with an `@` symbol. The Clue Question, Clue Type, and Clue Answer must not contain any `@` symbols themselves, Clue_Question must not have an `@` before it, Clue_Answer must not have an `@` at the end, and there should be no spaces after the `@` between the  Clue_Question, Clue_Type, and Clue_Answer.

![Image of Clue Format](README_Images/clue.png)

![Image of Clue Format](README_Images/clue_with_@_marked.png)

If your Question has multiple answers, for e.g. the Question "Name all the colours in the New Zealand flag ", will have the answer "Blue, Red, White" (irrespective of order), separate the multiple answers with a comma:
`Clue_Question@Clue_Type@Multiple_Clue_Answer_1,Multiple_Clue_Answer_2,Multiple_Clue_Answer_3`

If your Question has various possible answers, for e.g. the Question "Name one colour in the New Zealand flag ", will have the answer "Blue", "Red", or "White", separate the variations (possible answers) with a forward slash:
`Clue_Question@Clue_Type@Possible_Clue_Answer_1/Possible_Clue_Answer_2/Possible_Clue_Answer_3`

If your Question has multiple answers, where some or all of the multiplicities have a variation, for e.g. the Question "Name two colours in the New Zealand flag ", will have the answer "Blue, Red",  "Blue, White", or "White, Red" (irrespective of order), you can separate the multiple answers with a comma, and each variation with a forward slash:
`Clue_Question@Clue_Type@Multiple_1_Possible_Clue_Answer_1/Multiple_1_Possible_Clue_Answer_2,Multiple_2_Possible_Clue_Answer_1/Multiple_2_Possible_Clue_Answer_2`

### Dependencies:

To play the game you need Java version 14 and JavaFX relevant to it.
Also, festival will be needed as well. After installing festival, install the New Zealand festival voices at `/usr/share/festival/voices/english/`
The relevant zip files have been provided along with the `festivalNZVoiceSetup.sh` script file.
Keep the script file along with the zip files ("akl_nz_jdt_diphone.zip" and "akl_nz_cw_cg_cg.zip") in the same directory and open the terminal there. Then run:


`./festivalNZVoiceSetup.sh`


(In case it doesn't work you will need to give it executable rights by running the following command in the terminal: `chmod +x festivalNZVoiceSetup.sh`)
