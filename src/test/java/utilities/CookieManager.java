package utilities;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;

public class CookieManager {
    private final WebDriver driver;
    private final File cookieFile = new File("cookies.json");
    private static final long EXPIRY_DURATION_MS = 10 * 60 * 60 * 1000; // 10 hrs

    private static final Type COOKIE_TYPE = new TypeToken<Set<Map<String, Object>>>(){}.getType();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public CookieManager(WebDriver driver) {
        this.driver = driver;
    }

    public boolean loadCookiesIfValid() {
        if (!cookieFile.exists()) return false;

        if (Instant.now().toEpochMilli() - cookieFile.lastModified() > EXPIRY_DURATION_MS) {
            System.out.println("Cookies expired.");
            return false;
        }

        try (Reader reader = new FileReader(cookieFile)) {
            Set<Map<String, Object>> rawCookies = gson.fromJson(reader, COOKIE_TYPE);
            if (rawCookies == null || rawCookies.isEmpty()) {
                return false;
            }

            rawCookies.forEach(c -> {
                Cookie cookie = new Cookie.Builder((String) c.get("name"), (String) c.get("value"))
                        .domain((String) c.get("domain"))
                        .path((String) c.get("path"))
                        .expiresOn(c.get("expiry") != null ? new Date(((Double) c.get("expiry")).longValue()) : null)
                        .isSecure((Boolean) c.getOrDefault("isSecure", false))
                        .isHttpOnly((Boolean) c.getOrDefault("isHttpOnly", false))
                        .build();
                driver.manage().addCookie(cookie);
            });
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveCookies() {
        Set<Cookie> cookies = driver.manage().getCookies();
        if (cookies.isEmpty()) {
            return;
        }

        Set<Map<String, Object>> simplified = new HashSet<>();
        cookies.forEach(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", c.getName());
            map.put("value", c.getValue());
            map.put("domain", c.getDomain());
            map.put("path", c.getPath());
            map.put("expiry", c.getExpiry() != null ? c.getExpiry().getTime() : null);
            map.put("isSecure", c.isSecure());
            map.put("isHttpOnly", c.isHttpOnly());
            simplified.add(map);
        });

        try (Writer writer = new FileWriter(cookieFile)) {
            gson.toJson(simplified, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}