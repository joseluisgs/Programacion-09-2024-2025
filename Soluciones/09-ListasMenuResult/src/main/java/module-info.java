module dev.joseluisgs.listasmenuresult {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires logging.jvm;
    requires open;
    requires org.slf4j;
    requires kotlin.result.jvm;


    opens dev.joseluisgs.listasmenuresult to javafx.fxml;
    exports dev.joseluisgs.listasmenuresult;

    opens dev.joseluisgs.listasmenuresult.controllers to javafx.fxml;
    exports dev.joseluisgs.listasmenuresult.controllers;
}