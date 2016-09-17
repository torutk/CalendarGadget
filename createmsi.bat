@echo off

javapackager -deploy -native msi ^
-v ^
-outdir dist -outfile Calendar ^
-srcdir dist -srcfiles Calendar.jar ^
-appclass calendar.Calendar ^
-name "Calendar" ^
-BappVersion=0.2.0 ^
-title "Calendar" ^
-vendor Takahashi ^
-description "Calendar on desktop"

