# Arkanoid

A Java implementation of the classic **Arkanoid / Breakout** game.

Control the paddle, keep the balls in play, clear every block, and progress through increasingly challenging levels.

## Features

* Four playable levels with different block layouts and backgrounds
* Multiple balls with level-specific velocities
* Paddle controls using the arrow keys
* Block collision detection and removal
* Score tracking, including bonus points for clearing a level
* Countdown before each level starts
* Pause screen — press `P` during gameplay
* Win and Game Over screens
* Level progression with a persistent score throughout the session

## How to Play

Use the **Left** and **Right Arrow** keys to move the paddle.

Keep the balls from falling below the paddle and destroy every block to advance to the next level.

* Press `P` to pause
* Press `Space` to continue from pause and end screens

## Run the Game

Run `ArkanoidGame` to start the game.


## Project Structure

* `src/animation` — game loop, countdown, pause, and end screens
* `src/collision` — collision detection and game environment
* `src/game` — level setup, score management, and game flow
* `src/geometry` — points, lines, rectangles, and velocity
* `src/levels` — level layouts, backgrounds, and level configuration
* `src/sprites` — balls, blocks, paddle, and drawable game objects
* `src/listeners` — block removal, ball removal, and score events
* `src/indicators` — score and level-name display
