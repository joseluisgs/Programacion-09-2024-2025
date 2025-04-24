module dev.joseluisgs.demofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens dev.joseluisgs.demofx to javafx.fxml;
    exports dev.joseluisgs.demofx;

    // Controladores
    opens dev.joseluisgs.demofx.controllers to javafx.fxml;
    exports dev.joseluisgs.demofx.controllers;
}