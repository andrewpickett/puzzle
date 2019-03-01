# puzzle

## Overview
This is a simple web-based puzzle game that I created for my daughters. The idea is they are simply presented with a "puzzle" and have to answer it correctly to move on. These puzzles could consist of questions, riddles, or brain teasers. It might also involve them needing to do research, travel to locations, talk with specific people, etc. Think of it as a combination of a trivia game, choose-your-own-story game, and geocaching.  

My aim with this is that it can continue to grow WITH them and get harder and harder and be more and more tailored to their individual interests. Eventually I foresee them needing to travel to multiple locations to gather different pieces of a clue to then decipher and figure out the solution to move on.

## But why?
I'm writing this application for them because I think it could be fun for them as they grow up, but I'm also using it as my way of staying up to date on full-stack development.

I initially started it because I wanted to learn Vue.js (and webpack, and node, and all the front-end frameworks) as well as JPA. So the initial version was written with the following:

**Front End**
* npm
* Vue.js
* webpack
* Bootstrap 4

**Back End**
* MySQL
* Java
* Spring-Boot (with Spring JPA)

**Misc**
* Maven
* Git (obviously)
* IntelliJ

I'll probably write a version in React and/or Angular soon...possibly rewrite the backend in Python or Go. As I write new implementations, I'll be adding them to this repo.

## Basics
The data model basically allows each question to have any number of "correct" answers, each one allowing the user to be presented with a different puzzle upon completion. This allows for the "choose your own adventure" type of play. The answers are based on regular expressions and can be "normalized" so that it ignores things like user capitalization or punctuation or anything like that.

Along the way, the user will earn points for how well they answered the question (Did they use any hints? Did they enter any wrong answers? Did it take them a very long time to answer it?). It will keep track of these points, so they can be used however you want. You can even decide to show the running total and progress on the screen to the user or not through configuration.

## Screenshots

### Login Screen
![][login]

Basic login page. Auth is done through JWT tokens.

### Puzzle Screen
![][puzzle]

Players are presented with the puzzle and an input for their answer. They can ask for hints, if there are any available. Along the bottom shows their progress (if enabled). This includes a bar showing the current maximum score they can get on this puzzle, their current total score, the puzzle number, and the total percentage of maximum points they have received up to this point.

### Puzzle Hints
![][hints]

If the user requests a hint, they are displayed below the input. The bar along the bottom indicating the points they can earn on this puzzle will be shortened and change color if too many points are lost. 

### Choices, choices!
![][choice]

Depending on the answer given, the game can go different directions. As such, the player must make decisions sometimes which can alter the flow of the game. Also the puzzle can contain markup. The password here was the same color as the background, and would only be visible if the player selected the text. 

[login]: ./doc/images/login.png
[puzzle]: ./doc/images/puzzle1.png
[hints]: ./doc/images/puzzle_hints1.png
[choice]: ./doc/images/puzzle_choice1.png
