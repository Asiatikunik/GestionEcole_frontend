module fr.uvsq.isty {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires javafx.graphics;
    requires javafx.base;
    opens fr.uvsq.isty.gestionecole to javafx.fxml;
    opens fr.uvsq.isty.gestionecole.controleurs to javafx.fxml;
    exports fr.uvsq.isty.gestionecole;
    exports fr.uvsq.isty.gestionecole.controleurs;
    exports fr.uvsq.isty.gestionecole.modeles;
    requires com.google.gson;
    requires java.net.http;
    opens fr.uvsq.isty.gestionecole.modeles to com.google.gson;
}