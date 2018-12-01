package com.andre601.pingnachricht;

import co.aikar.commands.BukkitCommandManager;
import com.andre601.pingnachricht.commands.CmdPingNachricht;
import com.andre601.pingnachricht.manager.HoverMessage;
import com.andre601.pingnachricht.manager.PlayerCount;
import com.andre601.pingnachricht.util.config.ConfigKeys;
import com.andre601.pingnachricht.util.MessageUtil;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PingNachricht extends JavaPlugin {

    private static PingNachricht instance;
    private BukkitCommandManager manager;
    private PluginManager pm;
    private static ProtocolManager protocolManager;

    private HoverMessage hoverMessage;
    private PlayerCount playerCount;
    private MessageUtil messageUtil;

    private boolean papiEnabled;

    public void onEnable(){

        pm = Bukkit.getPluginManager();
        long startTime = System.currentTimeMillis();

        //  Setting the instance to PingNachricht.java
        instance = this;
        ConfigKeys.plugin = this;
        hoverMessage = new HoverMessage(this);
        playerCount = new PlayerCount(this);
        messageUtil = new MessageUtil(this);
        protocolManager = ProtocolLibrary.getProtocolManager();

        //  Starting the void to create the file
        this.saveDefaultConfig();

        //  Doesn't send the banner, if "ShowBanner" in the config.yml is set to false.
        if(ConfigKeys.SHOW_BANNER.getBoolean())
            sendBanner();

        getMessageUtil().sendMsg("&7Loading " + getNameAndVersion());

        //  If the check returns false: Disable plugin.
        if(!check(pm)) {
            pm.disablePlugin(getInstance());
            return;
        }

        registerCommands(this);

        if(hoverEnabled()) {
            getMessageUtil().sendMsg("&7Enabling HoverMessage...");
            getHoverMessage().setHover();
            getMessageUtil().sendMsg("&7HoverMessage enabled!");
        }

        if(playercountEnabled()){
            getMessageUtil().sendMsg("&7Enabling PlayerCount...");
            getPlayerCount().setPlayerCount();
            getMessageUtil().sendMsg("PlayerCount enabled!");
        }

        getMessageUtil().sendMsg("&7Checking for PlaceholderAPI...");
        if(checkPlaceholderAPI(pm)){
            papiEnabled = true;
            getMessageUtil().sendMsg("&7PlaceholderAPI found! Placeholders are now supported in the hovermessage.");
        }else{
            papiEnabled = false;
            getMessageUtil().sendMsg("&7No PlaceholderAPI found. Using default placeholders...");
        }

        getMessageUtil().sendMsg("&aPingNachricht enabled in " + (System.currentTimeMillis() - startTime) + "ms!");
    }

    public void onDisable(){
        getMessageUtil().sendMsg("&7Unregister commands...");
        if(manager != null)
            manager.unregisterCommands();
    }

    //  Returning instance, which was set in onEnable()
    public static PingNachricht getInstance(){
        return instance;
    }

    //  Returns the plugin name and version ("PingNachricht x.x")
    public String getNameAndVersion(){
        return this.getDescription().getName() + " " + this.getDescription().getVersion();
    }

    private void registerCommands(PingNachricht plugin){
        manager = new BukkitCommandManager(getInstance());

        manager.enableUnstableAPI("api");

        getMessageUtil().sendMsg("&7Register commands...");
        manager.registerCommand(new CmdPingNachricht(plugin));
        getMessageUtil().sendMsg("&7Commands successfully registered!");
    }

    private String getServerVersion(){
        return Bukkit.getVersion();
    }

    private String getAPIVersion(){
        return Bukkit.getBukkitVersion();
    }

    //  Sends the PingNachricht-Banner (similar to that in the config.yml) in the console.
    private void sendBanner(){
        getMessageUtil().sendMsg("");
        getMessageUtil().sendMsg("&b ____   &9 _   _");
        getMessageUtil().sendMsg("&b|  _ \\  &9| \\ | |");
        getMessageUtil().sendMsg("&b| |_) | &9|  \\| |");
        getMessageUtil().sendMsg("&b|  __/  &9| . ` |");
        getMessageUtil().sendMsg("&b| |     &9| |\\  |");
        getMessageUtil().sendMsg("&b|_|     &9|_| \\_|");
        getMessageUtil().sendMsg("");
    }

    private boolean check(PluginManager pluginManager){
        pm = pluginManager;

        getMessageUtil().sendMsg("&7Searching for ProtocolLib...");
        if(pm.getPlugin("ProtocolLib") == null || !pm.getPlugin("ProtocolLib").isEnabled()){
            noProtocolLib();
            return false;
        }
        if(pm.getPlugin("ServerListPlus") != null){
            slpInstalled();
            return false;
        }
        getMessageUtil().sendMsg("&7ProtocolLib found!");

        return true;
    }

    private void noProtocolLib(){
        for(String str : ConfigKeys.NO_PROTOCOLLIB.getStringList()){
            str = str.replace("%mcversion%", getServerVersion()).replace("%apiversion%", getAPIVersion());

            getMessageUtil().sendMsg(ChatColor.translateAlternateColorCodes('&', str));
        }
    }

    private void slpInstalled(){
        for(String str : ConfigKeys.SLP_INSTALLED.getStringList()){
            getMessageUtil().sendMsg(ChatColor.translateAlternateColorCodes('&', str));
        }
    }

    public ProtocolManager getProtocolManager(){
        return protocolManager;
    }

    private boolean hoverEnabled(){
        return ConfigKeys.HM_ENABLED.getBoolean();
    }

    private boolean playercountEnabled(){
        return ConfigKeys.PC_ENABLED.getBoolean();
    }

    private boolean checkPlaceholderAPI(PluginManager pm){
        if(pm.getPlugin("PlaceholderAPI") != null){
            return true;
        }

        return false;
    }

    public boolean isPapiEnabled() {
        return papiEnabled;
    }

    private HoverMessage getHoverMessage(){
        return hoverMessage;
    }

    private PlayerCount getPlayerCount(){
        return playerCount;
    }

    public MessageUtil getMessageUtil() {
        return messageUtil;
    }
}
