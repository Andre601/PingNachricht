package com.andre601;

import com.andre601.manager.HoverMessage;
import com.andre601.manager.PlayerCount;
import com.andre601.util.MessageUtil;
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
        if(!pm.isPluginEnabled("ProtocolLib")) {
            this.getLogger().severe(MessageUtil.color(
                    config().getString("Messages.Startup.NoProtocolLib")
            ));
            pm.disablePlugin(this);
            return;
        }

        //  Setting the instance to PingNachrichtMain.java
        instance = this;

        //  Starting the void to create the file
        setupFile();

        //  Registering the events in HoverMessgae.java and PlayerCount.java.
        pm.registerEvents(new HoverMessage(), this);
        pm.registerEvents(new PlayerCount(), this);

        //  Doesn't send the banner, if "ShowBanner" in the config.yml is set to false.
        if(config().getBoolean("Messages.Startup.ShowBanner"))
            sendBanner();
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
    private static void sendBanner(){
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b _____ _             &9_   _            _          _      _     _"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b|  __ (_)           &9| \\ | |          | |        (_)    | |   | |"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b| |__) | _ __   __ _&9|  \\| | __ _  ___| |__  _ __ _  ___| |__ | |_"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b|  ___/ | '_ \\ / _` &9| . ` |/ _` |/ __| '_ \\| '__| |/ __| '_ \\| __|"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b| |   | | | | | (_| &9| |\\  | (_| | (__| | | | |  | | (__| | | | |_"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b|_|   |_|_| |_|\\__, &9|_| \\_|\\__,_|\\___|_| |_|_|  |_|\\___|_| |_|\\__|"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b                __/ |"));
        PingNachrichtMain.getInstance().getLogger().info(
                MessageUtil.color("&b               |___/"));
    }

}
