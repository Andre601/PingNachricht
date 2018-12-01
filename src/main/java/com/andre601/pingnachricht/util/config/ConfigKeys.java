package com.andre601.pingnachricht.util.config;

import com.andre601.pingnachricht.PingNachricht;
import org.bukkit.ChatColor;

import java.util.List;

public enum ConfigKeys {

    PREFIX("Messages.Prefix"),
    ATTEMPT_RELOAD("Messages.Notifications.CommandExecutor.AttemptReload"),
    RELOADED("Messages.Notifications.Other.PluginReloaded"),
    RELOADED_OTHER("Messages.Notifications.Other.PluginReloaded"),

    SHOW_BANNER("Messages.Startup.ShowBanner"),
    NO_PROTOCOLLIB("Messages.Startup.NoProtocolLib"),
    SLP_INSTALLED("Messages.Startup.ServerListPlus"),

    PC_ENABLED("Settings.PlayerCounter.Enabled"),
    PLAYERCOUNT("Settings.PlayerCounter.Text"),
    HM_ENABLED("Settings.HoverMessage.Enabled"),
    HOVERMESSAGE("Settings.HoverMessage.Text");

    private final String PATH;
    public static PingNachricht plugin;

    ConfigKeys(String path){
        this.PATH = path;
    }

    public String getString(boolean colored){
        if(colored)
            return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(this.PATH));

        return plugin.getConfig().getString(this.PATH);
    }

    public List<String> getStringList(){
        return plugin.getConfig().getStringList(this.PATH);
    }

    public boolean getBoolean(){
        return plugin.getConfig().getBoolean(this.PATH);
    }

}
