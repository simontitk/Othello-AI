# Othello-AI

## Background

This is our group project for the course Introduction to Artificial Intelligence at the IT University of Copenhagen. 
It includes a game-playing agent which uses the [MiniMax](https://en.wikipedia.org/wiki/Minimax) search algorithm with [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) to navigate the game tree and find the optimal moves.

## Setup

To run the project:

- Clone the repository using `git clone https://github.com/simontitk/Othello-AI`.

- Compile the .java source files using `javac -cp . -d ./out  *.java`

- Start the game (vs. the AI agent) using `java -cp ./out Othello human HooliganAI <boardsize>` (`<boardsize>` can be substituted for any even number above 4)

- try to win!
