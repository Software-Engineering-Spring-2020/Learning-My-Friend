make:
	javac -cp src/core.jar -d bin/ src/BasicInteraction.java
run:
	java -cp ".:bin/core.jar" bin/BasicInteraction
runWin:
	cd bin
	java -cp ".;./core.jar" BasicInteraction
	cd ..

