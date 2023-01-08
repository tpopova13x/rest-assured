package data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class PropertiesSupplier {
    private static final Log log = LogFactory.getLog(PropertiesSupplier.class);
    public static Properties prop;
    static{
        try (InputStream input = PropertiesSupplier.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop = new Properties();
            if (input == null) {
                log.error("Sorry, unable to find properties");
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
