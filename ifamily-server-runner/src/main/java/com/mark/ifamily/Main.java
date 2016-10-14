package com.mark.ifamily;

import com.mark.ifamily.util.ConfigFileLoader;
import com.mark.ifamily.util.Options;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mark.zhu on 2016/10/13.
 */
public class Main {
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static String CONFIG_FILE_DEFAULT_URI = "config.properties";
    public static void main(String[] args) {
        Server server = null;
        try {
            String uri = CONFIG_FILE_DEFAULT_URI;
            LOGGER.info("Starting server...");
            ConfigFileLoader configFileLoader = ConfigFileLoader.newInstance(uri);
            server = new Server(configFileLoader.getInt("server.port"));
            WebAppContext context = new WebAppContext();
            context.setContextPath(configFileLoader.getString("context.path"));
            context.setWar(configFileLoader.getString("war.file.path"));
            server.setHandler(context);
            server.start();
        } catch (Exception e) {
            LOGGER.error("Server start failed!", e);
            try {
                if (server != null) {
                    server.stop();
                    LOGGER.info("Server is stopped!");
                }
            } catch (Exception e1) {
                LOGGER.error("Server stopped failed!",e1);
            }
        }
        try {
            server.join();
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }
        LOGGER.info("Server is started!");

    }
}
