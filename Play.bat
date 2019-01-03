@echo off
copy jansi-1.17.1.jar src\chess\
javac -cp jansi-1.17.1.jar src\chess\*.java
cd src
java -cp jansi-1.17.1.jar; chess/ChessDriver