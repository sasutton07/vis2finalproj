# vis2finalproj
## Cribbage

This project allows a user to play a game of cribbage. To begin run the welcome window class and select new game. This will show you a message and then pop up a new cribbage window. The plyaer to get 121 points first wins. More detailed information about the rules of the game can be found in the How To Play window found from the welcome window.

## Contents
pom.xml --
src /   --
  main/
    java/
      com/
        mycompany/
          finalprojectvis2/
            Card.java
            CribbageWindow.form
            CribbageWindow.java
            Deck.java
            Hand.java
            HowToWindow.form
            HowToWindow.java
            Image.java
            NewDeck.java
            Player.java
            SingleCard.java
            WelcomeWindow.form
            WelcomeWindow.java
    resources/
      backOfDeck (4) 10.25.33 PM.jpeg
      243133.jpeg
      1804142.png
      backOfDeck (4) 10.25.33 PM_1.jpeg
      pngimg.com - cards_PNG8474.jpeg
      transparent_icons_222.jpeg
      
  test/
    java/
files/  --
target/ --
nbactions.xml --

## Building
You must have maven installed
```bash
mvn package
```
## Running
make sure that you are inside of the finalprojectvis2 folder
```bash
javac **/*.java
java WelcomeWindow.java
```
the first line will compile all of the files and the second will run the welcome window

deck of cards api was used -- https://deckofcardsapi.com/ 
