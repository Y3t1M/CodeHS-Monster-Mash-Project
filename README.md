Text Monster Game README
Overview
Text Monster Game is a console-based adventure game written in Java. Players navigate through a series of floors, each filled with monsters, treasures, and mysteries. The objective is to defeat monsters, collect items, and ultimately conquer the boss to win the game.

Features
Multi-Floor Exploration: Players can explore multiple floors, each with its unique layout and challenges.
Inventory Management: Players can collect items such as swords and magic stones to aid in their quest.
Combat: Encounter and fight monsters using items collected along the way.
Secrets: Discover secret floors and items that can turn the tide in challenging battles.


Requirements
Java Development Kit (JDK) to compile and run the game.
A console or terminal to execute the game.
How to Run
Ensure Java is installed on your system.
Compile the game using javac TextMonsterGame.java from the command line.
Run the game using java TextMonsterGame.


Gameplay Instructions
At the start of the game, the player is placed in the center of the first floor.
Players navigate the dungeon using the commands: left, right, up, down.
Use the grab command to pick up items located on the floor.
Encounter monsters and use the fight command to engage in battle. Winning fights might require specific items.
To quit the game at any time, enter the quit command.


Game Commands
left/right/up/down: Moves the player in the specified direction.
grab: Picks up an item from the current tile.
fight: Engages in combat with a monster if present on the tile.
quit: Exits the game.


Implementation Details
The game world is represented as a three-dimensional array, with each floor being a two-dimensional grid of tiles.
Player movement and interactions are handled through a switch-case statement that processes user input.
The game tracks player inventory, defeated monsters, and game state to ensure progress and provide challenges.


Developer Notes
The game can be easily extended with more floors, items, and monster types to increase complexity and replayability.
Future enhancements could include a graphical user interface, more detailed combat mechanics, and a story element to provide context for the adventure.


Disclaimer
This game is a simple project intended for educational purposes. Enjoy exploring the dungeons and battling the monsters within!
