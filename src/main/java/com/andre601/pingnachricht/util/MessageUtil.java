package com.andre601.pingnachricht.util;

import com.andre601.pingnachricht.PingNachricht;
import org.bukkit.ChatColor;

public class MessageUtil {

    private PingNachricht plugin;

    public MessageUtil(PingNachricht plugin){
        this.plugin = plugin;
    }

    private String prefix = "&f[&bPing&9Nachricht&f] ";

    public void sendMsg(String msg){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes(
                '&', prefix + msg
        ));
    }

}
