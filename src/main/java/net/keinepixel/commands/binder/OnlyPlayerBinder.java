package net.keinepixel.commands.binder;

import eu.koboo.atcommand.api.binder.OnlyPlayerBinderSpigot;
import net.keinepixel.LobbyPlugin;
import net.keinepixel.adventure.ChatAction;
import org.bukkit.command.ConsoleCommandSender;

public record OnlyPlayerBinder(LobbyPlugin lobbyPlugin) implements OnlyPlayerBinderSpigot {
    @Override
    public void onlyPlayer(ConsoleCommandSender consoleCommandSender) {
        //TODO: Get sender language via API
        //TODO: Language Key: command.generic.only-player
        consoleCommandSender.sendMessage(ChatAction.of(lobbyPlugin.getPrefix()).with("§cOnly players can execute this command!").comp());
    }
}
