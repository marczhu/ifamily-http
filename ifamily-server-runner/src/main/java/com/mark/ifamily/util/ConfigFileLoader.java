package com.mark.ifamily.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mark.zhu on 2016/10/13.
 */
public class ConfigFileLoader {
    private String uri;
    private Properties properties;

    private ConfigFileLoader(String uri) throws IOException {
        this.uri = uri;
        this.properties = loadProperties();
    }
    public static ConfigFileLoader newInstance(String uri) throws IOException {
        return new ConfigFileLoader(uri);
    }
    private Properties loadProperties() throws IOException {
        // lets try the thread context class loader first
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ConfigFileLoader.class.getClassLoader();
        }
        InputStream in = classLoader.getResourceAsStream(uri);
        if (in == null) {
            throw new IOException("Could not find factory class for resource: " + uri);
        }

        // lets load the file
        BufferedInputStream reader = null;
        try {
            reader = new BufferedInputStream(in);
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
    }

    public String getString(String key){
        return (String)properties.get(key);
    }

    public int getInt(String key){
        return new Integer((String)properties.get(key)).intValue();
    }

}
