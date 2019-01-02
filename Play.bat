@echo off
copy jansi-1.17.1.jar src\chess\
javac -cp jan*.jar src\chess\*.java src\exceptions\*.java
cd src
java -cp jansi-1.17.1.jar; chess/ChessDriver
