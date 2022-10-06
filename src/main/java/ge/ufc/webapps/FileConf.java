package ge.ufc.webapps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class FileConf {
    private static final Logger logger = LogManager.getLogger();
    private volatile static FileConf conf;
    private static final String CONFIG_FILE_LOCATION = "config.properties";
    private static URL url;
    protected long lastModified;
    private static String password;
    private static String username;
    private static String database;
    private static String[] api;


    private FileConf(URLConnection conn) throws IOException
    {
        this.lastModified = conn.getLastModified();
        try (InputStream in = conn.getInputStream())
        {
            Properties props = new Properties();
            props.load(conn.getInputStream());
            logger.info("configuration reloaded");
            username = props.getProperty("username","");
            password = props.getProperty("password","");
            database = props.getProperty("database","");
            api = props.getProperty("allowed_ip","").split(";");
        }
    }
    public static FileConf getFileConf() throws IOException
    {
        if (url == null)
            url = FileConf.class.getClassLoader().getResource(CONFIG_FILE_LOCATION);
        if (url == null)
            throw new IOException("file not found");
        URLConnection conn = url.openConnection();
        long lastModified = conn.getLastModified();
        if (conf == null || lastModified > conf.lastModified) {
            synchronized (CONFIG_FILE_LOCATION) {
                if (conf == null || lastModified > conf.lastModified) {
                    conf = new FileConf(conn);
                }
            }
        }

        return conf;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getDatabase() {
        return database;
    }

    public static String[] getApi() {
        return api;
    }
}