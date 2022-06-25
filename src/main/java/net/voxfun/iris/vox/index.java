package net.voxfun.iris.vox;

import com.google.gson.JsonObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.voxfun.iris.vox.commands.CapesMeCommand;
import net.voxfun.iris.vox.commands.LobbyCommand;
import net.voxfun.iris.vox.commands.PlayCommand;
import net.voxfun.iris.vox.commands.TabCompletion;
import net.voxfun.iris.vox.listeners.ChatListener;
import net.voxfun.iris.vox.listeners.JoinListener;
import net.voxfun.iris.vox.listeners.LeaveListener;
import net.voxfun.iris.vox.listeners.PlayerMoveListener;
import net.voxfun.iris.vox.managers.LobbySelector;
import net.voxfun.iris.vox.managers.ReadJsonURL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class index extends JavaPlugin {
    public static Plugin plugin = null;
    FileConfiguration config = getConfig();
    public static String uri = null;
    public static Logger logger = Bukkit.getLogger();
    public static MongoClient mongoClient = null;
    public static MongoDatabase database = null;
    public static String domain = "http://localhost";
    public static Boolean isMainLobby = true;
    public static String game = "1.18.2";
    public static String pluginVersion = "0.1";
    @Override
    public void onEnable() {
        plugin = this;
        if (config.get("database") == null) {
            config.addDefault("database", "null");
            config.addDefault("domain", "http://localhost");
            config.addDefault("lobby", true);
            config.options().copyDefaults(true);
            saveConfig();
        }
        uri = !config.getString("database").equals("null") && config.getString("database").startsWith("mongodb+srv://") ? config.getString("database") : null;
        domain = config.getString("domain");
        isMainLobby = config.getBoolean("lobby");
        if (!isMainLobby) {
            getCommand("lobby").setExecutor(new LobbyCommand());
            getCommand("lobby").setTabCompleter(new TabCompletion("lobby", true, new String[]{}));
        } else {
            getCommand("play").setExecutor(new PlayCommand());
            getCommand("play").setTabCompleter(new TabCompletion("play", false, new String[]{"recon"}));
            getCommand("capesme").setExecutor(new CapesMeCommand());
            getCommand("capesme").setTabCompleter(new TabCompletion("capesme", true, new String[]{}));
        }
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        loadDb();
        checkVersion();
        if (isMainLobby) {
            getServer().getPluginManager().registerEvents(new LobbySelector(null), this);
            getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        }
    }

    private static void loadDb() {
        if (uri == null) {
            logger.info("Could not load database, it's missing it's uri!");
            return;
        }
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase("capesme");
    }

    private static void checkVersion() {
        try {
            JsonObject version = ReadJsonURL.readJsonFromUrl(domain + "/version/voxfun.jar");
            if (!version.get("version").getAsString().equalsIgnoreCase(pluginVersion) && version.get("minecraft").getAsString().equalsIgnoreCase(game)) {
                logger.info(String.format("There is a new version for VoxFun! Download it here: %s/plugins/voxfun/%s/voxfun-%s.jar", domain, version.get("minecraft").getAsString(), version.get("version").getAsString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
    }
}
