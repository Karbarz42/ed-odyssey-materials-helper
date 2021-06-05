package nl.jixxed.eliteodysseymaterials;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.enums.EngineerState;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Settings extends VBox {

    @FXML
    CheckBox checkBoxIrrelevant;
    @FXML
    CheckBox checkBoxUnlock;

    @FXML
    Label version;

    private final static Map<Engineer, EngineerState> ENGINEER_STATES = new HashMap<>();

    static {
        ENGINEER_STATES.put(Engineer.DOMINO_GREEN, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.HERO_FERRARI, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.JUDE_NAVARRO, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.KIT_FOWLER, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.ODEN_GEIGER, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.TERRA_VELASQUEZ, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.UMA_LASZLO, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.WELLINGTON_BECK, EngineerState.UNKNOWN);
        ENGINEER_STATES.put(Engineer.YARDEN_BOND, EngineerState.UNKNOWN);
    }


    public Settings() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        final String buildVersion = getBuildVersion();
        String latestVersion = "";
        try {
            latestVersion = getLatestVersion();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        if (getBuildVersion() == null) {
            this.version.setText("Version: dev");
        } else if (getBuildVersion().equals(latestVersion)) {
            this.version.setText("Version: " + latestVersion);
        } else {
            this.version.setText("Version: " + buildVersion + " (" + latestVersion + " available!)");
        }
    }

    public CheckBox getCheckBoxIrrelevant() {
        return this.checkBoxIrrelevant;
    }

    public CheckBox getCheckBoxUnlock() {
        return this.checkBoxUnlock;
    }

    public static boolean isEngineerKnown(final Engineer engineer) {
        final EngineerState engineerState = ENGINEER_STATES.get(engineer);
        return EngineerState.KNOWN.equals(engineerState) || isEngineerUnlocked(engineer);

    }

    public static boolean isEngineerUnlocked(final Engineer engineer) {
        final EngineerState engineerState = ENGINEER_STATES.get(engineer);
        return EngineerState.INVITED.equals(engineerState) || EngineerState.UNLOCKED.equals(engineerState);
    }

    public static void setEngineerState(final Engineer engineer, final EngineerState engineerState) {
        ENGINEER_STATES.put(engineer, engineerState);
    }

    private String getLatestVersion() throws IOException {
        final URL url = new URL("https://api.github.com/repos/jixxed/ed-odyssey-materials-helper/releases/latest");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        final InputStream responseStream = connection.getInputStream();
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode response = objectMapper.readTree(responseStream);
        return response.get("tag_name").asText();
    }

    public static String getBuildVersion() {
        return System.getProperty("app.version");
    }
}
