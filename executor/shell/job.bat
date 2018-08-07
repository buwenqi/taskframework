@echo off
echo %cd%
cd shell
set input=%1%
echo %input%
echo hello
::ping 127.0.0.1 -n 20 >nul
javac Hello.java
java Hello
echo hello
exit