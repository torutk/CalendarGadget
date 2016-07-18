# calendar
JavaFX calendar program to alternate for a desktop calendar gadget

![Calendar View](http://cdn-ak.f.st-hatena.com/images/fotolife/t/torutk/20160717/20160717202502.png)

## how to build

This repository has a source projects of Calendar program to build with NetBeans IDE 8.1 and Java SE 8.

## built binary

Some releases have built binary installer files for Windows OS.
These installers are self-contained application installable packages, so no JDK or JRE required.

## how to run

### after build

- execute an executable jar for all platform. (required JDK or JRE 8+)

### after install of self-contained applicaiton package(MSI)

- execute a short-cut icon or a native executable file

### command-line options

The command-line options are available as follows.

|Configuration item |option name | examples |
|---|---|---|
|top left x coordinate | x     | --x=800 |
|top left y coordinate | y     | --y=60  |
|calendar width        | width | --width=320 |
|calendar height       | height| --height=300 |

### configuration file for a self-contained application package(MSI)

The configuration file 'Calendar.cfg' is in 'app' folder in the installed folder.
JVM options and command-line options are descriptable in the file.


