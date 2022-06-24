package net.voxfun.iris.vox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {
    private String cmd = "";
    private Boolean isEmpty = false;
    private ArrayList list = new ArrayList<>();
    public TabCompletion(String command, Boolean empty, String[] list) {
        this.cmd = command;
        this.isEmpty = empty;
        for (int i = 0; i < list.length; i++) { this.list.add(list[i]); }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase(cmd)) {
            if (isEmpty) return new ArrayList<>();
            return list;
        }
        return null;
    }
}
