@echo off
rem Version: 1.1
rem Date:   24 Sep 2013
rem Author: Shawky
rem Refer to HELP: for info

SET XC=xcopy /D /Y /V /F /I
SET PROGDIR=D:\Development\Go Pro Demo (MQ4 Testing)
SET DSTPATH=%PROGDIR%\experts

SET SIMPATH1=G:\Apps\MT4\BackTest IC (Recent)\experts
SET SIMPATH2=G:\Apps\MT4\BackTest IC (All)\experts
SET SIMPATH3=G:\Apps\MT4\BackTest Go (All)\experts
SET DEPLOYPATH=D:\Development\Deployment\experts

SET SRCPATH=%1
SET SRCPATH=%SRCPATH:"=%
IF "%SRCPATH%"=="" (
    SET SRCPATH=[Arg1]
)

SET APPNAME=%2
SET APPNAME=%APPNAME:"=%
IF "%APPNAME%"=="" (
    SET APPNAME=[Arg2]
)

SET SRCFILE=%APPNAME%.mq4
SET DSTFILE=%APPNAME%.ex4


SET CMD="%PROGDIR%\metalang.exe" "%SRCFILE%" "%DSTFILE%"

IF "%SRCPATH%"=="[Arg1]"  GOTO HELP
IF "%APPNAME%"=="[Arg2]"  GOTO HELP

cd %SRCPATH%

IF NOT EXIST "%SRCFILE%"  (
    SET ERROR=Error: File "%SRCFILE%" does not exist in %SRCPATH%
    GOTO HELP
)

echo .
echo Compiling %SRCFILE% to %DSTPATH%\%DSTFILE%
echo .
DEL *.log
%CMD%
IF EXIST "%DSTFILE%" (
    echo .
    echo Distributing executable to SIM and Deployment paths
    %XC% "%DSTFILE%" "%DSTPATH%\"
    IF EXIST "%SIMPATH1%"   %XC% "%DSTFILE%" "%SIMPATH1%\"
    IF EXIST "%SIMPATH2%"   %XC% "%DSTFILE%" "%SIMPATH2%\"
    IF EXIST "%SIMPATH3%"   %XC% "%DSTFILE%" "%SIMPATH3%\"
    IF EXIST "%DEPLOYPATH%" copy /B /Y "%DSTFILE%" "%DEPLOYPATH%\%APPNAME% (Dev).ex4"
)

goto END

:HELP
echo .  Metatrader 4 Command Line utility for compiling MT4 programmes.
echo .
echo .  This batch files allows MT4 applications to be compiled from a directory other than .\experts.
echo .  The output will be copied to experts after compilation.
echo .
echo .  [Arg1] = Path to MT4 application directory
echo .  [Arg2] = Name (without extension) of the main MQ4 source code to compile.
echo .
echo .  Example:
echo .      MT4Compile.bat "D:\Development\MQ4\MyExpert\" "PrimaryMQ4FileName"
echo .
echo .  Programme Directory: %PROGDIR%
echo .  Source Path:         %SRCPATH%
echo .  Source File:         %SRCFILE%
echo .  Destination File:    %DSTFILE%
echo .  Target Path:         %DSTPATH%
echo .
echo .  Argument 1:          %SRCPATH%
echo .  Argument 2:          %APPNAME%
echo .
echo .  Commands to execute would be:
echo .
echo .      %CMD%
echo .      %XC% "%DSTFILE%" "%DSTPATH%\"
echo .
echo .  %ERROR%
echo .

pause

:END