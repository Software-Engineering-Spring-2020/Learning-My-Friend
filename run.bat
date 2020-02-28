@echo off
javac -cp "./src/.;src/core.jar;src/javamp3-1.0.4.jar;src/jsyn-20171016.jar;src/sound.jar" -d bin/ @sources
cd bin
java -cp ".;./core.jar;./javamp3-1.0.4.jar;./jsyn-20171016.jar;./sound.jar" LearningMyFriend #This must be run from bin folder
cd ..
PAUSE
