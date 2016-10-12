package me.mafrans.ultimateglow.permissions.configs;

import java.io.File;
import java.io.IOException;
import me.mafrans.ultimateglow.permissions.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class RankConfig
{
    public FileConfiguration config = null;
    public File configFile = null;
    private final Main plugin;
    private final String path;
    private final String fileName;

    public RankConfig(Main instance, String path, String fileName)
    {
        this.plugin = instance;
        this.path = path;
        this.fileName = fileName;
    }

    public void loadConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void reloadConfig()
    {
        if(this.configFile == null)
        {
            this.configFile = new File(this.plugin.getDataFolder() + path, fileName + ".yml");
        }

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void saveConfig()
    {
        if((this.config == null) || (this.configFile == null))
        {
            return;
        }

        try
        {
            this.config.save(this.configFile);
        }
        catch(IOException e)
        {
        }
    }

    public FileConfiguration getConfig()
    {
        if(this.config == null)
        {
            reloadConfig();
        }

        return this.config;
    }
}
