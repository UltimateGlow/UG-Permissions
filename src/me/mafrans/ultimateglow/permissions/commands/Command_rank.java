package me.mafrans.ultimateglow.permissions.commands;

import java.util.List;
import me.mafrans.ultimateglow.permissions.Main;
import me.mafrans.ultimateglow.permissions.handling.RankHandler;
import me.mafrans.ultimateglow.permissions.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Command_rank implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
        if(args.length < 1)
        {
            sender.sendMessage(ChatColor.GRAY + "Your rank is: " + Rank.getSenderRank(sender).getName());
            return true;
        }
        else if(sender instanceof Player)
        {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("console-only-message")));
            return true;
        }
        
        if(args[0].equalsIgnoreCase("add"))
        {
            if(args.length != 3)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank add <player> <rank>&7."));
                return true;
            }
            Player plr = Main.plugin.server.getPlayer(args[1]);
            Rank rank = Rank.getRank(args[2].toUpperCase());
            if(plr == null)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("player-not-found-message")));
                return true;
            }
            if(rank == null)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("rank-not-found-message")));
                return true;
            }
            
            Rank.addPlayer(plr, rank);
            if("".equals(Main.plugin.config.getString("add-player-to-rank-message")))
            {
                return true;
            }
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("add-player-to-rank-message").replace("%player%", plr.getName()).replace("%rank%", rank.getName())));
            return true;
        }
        else if(args[0].equalsIgnoreCase("remove"))
        {
            if(args.length != 2)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank remove <player>&7."));
                return true;
            }
            Player plr = Main.plugin.server.getPlayer(args[1]);
            Rank rank = Rank.getPlayerRank(plr);
            if(plr == null)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("player-not-found-message")));
                return true;
            }
            
            Rank.removePlayer(plr);
            if("".equals(Main.plugin.config.getString("remove-player-from-rank-message")))
            {
                return true;
            }
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("remove-player-from-rank-message").replace("%player%", plr.getName()).replace("%rank%", rank.getName())));
            return true;
        }
        else if(args[0].equalsIgnoreCase("list"))
        {
            if(args.length != 1)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank list&7."));
                return true;
            }
            sender.sendMessage(ChatColor.GRAY + "Displaying list of currently loaded ranks:");
            for(Rank r : Rank.values())
            {
                sender.sendMessage(ChatColor.GRAY + " - " + r.getName());
            }
            return true;
        }
        else if(args[0].equalsIgnoreCase("permission"))
        {
            if(args.length < 2)
            {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank permission <add|remove|list> [values...]&7."));
                return true;
            }
            if(args[1].equalsIgnoreCase("add"))
            {
                if(args.length != 4)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank permission add <rank> <command>&7."));
                    return true;
                }
                Rank rank = Rank.getRank(args[2]);
                
                if(rank == null)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("rank-not-found-message")));
                    return true;
                }
                List<String> permissions = RankHandler.getRankConfig(rank).getStringList("permissions");
                if(permissions.contains(args[3].toLowerCase())) return true;
                permissions.add(args[3].toLowerCase());
                RankHandler.getRankConfig(rank).set("permissions", permissions);
                RankHandler.getRankConfigFile(rank).saveConfig();
                RankHandler.getRankConfigFile(rank).reloadConfig();
            }
            else if(args[1].equalsIgnoreCase("remove"))
            {
                if(args.length != 4)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank permission remove <rank> <command>&7."));
                    return true;
                }
                Rank rank = Rank.getRank(args[2]);
                
                if(rank == null)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("rank-not-found-message")));
                    return true;
                }
                List<String> permissions = RankHandler.getRankConfig(rank).getStringList("permissions");
                if(!permissions.contains(args[3].toLowerCase())) return true;
                permissions.remove(args[3].toLowerCase());
                RankHandler.getRankConfig(rank).set("permissions", permissions);
                RankHandler.getRankConfigFile(rank).saveConfig();
                RankHandler.getRankConfigFile(rank).reloadConfig();
            }
            else if(args[1].equalsIgnoreCase("list"))
            {
                if(args.length != 3)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Invalid usage of command."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[&4&l!&c&l] &9Command -> &7Valid usage: &f/rank permission list <rank>&7."));
                    return true;
                }
                Rank rank = Rank.getRank(args[2]);
                if(rank == null)
                {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.config.getString("rank-not-found-message")));
                    return true;
                }
                sender.sendMessage(ChatColor.GRAY + "Displaying list of currently loaded permissions for " + rank.getName() + ChatColor.GRAY + ":");
                for(String r : RankHandler.getRankConfig(rank).getStringList("permissions"))
                {
                    sender.sendMessage(ChatColor.GRAY + " - " + r);
                }
                return true;
            }
        }
        
        
        return true;
    }
    
}
