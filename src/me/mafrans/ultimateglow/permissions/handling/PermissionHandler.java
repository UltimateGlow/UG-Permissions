package me.mafrans.ultimateglow.permissions.handling;

import java.util.ArrayList;
import java.util.List;
import me.mafrans.ultimateglow.permissions.ranks.Rank;
import org.bukkit.entity.Player;


public class PermissionHandler 
{
    public static List<Rank> getPermittedRanks(String command)
    {
        if(!command.startsWith("/"))
        {
            command = "/" + command;
        }
        
        List<Rank> permitted = new ArrayList();
        for(Rank r : Rank.values())
        {
            if(RankHandler.getRankConfig(r).getStringList("permissions").contains(command))
            {
                permitted.add(r);
            }
        }
        return permitted;
    }
    public static boolean hasPermission(Player player, String command)
    {
        Rank rank = Rank.getPlayerRank(player);
        
        if(RankHandler.getRankConfig(rank).getStringList("permissions").contains("@Override")) return true;
        return RankHandler.getRankConfig(rank).getStringList("permissions").contains(command.replaceFirst("/",""));
    }
}
