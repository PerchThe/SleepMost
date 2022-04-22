package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.builders.MessageBuilder;
import me.mrgeneralq.sleepmost.enums.ConfigMessage;
import me.mrgeneralq.sleepmost.interfaces.IMessageService;
import me.mrgeneralq.sleepmost.interfaces.ISleepService;
import me.mrgeneralq.sleepmost.interfaces.ISubCommand;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import me.mrgeneralq.sleepmost.statics.CommandSenderUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IMessageService messageService;

    public KickSubCommand(ISleepService sleepService, IMessageService messageService) {
    this.sleepService = sleepService;
    this.messageService = messageService;
    }

    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(!CommandSenderUtils.hasWorld(sender)){
            this.messageService.sendMessage(sender, messageService.getMessage(ConfigMessage.NO_CONSOLE_COMMAND).build());
            return true;
        }

        Player player = (Player) sender;

        World world = CommandSenderUtils.getWorldOf(sender);

        if(args.length < 2){
            this.messageService.sendMessage(sender, messageService.getMessage(ConfigMessage.SPECIFY_PLAYER).build());
            return true;
        }

        String targetPlayerName = args[1];

        if(Bukkit.getPlayer(targetPlayerName) == null){
            this.messageService.sendMessage(sender, messageService.getMessage(ConfigMessage.TARGET_NOT_ONLINE).build());
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if(!this.sleepService.isPlayerAsleep(targetPlayer)){
            this.messageService.sendMessage(sender, messageService.getMessage(ConfigMessage.TARGET_NOT_SLEEPING).build());
            return true;
        }

        MessageBuilder targetKickedFromBedMsg = this.messageService.getMessagePrefixed(ConfigMessage.KICKED_PLAYER_FROM_BED)
                .setPlayer(targetPlayer)
                .setWorld(targetPlayer.getWorld());

        MessageBuilder youAreKickedMsg = this.messageService.getMessage(ConfigMessage.YOU_ARE_KICKED_FROM_BED)
                .setPlayer(player);

        targetPlayer.teleport(targetPlayer.getLocation());
        this.messageService.sendMessage(sender,targetKickedFromBedMsg.build());
        this.messageService.sendMessage(targetPlayer, youAreKickedMsg.build());
        this.sleepService.setSleeping(targetPlayer, false);
        return true;
    }
}
