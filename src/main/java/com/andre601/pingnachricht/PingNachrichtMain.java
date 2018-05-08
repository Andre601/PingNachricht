package com.andre601.pingnachricht;

import com.andre601.pingnachricht.manager.HoverMessage;
import com.andre601.pingnachricht.manager.PlayerCount;
import com.andre601.pingnachricht.util.MessageUtil;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PingNachrichtMain extends JavaPlugin {

    private static PingNachrichtMain instance;

    public void onEnable(){
        PluginManager pm = Bukkit.getPluginManager();

        //  If ProtocolLib isn't enabled, send error-message, disable plugin and return.
        if(!pm.getPlugin("ProtocolLib").isEnabled()) {
            getLogger().severe(MessageUtil.color(
                    config().getString("Messages.Startup.NoProtocolLib")
            ));
            pm.disablePlugin(this);
            return;
        }else
        //  If ServerListPlus is enabled, disable PingNachricht to prevent any conflicts.
        if(pm.getPlugin("ServerListPlus").isEnabled()) {
            getLogger().severe(MessageUtil.color(
                    config().getString("Messages.Startup.ServerListPlus")
            ));
            pm.disablePlugin(this);
            return;
        }

        //  Setting the instance to PingNachrichtMain.java
        instance = this;

        //  Starting the void to create the file
        setupFile();

        //  Doesn't send the banner, if "ShowBanner" in the config.yml is set to false.
        if(config().getBoolean("Messages.Startup.ShowBanner"))
            sendBanner();

        if(config().getBoolean("Settings.HoverMessage.Enabled")) {
            getLogger().info("Setting up HoverMessage...");
            HoverMessage.setHover();
        }

        if(config().getBoolean("Settings.PlayerCounter.Enabled")){
            getLogger().info("Setting up PlayerCounter...");
            PlayerCount.setPlayerCount();
        }
    }

    //  Returning instance, which was set in onEnable()
    public static PingNachrichtMain getInstance(){
        return instance;
    }

    private void setupFile(){

        //  Create Datafolder ("plugins/PingNachricht") if it doesn't exist.
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        File cfg = new File(getDataFolder(), "config.yml");

        //  If the file doesn't exist, start to create one. Else: Do nothing.
        if(!cfg.exists()){
            try{

                // Creating new file and adding the default stuff to it.
                cfg.createNewFile();
                InputStream is = getClass().getResourceAsStream("config.yml");
                OutputStream os = new FileOutputStream(cfg);

                ByteStreams.copy(is, os);

                // Closing for safety.
                os.close();
            }catch (Exception e){
                throw new RuntimeException("Unable to create config.yml", e);
            }
        }
    }

    public static FileConfiguration config() {
        return PingNachrichtMain.getInstance().getConfig();
    }

    //  Returns the plugin name and version ("PingNachricht x.x")
    public static String getNameAndVersion(){
        return PingNachrichtMain.getInstance().getDescription().getName() + " " +
                PingNachrichtMain.getInstance().getDescription().getVersion();
    }

    //  Sends the PingNachricht-Banner (similar to that in the config.yml) in the console.
    private void sendBanner(){
        getLogger().info(
                MessageUtil.color("&b _____ _             &9_   _            _          _      _     _"));
        getLogger().info(
                MessageUtil.color("&b|  __ (_)           &9| \\ | |          | |        (_)    | |   | |"));
        getLogger().info(
                MessageUtil.color("&b| |__) | _ __   __ _&9|  \\| | __ _  ___| |__  _ __ _  ___| |__ | |_"));
        getLogger().info(
                MessageUtil.color("&b|  ___/ | '_ \\ / _` &9| . ` |/ _` |/ __| '_ \\| '__| |/ __| '_ \\| __|"));
        getLogger().info(
                MessageUtil.color("&b| |   | | | | | (_| &9| |\\  | (_| | (__| | | | |  | | (__| | | | |_"));
        getLogger().info(
                MessageUtil.color("&b|_|   |_|_| |_|\\__, &9|_| \\_|\\__,_|\\___|_| |_|_|  |_|\\___|_| |_|\\__|"));
        getLogger().info(
                MessageUtil.color("&b                __/ |"));
        getLogger().info(
                MessageUtil.color("&b               |___/"));
    }

}
