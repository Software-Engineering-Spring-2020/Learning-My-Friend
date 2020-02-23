@echo off
cd src
javadoc -sourcepath . -cp core.jar -d ../docs -subpackages frontend -subpackages backend
cd ..
PAUSE