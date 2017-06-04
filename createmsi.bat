@echo off

"%JAVA_HOME%"\bin\javapackager -deploy -native msi ^
-v ^
-outdir dist -outfile CalendarGadget ^
-srcdir dist -srcfiles CalendarGadget.jar;lib\GadgetSupport.jar ^
-appclass com.torutk.gadget.calendar.CalendarGadgetApp ^
-name "Calendar" ^
-BappVersion=0.4.0 ^
-title "Calendar" ^
-vendor Takahashi ^
-description "Calendar on desktop"

