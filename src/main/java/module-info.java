module record_app_coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires org.apache.logging.log4j;

    opens ui to javafx.fxml;
    opens ui.serviceController to javafx.fxml;
    opens models to javafx.base;

    exports mainPackage;
    exports models;
    exports ui;
    exports ui.serviceController;
    exports repository;
    exports utils;
    exports commands;
}