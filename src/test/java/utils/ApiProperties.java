package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiProperties {

    Properties prop = new Properties();
    File file = new File("apiProperties");

    public String getProp(String valor) {
        try {
            if (System.getProperty("properties") == null) {
                prop.load(new FileInputStream( file.getAbsolutePath() + "/dev.properties"));
            } else {
                prop.load(new FileInputStream(file.getCanonicalPath() + "/" + System.getProperty("properties")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(valor);
    }
}


