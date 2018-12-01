package com.andre601.pingnachricht.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.andre601.pingnachricht.PingNachricht;
import com.andre601.pingnachricht.util.MessageUtil;
import com.andre601.pingnachricht.util.config.ConfigKeys;
import org.bukkit.entity.Player;

@CommandAlias("pingnachricht|pn")
public class CmdPingNachricht extends BaseCommand {

    private PingNachricht plugin;

    public CmdPingNachricht(PingNachricht plugin){
        this.plugin = plugin;
    }

    @Subcommand("reload")
    @Description("Reloads the config.yml")
    @CommandPermission("pingnachricht.reload")
    public void doReload(Player player){
        plugin.getMessageUtil().sendMsg("&7Reloading config.yml...");
        player.sendMessage(ConfigKeys.PREFIX.getString(true) + ConfigKeys.ATTEMPT_RELOAD.getString(true));
        plugin.reloadConfig();
        plugin.getMessageUtil().sendMsg("&7Reload successful!");
        player.sendMessage(ConfigKeys.PREFIX.getString(true) + ConfigKeys.RELOADED.getString(true));
    }

    @Subcommand("help")
    @Default
    @HelpCommand
    @Description("Shows the available commands")
    public void giveHelp(CommandHelp help){
        help.showHelp();
    }
}
