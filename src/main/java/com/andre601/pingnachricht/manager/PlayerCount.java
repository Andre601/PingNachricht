package com.andre601.pingnachricht.manager;

import com.andre601.pingnachricht.PingNachricht;
import com.andre601.pingnachricht.util.config.ConfigKeys;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;

import java.util.Arrays;

public class PlayerCount {

    private PingNachricht plugin;

    public PlayerCount(PingNachricht plugin){
        this.plugin = plugin;
    }

    private String getPlayerCount(){
        return ConfigKeys.PLAYERCOUNT.getString(true);
    }

    public void setPlayerCount(){
        String playerCount = getPlayerCount();

        playerCount = playerCount.replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("%maxonline%", String.valueOf(Bukkit.getServer().getMaxPlayers()));

        final String finalPlayerCount = playerCount;

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(
                plugin,
                ListenerPriority.NORMAL,
                Arrays.asList(new PacketType[] {
                        PacketType.Status.Server.SERVER_INFO
                })
        ) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                e.getPacket().getServerPings().read(0).setVersionName(finalPlayerCount);
                e.getPacket().getServerPings().read(0).setVersionProtocol(-1);
            }
        });
    }
}
