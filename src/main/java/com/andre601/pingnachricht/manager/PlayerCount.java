package com.andre601.pingnachricht.manager;

import com.andre601.pingnachricht.PingNachrichtMain;
import com.andre601.pingnachricht.util.MessageUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;

import java.util.Arrays;

public class PlayerCount {

    private static String getPlayerCount(){
        return PingNachrichtMain.config().getString("Settings.PlayerCounter.Text");
    }

    public static void setPlayerCount(){
        final String pc = getPlayerCount();

        pc.replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("%maxonline%", String.valueOf(Bukkit.getServer().getMaxPlayers()));

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PingNachrichtMain.getInstance(),
                ListenerPriority.NORMAL, Arrays.asList(new PacketType[] {
                PacketType.Status.Server.OUT_SERVER_INFO
        }), new ListenerOptions[] {
                ListenerOptions.ASYNC
        }) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                e.getPacket().getServerPings().read(0).setVersionName(MessageUtil.color(pc));
                e.getPacket().getServerPings().read(0).setVersionProtocol(-1);
            }
        });
    }
}
