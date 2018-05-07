package com.andre601.pingnachricht.manager;

import com.andre601.pingnachricht.PingNachrichtMain;
import com.andre601.pingnachricht.util.MessageUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoverMessage {

    private static List<WrappedGameProfile> msg = new ArrayList<WrappedGameProfile>();

    /*
    * This string will be used for ProtocolLib.
    * We register the stuff with this String as UUID.
     */
    private static String protocolID = PingNachrichtMain.getNameAndVersion().replace(" ", "_");

    private static void getMessage(){
        for(String str : PingNachrichtMain.config().getStringList("Settings.HoverMessage.Text")){
            for(World w : Bukkit.getWorlds()){
                if(str.contains("%w_" + w.getName() + "%")){
                    str = str.replace("%w_" + w.getName() + "%", String.valueOf(w.getPlayers().size()))
                    .replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("%maxonline%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
                }
            }
            msg.add(new WrappedGameProfile(protocolID, MessageUtil.color(str)));
        }
    }

    public static void setHover(){
        getMessage();

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PingNachrichtMain.getInstance(),
                ListenerPriority.NORMAL, Arrays.asList(new PacketType[] {
                        PacketType.Status.Server.OUT_SERVER_INFO
                }), new ListenerOptions[] {
                ListenerOptions.ASYNC
        }) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                e.getPacket().getServerPings().read(0).setPlayers(msg);
            }
        });
    }
}
