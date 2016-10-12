/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mafrans.ultimateglow.permissions.ranks;

import java.util.List;
import me.mafrans.ultimateglow.permissions.Main;
import me.mafrans.ultimateglow.permissions.handling.RankHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author malmar03
 */
public enum Rank 
{
    PLAYER("Player", ChatColor.WHITE, null, RankType.STAFF),
    
    // Special Ranks.
    TWITCH("Twitch Streamer", ChatColor.DARK_PURPLE, "&5&lTwitch &7- &5", RankType.SPECIAL),
    YOUTUBE("You" + ChatColor.WHITE + "Tuber", ChatColor.RED, "&f&lYoutube &7- &f", RankType.SPECIAL),
    BUILDER("Builder", ChatColor.YELLOW, "&eBuilder &7- &9", RankType.SPECIAL),
    
    // Donator Ranks
    PRO("Pro", ChatColor.DARK_BLUE, "&1Pro &7- &1", RankType.DONATOR),
    ELITE("Elite", ChatColor.LIGHT_PURPLE, "&d&lElite &7- &d", RankType.DONATOR),
    GALAXY("Galaxy", ChatColor.DARK_BLUE, "&5&lGalaxy &7- &5", RankType.DONATOR),
    ULTIMATE("Ultimate", ChatColor.AQUA, "&b&lUltimate &7- &b", RankType.DONATOR),
    
    // Staff Ranks
    HELPER("Helper", ChatColor.GREEN, "&aHelper &7- &a", RankType.STAFF),
    MODERATOR("Moderator", ChatColor.DARK_AQUA, "&3Moderator &7- &3", RankType.STAFF),
    SPMOD("Special Moderator", ChatColor.DARK_AQUA, "&3SpMod &7- &3", RankType.STAFF),
    ADMIN("Administrator", ChatColor.RED, "&cAdmin &7- &c", RankType.STAFF),
    MANAGER("Manager", ChatColor.DARK_RED, "&4[Manager]  &e", RankType.STAFF),
    OWNER("Owner", ChatColor.RED, "&c[Owner]  &e", RankType.STAFF);
    
    private final String name;
    private final ChatColor color;
    private final String tag;
    private final RankType type;
    Rank(String name, ChatColor color, String tag, RankType type)
    {
        this.name = name;
        this.color = color;
        this.tag = tag;
        this.type = type;
    }
    
    /**
     *
     * @return Raw name of rank.
     */
    public String getRawName()
    {
        return ChatColor.stripColor(name);
    }

    /**
     *
     * @return Colored name of rank.
     */
    public String getName()
    {
        return color + name;
    }

    /**
     *
     * @return The rank color.
     */
    public ChatColor getColor()
    {
        return color;
    }

    /**
     *
     * @return Raw tag of rank.
     */
    public String getRawTag()
    {
        return ChatColor.stripColor(tag);
    }

    /**
     *
     * @return Full tag of rank.
     */
    public String getTag()
    {
        return ChatColor.translateAlternateColorCodes('&',tag);
    }
    
    public static Rank getSenderRank(CommandSender sender)
    {
        for(Rank rnk : Main.plugin.rankConfigs.keySet())
        {
            FileConfiguration config = Main.plugin.rankConfigs.get(rnk).getConfig();
            if(config.getStringList("players").contains(sender.getName()))
            {
                return rnk;
            }
        }
        return PLAYER;
    }
    public static Rank getPlayerRank(Player player)
    {
        return getSenderRank((CommandSender)player);
    }
    public static Rank getRank(String name)
    {
        for(Rank r : Rank.values())
        {
            if(r.toString().equalsIgnoreCase(name))
            {
                return r;
            }
            else if(r.getRawName().equalsIgnoreCase(name))
            {
                return r;
            }
            else if(r.getName().equalsIgnoreCase(name))
            {
                return r;
            }
        }
        return null;
    }
    public static void addPlayer(Player player, Rank rank)
    {
        List<String> players = RankHandler.getRankConfig(rank).getStringList("players");
        
        if(players.contains(player.getName())) return;
        
        for(Rank r : Rank.values())
        {
            if(RankHandler.getRankConfig(r).getStringList("players").contains(player.getName()))
            {
                List<String> plrs = RankHandler.getRankConfig(r).getStringList("players");
                plrs.remove(player.getName());
                RankHandler.getRankConfig(r).set("players", plrs);
            }
        }
        
        players.add(player.getName());
        RankHandler.getRankConfig(rank).set("players", players);
        RankHandler.getRankConfigFile(rank).saveConfig();
        RankHandler.getRankConfigFile(rank).reloadConfig();
    }
    public static void removePlayer(Player player)
    {
        Rank rank = getPlayerRank(player);
        List<String> players = RankHandler.getRankConfig(rank).getStringList("players");
        
        if(!players.contains(player.getName())) return;
        
        players.remove(player.getName());
        RankHandler.getRankConfig(rank).set("players", players);
        RankHandler.getRankConfigFile(rank).saveConfig();
        RankHandler.getRankConfigFile(rank).reloadConfig();
    }
}
