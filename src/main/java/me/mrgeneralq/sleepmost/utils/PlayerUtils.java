package me.mrgeneralq.sleepmost.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {

    public static boolean isRealPlayer(Player player){
        return Bukkit.getPlayer(player.getUniqueId()) != null;
    }

}
