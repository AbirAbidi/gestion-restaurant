import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadConfig() {
        Properties props = new Properties();
        try {
            // the FileInputStream function is used to obtain input bytes from a file in the file sys
            props.load(new FileInputStream("config.properties"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
