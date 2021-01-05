# Project_DA

Readme file for the report of the project

## Description of the app
In this app you control a gray circle (the player) using the orientation sensor of your Android device. There is no goal but you can score points by moving over the target (big white circle), the scores is printed onto the screen at the top and is redrawn every time you score a point. You can stop playing by pressing the little black square in the top left corner of the screen, it is the pause button. By pressing this button you are redirected to another activity which will ask for your name to save your score and your name together. You can then see your score in the leaderboard section.

## Activities
This application is made of 4 different activities.
* The main activity is the first activity that appears on the screen. It contains 2 buttons : one to start the game and the other to take a look at the leaderboard.
* The game activity is the part of the app that manage the sensor. It receive the data from the sensor and sends it to the game view to move the player accordingly.
* The leaderboar activity lists the scores previously saved in the designated file. It associates a player name with its score.
* The save activity is responsible for saving the scores. It gets the name of the user and stores it and its score in a file.

## Intents
An intent is created before showing an activity. Most of them are simple intents except for :
* When the player decides to stop the game, an intent is created to start the save activity but a bundle with the score value is attached to it.
* An intent is created to download the scores file from the internet in the background

## Background service
Initially the scores are stored in a file on github, the downloading process take take some time so it is done using a background service to allow the application to run smoothly even when downloading a big file. In this case the file is not that big and it could have been dowloaded without the background service.

## Sensor used
The only sensor used in this application is the orientation sensor. It captures the device orientation in all 3 axis though we are using only 2 of these axis in this app.

## Group
Group of 1 : Mathis TEIXEIRA mathis.teixeira1@etu-cyu.fr
