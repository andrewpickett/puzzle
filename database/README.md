# puzzle data model

The data model basically allows each question to have any number of "correct" answers, each one allowing the user to be presented with a different puzzle upon completion. This allows for the "choose your own adventure" type of play. The answers are based on regular expressions and can be "normalized" so that it ignores things like user capitalization or punctuation or anything like that.

Along the way, the user will earn points for how well they answered the question (Did they use any hints? Did they enter any wrong answers? Did it take them a very long time to answer it?). It will keep track of these points, so they can be used however you want. You can even decide to show the running total and progress on the screen to the user or not through configuration.

## ER Diagram

![][er_diagram]

A very simple data model. We have user accounts, puzzles for each user, hints and possible correct answers for each puzzle, and we keep track of all guesses the user makes. Nothing complicated. 

[er_diagram]: ./er_diagram.png
