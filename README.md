# Memory Game : Flip & Match Cards

A simple memory match game built with `Java`, following `MVC` architecture and `State Machine` approach, where you try to find matching pairs of cards among a shuffled deck of `4*n**2` cards, n increases in higher game levels.

## Table of Contents

* [Getting Started](#getting-started)
* [Gameplay](#gameplay)
* [Features](#features)
* [Author](#author)

## Getting Started

To run the game, simply clone this repository and open it in your preferred Java IDE. Make sure you have the necessary Java libraries installed.

### Prerequisites

* `Java Development Kit (JDK)`: You need to have JDK 8 or later installed on your system to compile and run the game.
* `Java IDE`: You need a Java Integrated Development Environment (IDE) such as Eclipse, NetBeans, or IntelliJ IDEA to open and run the project.
* `VSCode` : For running on VSCode, you need to have the `Java Extension Pack` installed in VSCode to support Java development.
* `Git`: You need to have Git installed on your system to clone the repository.

### Installing

```java
// Clone the repository
git clone 'https://github.com/Swarnotaj003/Memory-Game---Flip-and-Match-Cards'

// Open the project folder in your Java IDE
```

## Gameplay

- Run the game by executing the `Main` class of the `MemoryMatchGame`(project) folder
- Set the gameplay by entering your preferred `game level`
- A deck of shuffled cards is presented, all facing down
- Enter the addresses of two cards to flip over
- If you find a match, you will keep the cards
- If you don't find a match, the cards will flipped back
- Matched or not don't worry, carry on remembering & flipping
- The play continues until the last pair remains to be matched
- Lesser the number of attempts, better the score

## Features

- Built with `Java` following the `Model-View-Controller (MVC)` architecture
- Use of `OOP` concepts for modelling the software
- Use of `State Machine` approach to control the program flow
- Runs on `Command Line Interface (CLI)`
- Use of `Random` class to shuffle the deck of cards
- Use of `sleep()` and `flush()` methods to ensure limited duration for which the selected cards will be faced up

## Author

[Swarnotaj003](https://github.com/Swarnotaj003)