package utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvReader {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static String get(String key) {
        String val = dotenv.get(key);
        if (val == null) throw new RuntimeException("Missing env variable: " + key);
        return val;
    }

    public static String get(String key, String defaultVal) {
        return dotenv.get(key, defaultVal);
    }

    public static boolean getBoolean(String key, boolean defaultVal) {
        String val = dotenv.get(key);
        return val != null ? Boolean.parseBoolean(val.trim()) : defaultVal;
    }
}