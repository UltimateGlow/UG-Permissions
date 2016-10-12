/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.mafrans.ultimateglow.permissions;

import java.util.HashMap;
import me.mafrans.ultimateglow.permissions.commands.Command_rank;
import me.mafrans.ultimateglow.permissions.configs.RankConfig;
import me.mafrans.ultimateglow.permissions.handling.RankHandler;
import me.mafrans.ultimateglow.permissions.listeners.ChatListener;
import me.mafrans.ultimateglow.permissions.listeners.CommandListener;
import me.mafrans.ultimateglow.permissions.ranks.Rank;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author malmar03
 */
public class Main extends JavaPlugin
{    
    public static Main plugin;
    public FileConfiguration config;
    public Server server;
    public HashMap<Rank, RankConfig> rankConfigs = new HashMap();
    
    @Override
    public void onEnable()
    {
        plugin = this;
        config = getConfig();
        server = getServer();
        
        this.saveDefaultConfig();
        
        RankHandler.registerConfigs();
        
        this.getCommand("rank").setExecutor(new Command_rank());
        
        server.getPluginManager().registerEvents(new ChatListener(), plugin);
        server.getPluginManager().registerEvents(new CommandListener(), plugin);
    }
}
