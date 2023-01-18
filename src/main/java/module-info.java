module com.example.oopproject {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.oopproject.gui to javafx.fxml;
    exports com.oopproject.gui;
    exports com.oopproject.world.animals;
    exports com.oopproject.world.map.locations;
    exports com.oopproject;
    opens com.oopproject to javafx.fxml;
    exports com.oopproject.world;
    exports com.oopproject.world.factories;
    exports com.oopproject.world.interfaces;
    exports com.oopproject.world.map;
    opens com.oopproject.world.map to javafx.fxml;
}