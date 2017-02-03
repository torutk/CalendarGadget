@echo off

"%JAVA_HOME%"\bin\javapackager -deploy -native msi ^
-v ^
-outdir dist -outfile Calendar ^
-srcdir dist -srcfiles Calendar.jar ^
-appclass calendar.Calendar ^
-name "Calendar" ^
-BappVersion=0.3.1 ^
-title "Calendar" ^
-vendor Takahashi ^
-description "Calendar on desktop"

