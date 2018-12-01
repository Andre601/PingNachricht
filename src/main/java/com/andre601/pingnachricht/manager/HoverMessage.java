package com.andre601.pingnachricht.manager;

import com.andre601.pingnachricht.PingNachricht;
import com.andre601.pingnachricht.util.config.ConfigKeys;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.*;

public class HoverMessage {

    private PingNachricht plugin;

    public HoverMessage(PingNachricht plugin){
        this.plugin = plugin;
    }

    private UUID uuid = UUID.fromString("PingUUID-0002-0006-0000-authAndre601");

    private static List<WrappedGameProfile> msg = new ArrayList<>();

    private void getMessage(){
        for(String str : ConfigKeys.HOVERMESSAGE.getStringList()){
            for(World w : Bukkit.getWorlds()){
                if(str.contains("%w_" + w.getName() + "%")){
                    str = str.replace("%w_" + w.getName() + "%", String.valueOf(w.getPlayers().size()))
                    .replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("%maxonline%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                }
            }
            if(plugin.isPapiEnabled()) PlaceholderAPI.setPlaceholders(null, str);
        }
    }

    public void setHover(){
        getMessage();

        plugin.getProtocolManager().addPacketListener(new PacketAdapter(
                plugin,
                ListenerPriority.NORMAL,
                Arrays.asList(new PacketType[] {
                        PacketType.Status.Server.SERVER_INFO
                })
        ){
            @Override
            public void onPacketSending(PacketEvent event){

                event.getPacket().getServerPings().read(0).setPlayers(msg);
            }
        });
    }
}
