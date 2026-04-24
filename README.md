## Intro
This is a simple game coded entirely in Java 21 (using JavaFX) as a school project. 
It is inspired by the board game "Magic maze" (simplified). It can be found at https://github.com/alickad/magic-maze
.
### How to run the program
- unzip downloaded folder or clone the repo with one of the following lines
```
git clone git@github.com:alickad/magic-maze.git
git clone https://github.com/alickad/magic-maze.git
```
- to start the game, open terminal, go to the main folder and run (you need Maven and Java)
```mvn javafx:run```
- to generate documentation of code, run
```mvn javadoc:javadoc```
The generated website can be found at 
```magic-maze/target/reports/apidocs/index.html```

## How it's intended to be played
This is a cooperative game for 4 players.
There are 4 heroes of different colors. Their job is to get each hero to the exit
of their color. The catch is - each player can move the heroes in only one direction.
The players should not communicate. They cannot talk to each other, however they can
tap each other on the head (sometimes your teammates might be really slow, so they'll need some encouragement).
You can see the controls on the right side panel.

You need to be fast, the time is limited! There are special tiles with an hourglass 
that resets the countdown. However, each of them can only be used once.

Right after starting the game, most of the board will be black. You need to move the heroes
to explore unexplored tiles and their contents.

Good luck!

### Customize!
Right on the first screen you can choose which keyboard keys 
control which movement of which hero. You can choose how big you want the board to be and how
much time you want to have per timer.

You can mix it up,
use different keys for different difficulty. Just try to
not have one key for more movements, that could make the game
behave weird. You can play in less or more players and 
agree on alternative rules of playing.
