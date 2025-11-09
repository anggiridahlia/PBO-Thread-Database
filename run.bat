@echo off
echo ===========================================
echo   ☕ Menjalankan Program Kedai Kopi Kampus ☕
echo ===========================================
echo.

set CLASSPATH=lib\mysql-connector-j-9.5.0.jar;src
javac -cp %CLASSPATH% src\CoffeeShop.java
java -cp %CLASSPATH% CoffeeShop

pause
