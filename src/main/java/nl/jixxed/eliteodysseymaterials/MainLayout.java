package nl.jixxed.eliteodysseymaterials;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainLayout  extends VBox {

    public MainLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainLayout.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
