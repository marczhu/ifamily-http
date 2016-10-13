package com.mark.ifamily.util;


import java.util.*;

public class Options {

    private LinkedHashMap<String, Object> optionsMap = new LinkedHashMap<String, Object>();

    private List<String> args = new ArrayList<String>();

    private Options(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String key = args[i];
            if (key.startsWith("--")) {
                if (i + 1 >= args.length) {
                    throw new IllegalArgumentException("option needs a value: " + key);
                } else {
                    key = key.substring(2);
                    String value = args[i + 1];
                    optionsMap.put(key, value);
                }
            } else if (key.startsWith("-")) {
                key = key.substring(1);
                int pos = key.indexOf("=");
                if (pos != -1) {
                    optionsMap.put(key.substring(0, pos), key.substring(pos + 1));
                } else {
                    optionsMap.put(key, true);
                }
            } else {
                this.args.add(key);
            }
        }
    }
    public static Options getOptions(final String[] args) {
       return new Options(args);
    }

    public Object get(String key) {
        return optionsMap.get(key);
    }

    public Object remove(String key) {
        return optionsMap.remove(key);
    }

    public boolean hasOption(String key) {
        Object v = optionsMap.get(key);
        if (v instanceof Boolean) {
            return (Boolean) v;
        } else {
            return false;
        }
    }

    public void put(String key, Object value) {
        optionsMap.put(key, value);
    }

    public List<String> getArgs() {
        return args;
    }

    public Map<String, Object> getOptionsMap() {
        return optionsMap;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Options{");
        for (Iterator<String> it = optionsMap.keySet().iterator(); it.hasNext();){
            String key = it.next();
            sb.append(key).append("=").append(optionsMap.get(key));
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        sb.append('}');

        return sb.toString();
    }




    public static void main(String[] args) {
        String command = "--start server -console -ins=instance_name -server.port=10999";
        Options options = Options.getOptions(command.split(" "));
        System.out.println(options.toString());
    }
}
