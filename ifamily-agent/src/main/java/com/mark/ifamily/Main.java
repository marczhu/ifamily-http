package com.mark.ifamily;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mark.zhu on 2016/10/14.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String USUAGE="start key=xxx|put key=xxx|get key=xxx|list|clear ";
    public static void main(String[] args) {
        System.out.println(USUAGE);
        CommandExecutor commandExecutor = new CommandExecutor();
        if (args != null && args.length > 0) {
            if ("start".equalsIgnoreCase(args[0])) {
                commandExecutor.start(getKey(args));
            } else if ("put".equalsIgnoreCase(args[0])) {
               String result = commandExecutor.put(getKey(args));
                LOGGER.info("result:" + result);
            } else if ("get".equalsIgnoreCase(args[0])) {
                String result = commandExecutor.get(getKey(args));
                LOGGER.info("result:" + result);
            } else if ("list".equalsIgnoreCase(args[0])) {
                String result = commandExecutor.list();
                LOGGER.info("result:" + result);
            } else if ("clear".equalsIgnoreCase(args[0])) {
                commandExecutor.clear();
            } else {
                LOGGER.error("unsupported command:" + args[0]);
                System.exit(-1);
            }
        } else {
            commandExecutor.start(null);
        }
    }

    private static String getKey(String[] args) {
        if (args.length > 1) {
            if (args[1].startsWith("key=")) {
                return args[1].substring(args[1].indexOf("=") + 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
