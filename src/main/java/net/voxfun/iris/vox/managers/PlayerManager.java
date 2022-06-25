package net.voxfun.iris.vox.managers;

import com.mongodb.client.model.Updates;
import org.bson.json.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.voxfun.iris.vox.index;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class PlayerManager {
    private static Map<Player, Team> playerTeamMap = new HashMap<>();

    public static void addPlayerData(Player player) {
        if (index.database == null) {
            index.logger.info("Database is not initialized.");
            return;
        }
        String uuid = player.getUniqueId().toString().replace("-", "");
        MongoCollection<Document> collection = index.database.getCollection("voxfuns");
        Document document = collection.find(Filters.eq("uuid", uuid)).first();
        if (document == null) {
            Document newDocument = new Document("_id", new ObjectId());
            newDocument.append("name", player.getName())
                    .append("uuid", uuid)
                    .append("removed", false)
                    .append("bans", new ArrayList<>())
                    .append("mutes", new ArrayList<>())
                    .append("statistics", new JsonObject("{}"))
                    .append("ranks", new JsonObject("{}"))
                    .append("activeRank", null)
                    .append("joinedOn", new Date())
                    .append("lastOnline", new Date())
                    .append("online", true);
            collection.insertOne(newDocument);
        }
    }
    public static Document getPlayerData(String uuid) {
        if (index.database == null) {
            index.logger.info("Database is not initialized.");
            return null;
        }
        uuid = uuid.replace("-", "");
        MongoCollection<Document> collection = index.database.getCollection("voxfuns");
        Document document = collection.find(Filters.eq("uuid", uuid)).first();
        return document;
    }
    public static void join(String uuid) {
        if (index.database == null) {
            index.logger.info("Database is not initialized.");
            return;
        }
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        uuid = uuid.replace("-", "");
        MongoCollection<Document> collection = index.database.getCollection("voxfuns");
        Document document = collection.find(Filters.eq("uuid", uuid)).first();
        if (document == null) return;
        if (player != null) {
            collection.updateOne(Filters.eq("uuid", uuid), Updates.combine(Updates.set("name", player.getName()),Updates.set("online", true)));
            joinRankTeam(player);
            player.setPlayerListName(String.format("%s%s", PlayerManager.getRank(player.getUniqueId().toString()), player.getName()));
        } else {
            collection.updateOne(Filters.eq("uuid", uuid), Updates.combine(Updates.set("online", true)));
        }
    }
    public static void leave(String uuid) {
        if (index.database == null) {
            index.logger.info("Database is not initialized.");
            return;
        }
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        uuid = uuid.replace("-", "");
        MongoCollection<Document> collection = index.database.getCollection("voxfuns");
        Document document = collection.find(Filters.eq("uuid", uuid)).first();
        if (document == null) return;
        collection.updateOne(Filters.eq("uuid", uuid), Updates.combine(Updates.set("lastOnline", new Date()), Updates.set("online", false)));
        if (player != null) {
            leaveRankTeam(player);
        }
    }
    public static String getRank(String uuid) {
        if (index.database == null) {
            index.logger.info("Database is not initialized.");
            return "";
        }
        uuid = uuid.replace("-", "");
        MongoCollection<Document> collection = index.database.getCollection("voxfuns");
        Document document = collection.find(Filters.eq("uuid", uuid)).first();
        if (document == null || document.get("activeRank") == null) {
            return "";
        }
        ChatColor rankColor = ChatColor.WHITE;
        if (document.get("rankColor") != null) {
            try {
                rankColor = ChatColor.valueOf(document.get("rankColor").toString().toUpperCase());
            } catch (IllegalArgumentException e) {
                index.logger.info(e.toString());
            }
        }
        return String.format("[%s] ", rankColor + document.get("activeRank").toString() + ChatColor.WHITE);
    }
    private static void joinRankTeam(Player player) {
        if (getRankTeam(player) != null) {
            getRankTeam(player).addEntry(player.getName());
        } else {
            Team team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(player.getUniqueId().toString());
            team.setPrefix(PlayerManager.getRank(player.getUniqueId().toString()));
            team.addEntry(player.getName());
            playerTeamMap.put(player, team);
        }
    }
    private static void leaveRankTeam(Player player) {
        Team team = getRankTeam(player);
        if (team == null) {
            index.logger.info(String.format("Prefix team is invalid: %s", player.getUniqueId()));
        } else {
            team.unregister();
            playerTeamMap.remove(player);
        }
    }
    public static Team getRankTeam(Player player) {
        if (player == null) return null;
        return playerTeamMap.get(player);
    }
}
