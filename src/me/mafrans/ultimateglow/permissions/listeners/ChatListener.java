package me.mafrans.ultimateglow.permissions.listeners;

import me.mafrans.ultimateglow.permissions.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatListener implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        String message = e.getMessage();
        Player player = e.getPlayer();
        Rank rank = Rank.getPlayerRank(player);
        
        if(rank.getTag() != null)
        {
            Bukkit.broadcastMessage(rank.getTag() + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + message);
            e.setCancelled(true);
        }
    }
}
