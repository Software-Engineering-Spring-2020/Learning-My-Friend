@echo off
javac -cp "./src/.;src/core.jar;src/javamp3-1.0.4.jar;src/jsyn-20171016.jar;src/sound.jar;src/gst1.jar;src/yt.jar;src/jna.jar;src/video.jar" -d bin/ @sources
cd bin
java -cp ".;../lib/core.jar;../lib/javamp3-1.0.4.jar;../lib/jsyn-20171016.jar;../lib/sound.jar;../lib/gst1.jar;../lib/yt.jar;../lib/jna.jar;../lib/video.jar" LearningMyFriend -Dgstreamer.library.path=lib/windows32 #This must be run from bin folder
cd ..
PAUSE
