@echo off
:: BatchGotAdmin
:-------------------------------------
REM  --> Check for permissions
    IF "%PROCESSOR_ARCHITECTURE%" EQU "amd64" (
>nul 2>&1 "%SYSTEMROOT%\SysWOW64\cacls.exe" "%SYSTEMROOT%\SysWOW64\config\system"
) ELSE (
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
)

REM --> If error flag set, we do not have admin.
if '%errorlevel%' NEQ '0' (
    echo Requesting administrative privileges
    echo Needed to place chess command in path
    pause
    goto UACPrompt
) else ( goto gotAdmin )

:UACPrompt
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
    set params= %*
    echo UAC.ShellExecute "cmd.exe", "/c ""%~s0"" %params:"=""%", "", "runas", 1 >> "%temp%\getadmin.vbs"

    "%temp%\getadmin.vbs"
    del "%temp%\getadmin.vbs"
    exit /B

:gotAdmin
    pushd "%CD%"
    CD /D "%~dp0"
:--------------------------------------    
SET /P chessDir=Please enter your chess directory:  
SET "chessDir=%chessDir:/=\%"
cd %chessDir%
echo./test>>.gitignore
mkdir test
Start /B "" bitsadmin /transfer jd /download /priority high http://repo1.maven.org/maven2/org/fusesource/jansi/jansi/1.17.1/jansi-1.17.1.jar %chessDir%\test\jansi.jar
echo.copy %chessDir%\src\* %chessDir%\test\>chess.cmd
echo.cd %chessDir%>>chess.cmd
echo.javac -cp test\jansi.jar test\*.java>>chess.cmd
echo.cd test>>chess.cmd
echo.java -cp jansi.jar; ChessDriver>>chess.cmd
move chess.cmd "C:\Program Files\git\cmd"