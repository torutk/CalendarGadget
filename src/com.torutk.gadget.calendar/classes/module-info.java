/*
 *  Copyright © 2018 toru
 */

module com.torutk.gadget.calendar {
    requires com.torutk.gadget.support;
    requires javafx.controls;
    requires java.logging;
    requires java.prefs;
    requires java.desktop;
    opens com.torutk.gadget.calendar to javafx.graphics;
}
