package net.voxfun.iris.vox.managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ReadJsonURL {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JsonObject readJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(readAll(rd));
            JsonObject rootobj = root.getAsJsonObject();
            return rootobj;
        }
    }
}
