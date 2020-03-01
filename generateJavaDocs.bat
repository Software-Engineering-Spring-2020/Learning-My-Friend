@echo off
cd src
javadoc -sourcepath . -cp ".;core.jar;javamp3-1.0.4.jar;jsyn-20171016.jar;sound.jar;gst1.jar;yt.jar;jna.jar;video.jar" -d ../docs -subpackages frontend -subpackages backend
cd ..
PAUSE
