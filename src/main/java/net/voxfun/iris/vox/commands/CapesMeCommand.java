package net.voxfun.iris.vox.commands;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import net.voxfun.iris.vox.index;
import net.voxfun.iris.vox.managers.RandomId;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static net.voxfun.iris.vox.index.database;

public class CapesMeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!index.isMainLobby) return true;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String uuid = p.getUniqueId().toString().replace("-", "");
            String randomId = new RandomId(8).generate();
            if (database == null) {
                index.logger.info("Database not initialized");
                p.sendMessage(ChatColor.RED + "Database is not initialized.");
                return true;
            }
            MongoCollection<Document> collection = database.getCollection("account-links");
            Document doc = collection.find(eq("uuid", uuid)).first();
            if (doc != null) {
                Document query = new Document().append("uuid", uuid);
                Bson updates = Updates.combine(Updates.set("code", randomId), Updates.set("date", new Date()));
                UpdateOptions options = new UpdateOptions().upsert(true);
                try {
                    collection.updateOne(query, updates, options);
                    index.logger.info(String.format("https://capes.me/link/%s", randomId));
                    p.sendMessage(String.format("https://capes.me/link/%s", randomId));
                } catch (MongoException e) {
                    p.sendMessage(ChatColor.RED + "There was an error trying to update the auth key.");
                    e.printStackTrace();
                }
            } else {
                try {
                    Document cDoc = new Document("_id", new ObjectId());
                    cDoc.append("date", new Date()).append("uuid", uuid).append("code", randomId);
                    collection.insertOne(cDoc);
                    index.logger.info(String.format("https://capes.me/link/%s", randomId));
                    p.sendMessage(String.format("https://capes.me/link/%s", randomId));
                } catch (MongoException e) {
                    p.sendMessage(ChatColor.RED + "There was an error trying to create an auth key.");
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
