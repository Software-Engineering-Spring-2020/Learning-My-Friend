@echo off
cd src
javadoc -sourcepath . -cp "./src/.;src/core.jar;src/javamp3-1.0.4.jar;src/jsyn-20171016.jar;src/sound.jar" -d ../docs -subpackages frontend -subpackages backend
cd ..
PAUSE
