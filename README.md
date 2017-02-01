# Street Fighter: Java Edition <img src=https://github.com/theRoughCode/StreetFighterJava/blob/master/src/sflogo.png width="70">
![Home Screen](https://github.com/theRoughCode/StreetFighterJava/blob/master/screenshots/main_screen.PNG "Home Screen")
A high school project where I attempted to recreate the Street Fighter game in Java.  This project was super fun, and this was where I learnt:
- Frames
- Animating graphics
- Incorporating audio and in-game commands
- Game Mechanics (i.e. hit box, attack animation length)

There are a lot of other cool stuff that can yet be implemented, which is located in [TODO](https://github.com/theRoughCode/StreetFighterJava/blob/master/TODO).

![](https://github.com/theRoughCode/StreetFighterJava/blob/master/screenshots/loading_screen.PNG "Loading Screen")

## In-Game Menus
Players get to choose from different stages and characters.  For the project, I only implemented two characters as each character took a long time to incorporate into the game.  Here are some of the things that were needed to be done for each character implementation:
- Parsing sprite from spritesheet
- Animating sprites by frame
- Create unique hit box for each attack
- Animate jumping animation (up, forward, back)

![](https://github.com/theRoughCode/StreetFighterJava/blob/master/screenshots/stage_select.PNG "Stage Select") ![](https://github.com/theRoughCode/StreetFighterJava/blob/master/screenshots/champ_select.PNG "Character Select")

## Battle Scene
The interface layout for the battle scene is minimalistic as it was the least of my prioirities.  I figured that as long as the game mechanics were in place and the main menus looked good, that the lacklustre UI for the battle scene would be overlooked.  Healthbars and timers aren't too fancy and the font for player names aren't really pleasing to the eye.  The UI for the battle scene was one of the last things implemented in this project.
![](https://github.com/theRoughCode/StreetFighterJava/blob/master/screenshots/battle_scene.png "Battle Scene")
