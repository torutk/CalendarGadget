@echo off

REM increment the version number each creation.
set APP_VERSION=0.6.2

"%JAVA_HOME%"\bin\jpackage ^
--type msi ^
--win-upgrade-uuid f849420c-ec88-42b0-bc38-20fda41cc51d ^
--win-menu-group "High Bridge" ^
--win-menu ^
--win-shortcut ^
--app-version %APP_VERSION% ^
--description "Calendar on desktop" ^
--name "CalendarGadget" ^
--dest build\installer ^
--vendor "High Bridge" ^
--module-path build\libs;javafx-gadgetsupport\build\libs ^
--module com.torutk.gadget.calendar ^
--java-options "-Xms32m -Xmx64m -Xss256k -XX:TieredStopAtLevel=1 -XX:CICompilerCount=2 -XX:CompileThreshold=1500 -XX:InitialCodeCacheSize=160k -XX:ReservedCodeCacheSize=32m -XX:MetaspaceSize=12m -XX:+UseSerialGC" ^
--verbose




