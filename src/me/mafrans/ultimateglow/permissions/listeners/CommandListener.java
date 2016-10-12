package me.mafrans.ultimateglow.permissions.listeners;

import me.mafrans.ultimateglow.permissions.Main;
import me.mafrans.ultimateglow.permissions.handling.PermissionHandler;
import me.mafrans.ultimateglow.permissions.ranks.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class CommandListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        String command = e.getMessage();
        Player player = e.getPlayer();
        Rank rank = Rank.getPlayerRank(player);
        
        if(PermissionHandler.hasPermission(player, command))
        {
            player.setOp(true);
        }
        else
        {
            player.setOp(false);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.plugin.config.getString("no-permission-message")));
            e.setCancelled(true);
        }
    }
}
