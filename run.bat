@echo off
javac -cp "./src/.;src/core.jar;src/javamp3-1.0.4.jar;src/jsyn-20171016.jar;src/sound.jar;src/gst1.jar;src/yt.jar;src/jna.jar;src/video.jar" -d bin/ @sources
cd bin
java -cp ".;./core.jar;./javamp3-1.0.4.jar;./jsyn-20171016.jar;./sound.jar;./gst1.jar;./yt.jar;./jna.jar;./video.jar" LearningMyFriend -Dgstreamer.library.path=src/windows32 #This must be run from bin folder
cd ..
PAUSE
