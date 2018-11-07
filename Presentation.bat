@echo off
copy jansi-1.17.1.jar src\
javac -cp jan*.jar src\*.java
cd src
java -cp jansi-1.17.1.jar; ChessDriver