/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.mafrans.ultimateglow.permissions.handling;

import me.mafrans.ultimateglow.permissions.Main;
import me.mafrans.ultimateglow.permissions.configs.RankConfig;
import me.mafrans.ultimateglow.permissions.handling.*;
import me.mafrans.ultimateglow.permissions.ranks.Rank;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author malmar03
 */
public class RankHandler 
{
    private static final Main plugin = Main.plugin;
    public static void registerConfigs()
    {
        for(Rank r : Rank.values())
        {
            plugin.rankConfigs.put(r, new RankConfig(plugin, "/ranks", r.getRawName().toLowerCase()));
        }
    }
    
    public static FileConfiguration getRankConfig(Rank rank)
    {
        return plugin.rankConfigs.get(rank).getConfig();
    }
    public static RankConfig getRankConfigFile(Rank rank)
    {
        return plugin.rankConfigs.get(rank);
    }
}
